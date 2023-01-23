package com.professionallawnservices.app.repos;

import com.professionallawnservices.app.models.Jobs;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobsRepo extends JpaRepository<Jobs, Long> {
}
