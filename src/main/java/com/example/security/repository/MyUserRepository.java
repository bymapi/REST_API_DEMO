package com.example.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.security.entities.MyUser;
import java.util.Optional;


@Repository
public interface MyUserRepository extends JpaRepository<MyUser,Integer>{

    Optional<MyUser> findByUserName(String userName);
    Boolean exisexistsByUserName(String userName);

}
