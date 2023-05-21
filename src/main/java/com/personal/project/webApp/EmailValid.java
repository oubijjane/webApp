package com.personal.project.webApp;

import com.personal.project.webApp.entity.Customer;
import com.personal.project.webApp.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class EmailValid implements Validator {
    @Autowired
    CustomerService customerService;
    @Override
    public boolean supports(Class<?> clazz) {
        return Customer.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Customer customer = (Customer) target;
        /*for(Customer customer1 : customerService.findAll()) {
            if(customer1.getEmail().equals(customer.getEmail())){
                errors.rejectValue("email", "customer.email","email already exist");
            }
        }*/
        if(customerService.FindCustomerByEmail(customer.getEmail())!=null){
            errors.rejectValue("email", "customer.email","email already exist");
        }

    }
}
