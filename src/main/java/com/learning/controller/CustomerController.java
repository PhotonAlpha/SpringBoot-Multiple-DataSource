package com.learning.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.learning.dao.cust.CustomerRepository;
import com.learning.dao.prod.ProductRepository;
import com.learning.entity.Customer;

@RestController
public class CustomerController {
	@Autowired
	private CustomerRepository custRepository;
	@Autowired
	private ProductRepository prodRepository;
	
	@RequestMapping(path="/customer/{id}", method = RequestMethod.GET)
	@Transactional(transactionManager="transactionManagerPrimary", rollbackFor= Exception.class)
	public String getData(@PathVariable("id") Long id) {
		System.out.println("query:"+id);
		custRepository.save(new Customer("jerry", "rat"));
		prodRepository.save(new Customer("tom", "cat"));
		return "firstDBFirstName:"+custRepository.findById(id).get().getFirstName()+
				"----------secondDBFirstName:"+prodRepository.findById(id).get().getFirstName();
	}
}
