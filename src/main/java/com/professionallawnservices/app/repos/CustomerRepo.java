package com.professionallawnservices.app.repos;

import com.professionallawnservices.app.models.data.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface CustomerRepo extends JpaRepository<Customer, Long> {


    @Query("select c from Customer c where c.customerName like %:customerName%")
    ArrayList<Customer> findByCustomerNameLike(@Param("customerName") String customerName);

}
