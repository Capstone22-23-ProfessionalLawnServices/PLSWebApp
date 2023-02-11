package com.professionallawnservices.app.repos;

import com.professionallawnservices.app.models.data.Contact;
import com.professionallawnservices.app.models.data.Help;
import com.professionallawnservices.app.models.data.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface JobRepo extends JpaRepository<Job, Long> {

    @Query(value = "SELECT j FROM Job j WHERE j.customer.customerId = ?1")
    ArrayList<Job> getAllJobByCustomerId(@Param("customerId") Long customerId);
}
