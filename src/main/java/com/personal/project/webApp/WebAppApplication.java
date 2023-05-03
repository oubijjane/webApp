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
		Product product = productDAO.findById(10);
		Customer customer = customerDAO.findById(1);
		System.out.println(customer);

		System.out.println(customerDAO.getProducts(1));

		//customerDAO.save(customer);
	}

	private void saveCustomer(CustomerDAO customerDAO) {
		Customer customer = new Customer("zakaria", "oubijjane", "casablanca", "oubijjane48@gmail.com");

		customerDAO.save(customer);
	}


}
