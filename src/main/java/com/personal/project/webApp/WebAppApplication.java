package com.personal.project.webApp;


import com.personal.project.webApp.dao.CustomerDAO;
import com.personal.project.webApp.dao.ProductDAO;
import com.personal.project.webApp.entity.Customer;
import com.personal.project.webApp.entity.OrderList;
import com.personal.project.webApp.entity.Product;
import com.personal.project.webApp.entity.Roles;
import com.personal.project.webApp.service.CustomerService;
import com.personal.project.webApp.service.OrderListService;
import com.personal.project.webApp.service.ProductService;
import com.personal.project.webApp.service.RolesServiceImpl;
import com.personal.project.webApp.storage.StorageProperties;
import com.personal.project.webApp.storage.StorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder(BCryptPasswordEncoder.BCryptVersion.$2B);
	}

	@Bean
	public static CommandLineRunner commandLineRunner(CustomerService customerService, RolesServiceImpl rolesService) {

		return runner ->{
			//addRole(customerService,rolesService);
		};
	}

	private static void addRole(CustomerService customerService, RolesServiceImpl rolesService) {
		Customer customer = customerService.findById(1);
		Roles role = new Roles(customer, "ROLE_ADMIN");
		rolesService.save(role);
		role = new Roles(customer, "ROLE_CUSTOMER");
		rolesService.save(role);
	}
}
