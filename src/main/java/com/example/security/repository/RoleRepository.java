package com.example.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.security.entities.Role;
import java.util.Optional;


@Repository
public interface RoleRepository extends JpaRepository<Role,Integer>{

    Optional<Role> findByRole(String role);

}
