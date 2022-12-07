package com.webjournal.service.user;

import com.webjournal.dto.PageDTO;
import com.webjournal.dto.FollowDTO;
import com.webjournal.dto.search.AuthorSearch;
import com.webjournal.dto.search.SearchDTO;
import com.webjournal.dto.user.AuthorDTO;
import com.webjournal.dto.user.UserDTO;
import com.webjournal.dto.user.UserUpdateRequest;
import com.webjournal.entity.User;
import com.webjournal.enums.SortDirection;
import com.webjournal.exception.DatabaseFetchException;
import com.webjournal.mapper.UserMapper;
import com.webjournal.repository.UserRepository;
import com.webjournal.utils.QueryHelper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    public UserServiceImpl(UserRepository repository, UserMapper mapper, EntityManager entityManager) {
        this.repository = repository;
        this.mapper = mapper;
        this.entityManager = entityManager;
    }

    @Override
    public AuthorDTO getAuthorById(Integer id) {
        return repository.findById(id).map(mapper::toAuthorDto).orElseThrow(() -> new DatabaseFetchException("Could not find Author entity with id " + id));
    }

    @Override
    public UserDTO getUserById(Integer id) {
        return repository.findById(id).map(mapper::toUserDto).orElseThrow(() -> new DatabaseFetchException("Could not find Author entity with id " + id));
    }

    @Override
    public User getByUsernameButGetUser(String username) {
        return repository.findByUsername(username).orElseThrow(() -> new DatabaseFetchException("Could not find User entity with username " + username));
    }

    @Override
    public void delete(Integer id) {
        if (!repository.existsById(id)) {
            throw new DatabaseFetchException("CANT DELETE! Not found user with id = " + id);
        }
        repository.deleteById(id);
    }

    @Override
    public void update(UserUpdateRequest request) {
        User userToUpdate = repository.findById(request.getId()).orElseThrow(() -> new DatabaseFetchException("Could not find User entity with id " + request.getId()));
        User updatedUser = mapper.toUserEntity(userToUpdate, request);
        repository.save(updatedUser);
    }

    @Override
    public UserDTO getById(Integer id) {
        return repository.findById(id).map(mapper::toUserDto).orElseThrow(() -> new DatabaseFetchException("Could not find User entity with id " + id));
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
