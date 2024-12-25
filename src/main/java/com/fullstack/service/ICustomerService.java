package com.fullstack.service;

import com.fullstack.model.Customer;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;

public interface ICustomerService {

   Customer signUp(Customer customer);

   boolean signIn(String custEmailId, String custPassword);

   Optional<Customer>findById(int custId);

   List<Customer>findAll();

   Customer update(Customer customer);

   void deleteById(int custId);

   void deleteAll();

   List<Customer> saveAll(@Valid List<Customer> customerList);
}
