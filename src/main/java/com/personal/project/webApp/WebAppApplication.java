package com.personal.project.webApp;


import com.personal.project.webApp.dao.CustomerDAO;
import com.personal.project.webApp.dao.ProductDAO;
import com.personal.project.webApp.entity.Customer;
import com.personal.project.webApp.entity.Product;
import com.personal.project.webApp.service.CustomerService;
import com.personal.project.webApp.service.ProductService;
import com.personal.project.webApp.storage.StorageProperties;
import com.personal.project.webApp.storage.StorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class WebAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebAppApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(CustomerService customerDAO, ProductService productDAO) {
		return runner -> {

			//saveCustomer(customerDAO);
			addProductstoCart(customerDAO,productDAO);

		};
	}

	@Transactional
	private void addProductstoCart(CustomerService customerDAO, ProductService productDAO) {
		List<Product> products = productDAO.findAll();
		System.out.println("1///");
		Customer customer = customerDAO.findById(1);
		System.out.println("2///");
		Product product = productDAO.findById(7);
		System.out.println("3///");
		customerDAO.addToCart(1,product);

	}

	private void saveCustomer(CustomerService customerDAO) {
		Customer customer = new Customer("redouan", "oubijjane", "casablanca", "oubijjane8@gmail.com", "redouan");

		customerDAO.save(customer);
	}


}
