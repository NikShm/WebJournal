package com.webjournal.service.user;

import com.webjournal.dto.FollowDTO;
import com.webjournal.dto.PageDTO;
import com.webjournal.dto.SearchDTO;
import com.webjournal.dto.user.AuthorDTO;
import com.webjournal.dto.user.UserDTO;
import com.webjournal.entity.Post;
import com.webjournal.entity.User;
import com.webjournal.exception.ApiRequestException;
import com.webjournal.enums.SortDirection;
import com.webjournal.exception.DatabaseFetchException;
import com.webjournal.mappers.UserMapper;
import com.webjournal.repository.UserRepository;
import com.webjournal.utils.QueryHelper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository repository;
    private final UserMapper mapper;
    private final EntityManager entityManager;

    public UserServiceImpl(UserRepository repository, UserMapper mapper, EntityManager entityManager) {
        this.repository = repository;
        this.mapper = mapper;
        this.entityManager = entityManager;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User with username " + username + " was not found."));
    }

    @Override
    public Integer create(UserDTO dto) {
        User createdUser = mapper.toUserEntity(new User(), dto);
        return repository.save(createdUser).getId();
    }

    @Override
    public void delete(Integer id) {
        if (!repository.existsById(id)) {
            throw new ApiRequestException("CANT DELETE! Not found user with id = " + id);
        }
        repository.deleteById(id);
    }

    @Override
    public void update(UserDTO dto) {
        User userToUpdate = repository.findById(dto.getId()).orElseThrow(() -> new DatabaseFetchException(dto.getId(), Post.class.getSimpleName()));
        User updatedUser = mapper.toUserEntity(userToUpdate, dto);
        repository.save(updatedUser);
    }

    @Override
    public UserDTO get(Integer id) {
        return repository.findById(id).map(mapper::toUserDto).orElseThrow(() -> new DatabaseFetchException(id, User.class.getSimpleName()));
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

    @Override
    public PageDTO<AuthorDTO> getPage(SearchDTO search) {
        Sort sort = Sort.by(search.getSortField());
        if (search.getSortDirection() == SortDirection.DESC) {
            sort = sort.descending();
        }
        Pageable pageable = PageRequest.of(search.getPage(), search.getPageSize(), sort);
        Page<User> all = repository.findAll((root, query, criteriaBuilder) -> getPredicate(search, criteriaBuilder, root), pageable);
        PageDTO<AuthorDTO> dto = new PageDTO<>();
        dto.setContent(all.stream().map(mapper::toAuthorDto).collect(Collectors.toList()));
        dto.setTotalItem(all.getTotalElements());
        return dto;
    }

    private Predicate getPredicate(SearchDTO search, CriteriaBuilder criteriaBuilder, Root<User> user) {
        List<Predicate> predicates = new ArrayList<>();
        String value = search.getSearch();
        if (value != null) {
            predicates.add(QueryHelper.ilike(user.get("username"), criteriaBuilder, value));
        }
        return predicates.size() == 1 ? predicates.get(0) : criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
