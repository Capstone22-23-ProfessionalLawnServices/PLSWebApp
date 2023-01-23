package com.professionallawnservices.app.repos;


import com.professionallawnservices.app.models.Help;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HelpRepo extends JpaRepository<Help, Long> {
}
