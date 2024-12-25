package com.fullstack.repository;

import com.fullstack.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Integer> {

    // CUSTOM METHODS

    Customer findByCustEmailIdAndCustPassword(String custEmailId, String custPassword);
}
