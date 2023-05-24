package com.personal.project.webApp;


import com.personal.project.webApp.dao.CustomerDAO;
import com.personal.project.webApp.dao.ProductDAO;
import com.personal.project.webApp.entity.Customer;
import com.personal.project.webApp.entity.OrderList;
import com.personal.project.webApp.entity.Product;
import com.personal.project.webApp.entity.Roles;
import com.personal.project.webApp.service.*;
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
	public CommandLineRunner commandLineRunner(CustomerService customerService, RolesService rolesService, PasswordEncoder passwordEncoder){

		return runner ->{
			saveCUstomer(customerService, rolesService,passwordEncoder);
		};
	}

	private void saveCUstomer(CustomerService customerService, RolesService rolesService, PasswordEncoder passwordEncoder) {

		//customerService.save(new Customer("admin","user1", "casablanca", "admin@gamil.com","admin123"));
	}

}
