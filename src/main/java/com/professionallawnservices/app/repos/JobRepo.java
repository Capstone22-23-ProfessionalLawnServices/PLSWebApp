package com.professionallawnservices.app.repos;

import com.professionallawnservices.app.models.data.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.ArrayList;

@Repository
public interface JobRepo extends JpaRepository<Job, Long> {

    @Query(value = "SELECT j FROM Job j WHERE j.customer.customerId = :customerId")
    ArrayList<Job> getAllJobByCustomerId(@Param("customerId") Long customerId);

    @Query("select j from Job j where j.scheduledDate is not null and j.scheduledDate = :scheduleDate and " +
            "j.startTime is null and j.endTime is null")
    ArrayList<Job> getCalendarJobsByDate(@Param("scheduleDate") Date scheduleDate);

    @Query("select j from Job j where j.scheduledDate between :scheduleDate and :yesterdayDate and j.endTime is null")
    ArrayList<Job> findMissedJobs(
            @Param("scheduleDate") Date scheduleDate,
            @Param("yesterdayDate") Date yesterdayDate
    );

    @Query("select j from Job j where j.startTime is not null and j.endTime is null")
    ArrayList<Job> findActiveJobs();

    @Transactional
    @Modifying
    @Query("update Job j set j.startTime = CURRENT_TIME where j.jobId = :jobId")
    void startSession(@Param("jobId") long jobId);

    @Transactional
    @Modifying
    @Query("update Job j set j.endTime = CURRENT_TIME where j.jobId = :jobId")
    void endSession(@Param("jobId") long jobId);


}
