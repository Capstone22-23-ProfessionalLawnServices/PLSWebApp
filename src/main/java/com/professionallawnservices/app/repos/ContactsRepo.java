package com.professionallawnservices.app.repos;

import com.professionallawnservices.app.models.Contacts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactsRepo extends JpaRepository<Contacts, Long> {

    @Query("select e from Contacts e where lower(e.contactName) like lower(concat('%', :search, '%')) ")
    List<Contacts> findByContactName(@Param("search") String search);
}
