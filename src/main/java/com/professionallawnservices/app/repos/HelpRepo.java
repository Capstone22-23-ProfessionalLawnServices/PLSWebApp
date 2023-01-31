package com.professionallawnservices.app.repos;


import com.professionallawnservices.app.models.data.Help;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HelpRepo extends JpaRepository<Help, Long> {


}
