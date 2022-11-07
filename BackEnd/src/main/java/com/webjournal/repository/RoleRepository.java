package com.webjournal.repository;

import com.webjournal.entity.Role;
import com.webjournal.enums.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/*
@author Емілія
@project WebJournal
@class RoleRepository
@version 1.0.0
@since 02.10.2022 - 16.57
*/
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByRole(RoleType role);
}
