package com.learning.dao.prod;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.learning.entity.Customer;

public interface ProductRepository extends CrudRepository<Customer, Long> {

    List<Customer> findByLastName(String lastName);
}
