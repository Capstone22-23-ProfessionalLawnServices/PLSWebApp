package com.professionallawnservices.app.repos;

import com.professionallawnservices.app.models.data.Worker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkerRepo extends JpaRepository<Worker, Long> {

    @Query("select e from Worker e where lower(e.workerName) like lower(concat('%', :search, '%')) ")
    List<Worker> findByWorkerName(@Param("search") String search);
}
