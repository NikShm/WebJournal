package com.webjournal.service.user;

import com.webjournal.dto.PageDTO;
import com.webjournal.dto.FollowDTO;
import com.webjournal.dto.search.AuthorSearch;
import com.webjournal.dto.search.SearchDTO;
import com.webjournal.dto.user.*;
import com.webjournal.entity.User;
import com.webjournal.enums.RoleType;
import com.webjournal.enums.SortDirection;
import com.webjournal.exception.DatabaseFetchException;
import com.webjournal.exception.ForbiddenException;
import com.webjournal.exception.UpdateException;
import com.webjournal.mapper.UserMapper;
import com.webjournal.repository.UserRepository;
import com.webjournal.service.role.RoleServiceImpl;
import com.webjournal.utils.QueryHelper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private final UserMapper mapper;
    private final EntityManager entityManager;
    private final RoleServiceImpl roleService;

    public UserServiceImpl(UserRepository repository, UserMapper mapper, EntityManager entityManager, RoleServiceImpl roleService) {
        this.repository = repository;
        this.mapper = mapper;
        this.entityManager = entityManager;
        this.roleService = roleService;
    }

    @Override
    public AuthorDTO getAuthorById(Integer id) {
        return repository.findById(id).map(mapper::toAuthorDto).orElseThrow(() -> new DatabaseFetchException("Could not find User entity with id " + id));
    }

    @Override
    public UserDTO getPublicUserById(Integer id) {
        return repository.findById(id).map(mapper::toUserDto).orElseThrow(() -> new DatabaseFetchException("Could not find User entity with id " + id));
    }

    @Override
    public FullUserDTO getFullUserById(Integer id) {
        return repository.findById(id).map(mapper::toFullUserDto).orElseThrow(() -> new DatabaseFetchException("Could not find User entity with id " + id));
    }

    @Override
    public User getByUsernameButGetUser(String username) {
        return repository.findByUsername(username).orElseThrow(() -> new DatabaseFetchException("Could not find User entity with username " + username));
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        User userToDelete = repository.findById(id).orElseThrow(() -> new DatabaseFetchException("Could not delete: could not find User entity with id " + id));
        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!principal.getId().equals(id) && userToDelete.getRole().getRole() != RoleType.ADMIN) {
            throw new ForbiddenException("Access is denied. You don't have permission to access this resource");
        }
        repository.deleteById(id);
    }

    @Override
    public void update(UserUpdateRequest request) {
        User userToUpdate = repository.findById(request.getId()).orElseThrow(() -> new DatabaseFetchException("Could not find User entity with id " + request.getId()));
        if (!request.getUsername().equals(userToUpdate.getUsername()) && repository.existsByUsername(request.getUsername())) {
            throw new UpdateException("this username is already taken");
        }
        User updatedUser = mapper.toUserEntity(userToUpdate, request);
        repository.save(updatedUser);
    }

    @Override
    public void changeRole(RoleUpdateRequest request) {
        User userToUpdate = repository.findById(request.getId()).orElseThrow(() -> new DatabaseFetchException("Could not find User entity with id " + request.getId()));
        if (request.getRole() == RoleType.ADMIN || userToUpdate.getRole().getRole() == RoleType.ADMIN) {
            throw new ForbiddenException("Access is denied. You don't have permission to access this resource");
        }
        userToUpdate.setRole(roleService.getRoleByRoleType(request.getRole()));
        repository.save(userToUpdate);
    }

    @Override
    public List<UserDTO> getAll() {
        return repository.findAll().stream().map(mapper::toUserDto).toList();
    }

    @Override
    public List<AuthorDTO> getInterestingAuthors(int quantity) {
        Pageable page = PageRequest.of(0, quantity);
        return repository.findInterestingAuthors(page).stream().map(mapper::toAuthorDto).toList();
    }

    @Override
    public PageDTO<AuthorDTO> getAuthorPage(SearchDTO<AuthorSearch> search) {
        Sort sort = Sort.by(search.getSortField());
        if (search.getSortDirection() == SortDirection.DESC) {
            sort = sort.descending();
        }
        Pageable pageable = PageRequest.of(search.getPage(), search.getPageSize(), sort);
        Page<User> all = repository.findAll((root, query, criteriaBuilder) -> getPredicate(search.getSearchPattern(), criteriaBuilder, root), pageable);
        PageDTO<AuthorDTO> dto = new PageDTO<>();
        dto.setContent(all.stream().map(mapper::toAuthorDto).collect(Collectors.toList()));
        dto.setTotalItem(all.getTotalElements());
        return dto;
    }

    private Predicate getPredicate(AuthorSearch search, CriteriaBuilder criteriaBuilder, Root<User> user) {
        List<Predicate> predicates = new ArrayList<>();
        String value = search.getSearch();
        if (value != null) {
            predicates.add(QueryHelper.ilike(user.get("username"), criteriaBuilder, value));
        }
        return predicates.size() == 1 ? predicates.get(0) : criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

    @Transactional
    @Override
    public void subscribe(FollowDTO followDTO) {
        if (followDTO.getFollowingUserId() != null && followDTO.getUserId() != null) {
            Boolean isExist = (Boolean) entityManager.createNativeQuery("select exists(select 1 from follow where user_id=?1 and following_user_id=?2)")
                    .setParameter(1, followDTO.getUserId())
                    .setParameter(2, followDTO.getFollowingUserId())
                    .getSingleResult();
            if (!Objects.equals(followDTO.getFollowingUserId(), followDTO.getUserId()) && !isExist) {
                String INSERT_LIKE = "insert into follow(user_id, following_user_id) VALUES (?1,?2) ON CONFLICT DO NOTHING";
                entityManager.createNativeQuery(INSERT_LIKE).setParameter(1, followDTO.getUserId())
                        .setParameter(2, followDTO.getFollowingUserId()).executeUpdate();
            }
        }
    }

    @Transactional
    @Override
    public void unsubscribe(FollowDTO followDTO) {
        if (followDTO.getFollowingUserId() != null && followDTO.getUserId() != null) {
            String DELETE_LIKE = "delete from follow where user_id = ?1 and following_user_id = ?2";
            entityManager.createNativeQuery(DELETE_LIKE).setParameter(1, followDTO.getUserId())
                    .setParameter(2, followDTO.getFollowingUserId()).executeUpdate();
        }
    }
}
