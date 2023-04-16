package com.professionallawnservices.app.repos;


import com.professionallawnservices.app.models.data.Help;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface HelpRepo extends JpaRepository<Help, Long> {

    @Query(value = "SELECT h FROM Help h WHERE h.job.jobId = ?1")
    ArrayList<Help> getAllHelpByJobId(@Param("jobId") Long jobId);

    @Query(value = "SELECT h FROM Help h WHERE h.worker.workerId = ?1")
    ArrayList<Help> getAllHelpByWorkerId(@Param("workerId") Long workerId);

    @Query(value = "SELECT h FROM Help h WHERE h.worker.workerId = ?1 AND h.job.jobId = ?2")
    ArrayList<Help> getHelpByWorkerIdAndJobId(@Param("workerId") Long workerId,
                                              @Param("jobId") Long jobId);
}
