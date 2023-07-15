package com.professionallawnservices.app.repos;

import com.professionallawnservices.app.models.data.Customer;
import com.professionallawnservices.app.models.data.Users;
import com.professionallawnservices.app.models.data.WorkerAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface WorkerAccountRepo extends JpaRepository<WorkerAccount, Long> {


    @Query("select w from WorkerAccount w where w.worker.workerId  = :workerId")
    WorkerAccount findWorkerAccountByWorkerId(@Param("workerId") Long workerId);

}
