package com.learning.test;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.learning.dao.cust.CustomerRepository;
import com.learning.dao.prod.ProductRepository;
import com.learning.entity.Customer;


@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootMultiDatasourceApplicationTests {

	@Resource
	private CustomerRepository custRepository;
	@Resource
	private ProductRepository prodRepository;

	@Test
	public void testSave() throws Exception {
		custRepository.save(new Customer("jerry", "rat"));
		prodRepository.save(new Customer("tom", "cat"));
		System.out.println("firstDBFirstName:"+custRepository.findById(1L).get().getFirstName()+
				"----------secondDBFirstName:"+prodRepository.findById(1L).get().getFirstName());
	}

	@Test
	public void contextLoads() {
	}

}
