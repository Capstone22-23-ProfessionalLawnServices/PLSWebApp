package com.professionallawnservices.app.repos;

import com.professionallawnservices.app.models.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobsRepo extends JpaRepository<Job, Long> {
}
