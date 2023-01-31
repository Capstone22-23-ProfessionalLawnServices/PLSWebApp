package com.professionallawnservices.app.repos;

import com.professionallawnservices.app.models.data.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactRepo extends JpaRepository<Contact, Long> {

    @Query("select e from Contact e where lower(e.contactName) like lower(concat('%', :search, '%')) ")
    List<Contact> findByContactName(@Param("search") String search);
}
