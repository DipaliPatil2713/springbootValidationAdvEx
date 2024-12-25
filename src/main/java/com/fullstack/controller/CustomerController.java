package com.fullstack.controller;

import com.fullstack.exception.RecordNotFoundException;
import com.fullstack.model.Customer;
import com.fullstack.service.ICustomerService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/customers")
//@Slf4j
public class CustomerController {

    @Autowired
    private ICustomerService customerService;

    @PostMapping("/signup")
    public ResponseEntity<Customer> signUp(@Valid @RequestBody Customer customer) {
//        log.info("Trying to signUp for Customer :" + customer.getCustName());
        return new ResponseEntity<>(customerService.signUp(customer), HttpStatus.CREATED);
    }

    @GetMapping("/signin/{custEmailId}/{custPassword}")
    public ResponseEntity<Boolean> signIn(@PathVariable String custEmailId, @PathVariable String custPassword) {
        return new ResponseEntity<>(customerService.signIn(custEmailId, custPassword), HttpStatus.OK);
    }

    @GetMapping("/findbyid/{custId}")
    public ResponseEntity<Optional<Customer>> findById(@PathVariable int custId) {
        return new ResponseEntity<>(customerService.findById(custId), HttpStatus.OK);

    }

    @GetMapping("/findall")
    public ResponseEntity<List<Customer>> findAll() {
        return new ResponseEntity<>(customerService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/findbyname{custName}")
    public ResponseEntity<List<Customer>> findByName(@PathVariable String custName) {
        return new ResponseEntity<>(customerService.findAll().stream().filter(customer -> customer.getCustName().equals(custName)).toList(), HttpStatus.OK);
    }

    @GetMapping("/findbyemailid")
    public ResponseEntity<List<Customer>> findByEmailId(@RequestParam String custEmailId) {
        return new ResponseEntity<>(customerService.findAll().stream().filter(customer -> customer.getCustEmailId().equals(custEmailId)).toList(), HttpStatus.OK);

    }

    @GetMapping("/findbycontactnumber")
    public ResponseEntity<List<Customer>> findByContactNumber(@RequestParam long custContactNumber) {
        return new ResponseEntity<>(customerService.findAll().stream().filter(customer -> customer.getCustContactNumber() == custContactNumber).toList(), HttpStatus.OK);
    }

    @GetMapping("/findbyDOB/{custDOB}")
    public ResponseEntity<List<Customer>> findByCustDOB(@PathVariable String custDOB) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        return new ResponseEntity<>(customerService.findAll().stream().filter(customer -> simpleDateFormat.format(customer.getCustDOB()).equals(custDOB)).toList(), HttpStatus.OK);

    }

    @PostMapping("/saveall")
    public ResponseEntity<List<Customer>> saveAll(@Valid @RequestBody List<Customer> customerList) {
        return new ResponseEntity<>(customerService.saveAll(customerList), HttpStatus.OK);
    }

    @GetMapping("/sortbyid")
    public ResponseEntity<List<Customer>> sortById() {
        return new ResponseEntity<>(customerService.findAll().stream().sorted(Comparator.comparing(Customer::getCustId)).toList(), HttpStatus.OK);
    }

    @GetMapping("/sortbyname")
    public ResponseEntity<List<Customer>> sortByName() {
        return new ResponseEntity<>(customerService.findAll().stream().sorted(Comparator.comparing(Customer::getCustId)).toList(), HttpStatus.OK);
    }

    @GetMapping("/sortbyaccbal")
    public ResponseEntity<List<Customer>> sortByAccBalance() {
        return new ResponseEntity<>(customerService.findAll().stream().sorted(Comparator.comparing(Customer::getCustAccountBalance)).toList(), HttpStatus.OK);
    }

    @GetMapping("/sortbydob")
    public ResponseEntity<List<Customer>> sortByDOB() {
        return new ResponseEntity<>(customerService.findAll().stream().sorted(Comparator.comparing(Customer::getCustDOB)).toList(), HttpStatus.OK);
    }

    @GetMapping("/findbyanyinput")
    public ResponseEntity<List<Customer>> findByAnyInput(@RequestParam String input) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        return new ResponseEntity<>(customerService.findAll().stream().filter(customer -> customer.getCustName().equals(input)
                || simpleDateFormat.format(customer.getCustDOB()).equals(input)
                || String.valueOf(customer.getCustId()).equals(input)
                || customer.getCustEmailId().equals(input)
                || String.valueOf(customer.getCustContactNumber()).equals(input)).toList(), HttpStatus.OK);

    }

    @GetMapping("/checkloaneligibility/{custId}")
    public ResponseEntity<String> checkLoanEligibility(@PathVariable int custId) {

        Customer customer = customerService.findById(custId).orElseThrow(() -> new RecordNotFoundException("Customer does not exist"));

// here we use ternary operator to reduced the line of code
        return new ResponseEntity<>(customer.getCustAccountBalance() >= 50000.00 ? "Eligible for the loan" : "Not eligible for loan", HttpStatus.OK);
    }
    @PutMapping("/update/{custId}")
    public ResponseEntity<Customer>update(@PathVariable int custId ,@Valid @RequestBody Customer customer){
        Customer customer1= customerService.findById(custId).orElseThrow(()-> new RecordNotFoundException("Customer #Id does not exist"));

        customer1.setCustName(customer.getCustName());
        customer1.setCustEmailId(customer.getCustEmailId());
        customer1.setCustAddress(customer.getCustAddress());
        customer1.setCustPassword(customer.getCustPassword());
        customer1.setCustDOB(customer.getCustDOB());
        customer1.setCustContactNumber(customer.getCustContactNumber());
        customer1.setCustAccountBalance(customer.getCustAccountBalance());

        return new ResponseEntity<>(customerService.update(customer1),HttpStatus.CREATED);
    }
    @DeleteMapping("/deletebyid/{custId}")
    public  ResponseEntity<String>deleteByCustId(@PathVariable int custId){
        customerService.deleteById(custId);

        return new ResponseEntity<>("Data deleted Successfully",HttpStatus.OK);
    }

    @DeleteMapping("/deleteall")
    public ResponseEntity<String>deleteAll(){
        customerService.deleteAll();
        return new ResponseEntity<>("All Data Deleted Successfully",HttpStatus.OK);
    }
    @PatchMapping("/changeaddress/{custId}/{custAddress}")
    public ResponseEntity<Customer>changeAddress(@PathVariable int custId, @PathVariable String custAddress){
        Customer customer1= customerService.findById(custId).orElseThrow(()-> new RecordNotFoundException("Customer #Id does not exist"));

        customer1.setCustAddress(custAddress);

        return new ResponseEntity<>(customerService.update(customer1),HttpStatus.OK);
    }
}
