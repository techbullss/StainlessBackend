package com.example.StainlesSteel.Repository;

import com.example.StainlesSteel.Tables.UserTable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<UserTable, Long> {
    Optional<UserTable> findByEmail(String email);

}