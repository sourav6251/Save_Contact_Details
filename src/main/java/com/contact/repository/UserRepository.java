package com.contact.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.contact.db.Users;

public interface UserRepository extends JpaRepository<Users, Long> {

    Users findByEmail(String email);

    public boolean existsByEmail(String email);

    @Query("SELECT c FROM Users c WHERE c.user_id = :UserId")
    Users findByUserId(@Param("UserId") long user_id);

    @Query("SELECT u.password FROM Users u WHERE u.email = :email")
    String findPasswordByEmail(@Param("email") String email);

    @Query("SELECT u.password FROM Users u WHERE u.user_id = :id")
    String findPasswordByUserId(@Param("id") Long id);

}
