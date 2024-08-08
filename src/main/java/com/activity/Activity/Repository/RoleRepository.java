package com.activity.Activity.Repository;

import com.activity.Activity.Model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
    public interface RoleRepository extends JpaRepository<Role,Long> {
        Optional<Role> findByName(String name);
    }
