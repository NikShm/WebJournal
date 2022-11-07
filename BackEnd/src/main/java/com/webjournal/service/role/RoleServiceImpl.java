package com.webjournal.service.role;

import com.webjournal.entity.Role;
import com.webjournal.enums.RoleType;
import com.webjournal.exception.DatabaseFetchException;
import com.webjournal.repository.RoleRepository;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository repository;

    public RoleServiceImpl(RoleRepository repository) {
        this.repository = repository;
    }

    @Override
    public Role getRoleByRoleType(RoleType type) {
        return repository.findByRole(type).orElseThrow(() -> new DatabaseFetchException("Could not find entity Role with name " + type.name()));
    }
}
