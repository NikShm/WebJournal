package com.webjournal.service.user;

import com.webjournal.dto.PageDTO;
import com.webjournal.dto.FollowDTO;
import com.webjournal.dto.PageDTO;
import com.webjournal.dto.search.AuthorSearch;
import com.webjournal.dto.search.SearchDTO;
import com.webjournal.dto.user.AuthorDTO;
import com.webjournal.dto.user.UserDTO;
import com.webjournal.entity.MailToken;
import com.webjournal.entity.Role;
import com.webjournal.entity.User;
import com.webjournal.enums.RoleType;
import com.webjournal.exception.RegistrationException;
import com.webjournal.enums.SortDirection;
import com.webjournal.exception.DatabaseFetchException;
import com.webjournal.enums.SortDirection;
import com.webjournal.mail.context.AccountVerificationMailContext;
import com.webjournal.mail.service.mail.MailServiceImpl;
import com.webjournal.mail.service.mailtoken.MailTokenServiceImpl;
import com.webjournal.mapper.UserMapper;
import com.webjournal.repository.UserRepository;
import com.webjournal.utils.QueryHelper;
import org.springframework.data.domain.Page;
import com.webjournal.security.payload.request.RegistrationRequest;
import com.webjournal.service.role.RoleServiceImpl;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Value;
import com.webjournal.utils.QueryHelper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.IOException;
import java.util.ArrayList;
import javax.mail.MessagingException;
import java.io.IOException;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository repository;
    private final RoleServiceImpl roleService;
    private final UserMapper mapper;
    private final EntityManager entityManager;
    private final PasswordEncoder passwordEncoder;
    private final MailTokenServiceImpl mailTokenService;
    private final MailServiceImpl mailService;
    @Value("${site.base.url.http}")
    private String baseURL;

    public UserServiceImpl(UserRepository repository, RoleServiceImpl roleService, UserMapper mapper, EntityManager entityManager, PasswordEncoder passwordEncoder, MailTokenServiceImpl mailTokenService, MailServiceImpl mailService) {
        this.repository = repository;
        this.roleService = roleService;
        this.mapper = mapper;
        this.entityManager = entityManager;
        this.passwordEncoder = passwordEncoder;
        this.mailTokenService = mailTokenService;
        this.mailService = mailService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return repository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User with email " + email + " was not found."));
    }

    @Override
    public Boolean checkIfUserExistsByUsername(String username) {
        return repository.existsByUsername(username);
    }

    @Override
    public Boolean checkIfUserExistsByEmail(String email) {
        return repository.existsByEmail(email);
    }

    @Override
    public void create(RegistrationRequest registrationRequest) throws TemplateException, MessagingException, IOException {
        if (checkIfUserExistsByUsername(registrationRequest.getUsername())) {
            throw new RegistrationException("Username is already taken");
        }
        if (checkIfUserExistsByEmail(registrationRequest.getEmail())) {
            throw new RegistrationException("Email is already in use");
        }
        User createdUser = new User();
        createdUser.setUsername(registrationRequest.getUsername());
        createdUser.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
        createdUser.setEmail(registrationRequest.getEmail());
        createdUser.setAccountVerified(false);
        createdUser.setBio(registrationRequest.getBio());

        Role role = roleService.getRoleByRoleType(RoleType.AUTHOR);
        if (registrationRequest.getRole() != null) {
            switch (registrationRequest.getRole()) {
                case "ADMIN" ->
                        role = roleService.getRoleByRoleType(RoleType.ADMIN);
                case "MODERATOR" ->
                        role = roleService.getRoleByRoleType(RoleType.MODERATOR);
            }
        }
        createdUser.setRole(role);

        createdUser = repository.save(createdUser);
        sendRegistrationConfirmationEmail(createdUser);
    }

    private void sendRegistrationConfirmationEmail(User user) throws TemplateException, MessagingException, IOException {
        MailToken mailToken = mailTokenService.createMailToken();
        mailToken.setUser(user);
        mailTokenService.saveMailToken(mailToken);
        AccountVerificationMailContext mailContext = new AccountVerificationMailContext();
        mailContext.init(user);
        mailContext.buildVerificationUrl(baseURL, mailToken.getToken());

        mailService.sendMail(mailContext);
    }

    @Override
    public void verifyUser(String token) {
        if (!StringUtils.hasText(token)) {
            throw new RegistrationException("Token is empty");
        }
        MailToken mailToken = mailTokenService.getByToken(token);
        if (mailToken == null || !token.equals(mailToken.getToken()) || mailToken.getExpiresAt().isBefore(LocalDateTime.now())) {
            mailTokenService.deleteToken(mailToken);
            throw new RegistrationException("Mail token is not valid");
        }
        User user = repository.findById(mailToken.getUser().getId()).orElse(null);
        if (user == null) {
            throw new RegistrationException("This user doesn't exist");
        }
        user.setAccountVerified(true);
        repository.save(user);

        mailTokenService.deleteToken(mailToken);
    }

    @Override
    public void delete(Integer id) {
        if (!repository.existsById(id)) {
            throw new DatabaseFetchException("CANT DELETE! Not found user with id = " + id);
        }
        repository.deleteById(id);
    }

    @Override
    public void update(UserDTO dto) {
        User userToUpdate = repository.findById(dto.getId()).orElseThrow(() -> new DatabaseFetchException("Could not find User entity with id " + dto.getId()));
        User updatedUser = mapper.toUserEntity(userToUpdate, dto);
        repository.save(updatedUser);
    }

    @Override
    public UserDTO get(Integer id) {
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
