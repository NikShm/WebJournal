package com.webjournal.service.user;

import com.webjournal.dto.PageDTO;
import com.webjournal.dto.search.AuthorSearch;
import com.webjournal.dto.search.SearchDTO;
import com.webjournal.dto.user.*;
import com.webjournal.entity.User;
import com.webjournal.enums.RoleType;
import com.webjournal.enums.SortDirection;
import com.webjournal.exception.DatabaseFetchException;
import com.webjournal.exception.ForbiddenException;
import com.webjournal.exception.InvalidRequestException;
import com.webjournal.mapper.UserMapper;
import com.webjournal.repository.UserRepository;
import com.webjournal.service.auth.AuthServiceImpl;
import com.webjournal.service.role.RoleServiceImpl;
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
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private final UserMapper mapper;
    private final RoleServiceImpl roleService;
    private final AuthServiceImpl authService;

    public UserServiceImpl(UserRepository repository, UserMapper mapper, RoleServiceImpl roleService, AuthServiceImpl authService) {
        this.repository = repository;
        this.mapper = mapper;
        this.roleService = roleService;
        this.authService = authService;
    }

    @Override
    public UserDTO getPublicUserById(Integer id) {
        return mapper.toUserDto(getEntityById(id));
    }

    @Override
    public FullUserDTO getFullUserById(Integer id) {
        return mapper.toFullUserDto(getEntityById(id));
    }

    @Override
    public User getEntityById(Integer id) {
        return repository.findById(id).orElseThrow(() -> new DatabaseFetchException("Could not find User entity with id " + id));
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        User userToDelete = getEntityById(id);
        User principal = (User) authService.getCurrentUser();
        if (!principal.getId().equals(userToDelete.getId()) && userToDelete.getRole().getRole() == RoleType.ADMIN) {
            throw new ForbiddenException("Access is denied. You don't have permission to access this resource");
        }
        repository.deleteById(id);
    }

    @Override
    public void update(UserUpdateRequest request) {
        User userToUpdate = getEntityById(request.getId());
        if (!request.getUsername().equals(userToUpdate.getUsername()) && repository.existsByUsername(request.getUsername())) {
            throw new InvalidRequestException("this username is already taken");
        }
        User updatedUser = mapper.toUserEntity(userToUpdate, request);
        repository.save(updatedUser);
    }

    @Override
    public void changeRole(RoleUpdateRequest request) {
        User userToUpdate = getEntityById(request.getId());
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
}
