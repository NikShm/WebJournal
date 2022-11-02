package com.webjournal.service.role;

import com.webjournal.entity.Role;
import com.webjournal.enums.RoleType;

public interface RoleService {
    Role getRoleByRoleType(RoleType type);
}
