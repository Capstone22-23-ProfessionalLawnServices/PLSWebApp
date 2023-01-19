package com.professionallawnservices.app.repos;

import com.professionallawnservices.app.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepo extends JpaRepository<Customer, Long> {
}
