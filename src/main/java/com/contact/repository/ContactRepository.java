package com.contact.repository;

import com.contact.db.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ContactRepository extends JpaRepository<Contact, Long> {


    @Query("SELECT c FROM Contact c WHERE c.user.user_id = :userId")
    List<Contact> findContactsByUserId(@Param("userId") long userId);

    @Query("SELECT c FROM Contact c WHERE c.contact_id = :ContactId")
    Contact findByContactId(@Param("ContactId") long contact_id);

}
