package com.contact.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.contact.db.Users;



public interface UserRepository extends JpaRepository<Users,Long> {

    Users findByEmail(String email);
    public boolean existsByEmail(String email);

    
}
