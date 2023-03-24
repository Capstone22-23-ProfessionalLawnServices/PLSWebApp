package com.professionallawnservices.app.repos;

import com.professionallawnservices.app.models.data.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Repository
public interface JobRepo extends JpaRepository<Job, Long> {

    @Query(value = "SELECT j FROM Job j WHERE j.customer.customerId = :customerId")
    ArrayList<Job> getAllJobByCustomerId(@Param("customerId") Long customerId);

    @Query("select j from Job j where j.scheduledDate is not null and j.scheduledDate = :scheduleDate")
    ArrayList<Job> getAllByScheduledDateIsNotNullAndScheduledDate(@Param("scheduleDate") Date scheduleDate);

    @Transactional
    @Modifying
    @Query("update Job j set j.scheduledDate = :scheduleDate where j.jobId = :jobId")
    int updateScheduledDateByJobId(@Param("scheduleDate")Date scheduledDate, @Param("jobId")long jobId);
}
