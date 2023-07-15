package com.professionallawnservices.app.repos;

import com.professionallawnservices.app.models.data.Customer;
import com.professionallawnservices.app.models.data.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface UsersRepo extends JpaRepository<Users, Long> {


    @Query("select u from Users u where u.username = :username")
    Users findByUsername(@Param("username") String username);



}
