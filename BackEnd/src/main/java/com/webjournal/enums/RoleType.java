package com.webjournal.enums;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.Set;

/**
 * @author Yuliana
 * @version 1.0.0
 * @project WebJournal
 * @class Role
 * @since 10/18/2022 - 14.34
 **/
public enum RoleType {
    AUTHOR,
    MODERATOR,
    ADMIN;

    public Set<SimpleGrantedAuthority> getGrantedAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority("ROLE_" + this.name()));
    }
}
