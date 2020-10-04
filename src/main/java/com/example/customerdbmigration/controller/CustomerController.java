package com.example.customerdbmigration.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.customerdbmigration.model.Customer;
import com.example.customerdbmigration.service.impl.CustomerServiceImpl;

@RestController
@CrossOrigin
public class CustomerController {

    @Autowired
    CustomerServiceImpl customerService;

    @GetMapping("/getCustomerDetails")
    public Customer getCustomer(@RequestParam String username ) throws Exception{
    	Customer result = customerService.getCustomerDetails(username);
    	if(result != null) {
    		return customerService.getCustomerDetails(username);
    	}
    	return null;
    }

    @PostMapping("/createCustomer")
    public String createCustomer(@RequestBody List<Customer> customer ) throws Exception {
        return customerService.saveCustomerDetails(customer);
    }

    @PutMapping("/updateCustomer")
    public String updateCustomer(@RequestBody Customer customer  ) throws Exception {
        return customerService.updateCustomerDetails(customer);
    }

    @DeleteMapping("/deleteCustomer")
    public String deleteCustomer(@RequestParam String username){
        return customerService.deleteCustomer(username);
    }
}