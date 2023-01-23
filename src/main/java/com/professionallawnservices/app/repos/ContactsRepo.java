package com.professionallawnservices.app.repos;

import com.professionallawnservices.app.models.Contacts;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactsRepo extends JpaRepository<Contacts, Long> {
}
