package com.personal.project.webApp.service;

import com.personal.project.webApp.dao.CustomerDAO;
import com.personal.project.webApp.entity.Customer;
import com.personal.project.webApp.entity.OrderList;
import com.personal.project.webApp.entity.Product;
import org.assertj.core.api.Assert;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {

    @Mock
    private CustomerDAO customerDAO;

    private CustomerService underTest;

    @BeforeEach
    void setUp() {
        underTest = new CustomerServiceImpl(customerDAO);
    }



    @Test
    void Should_findAll() {
        //when
        underTest.findAll();

        //then
        verify(customerDAO).findAll();


    }

    @Test
    void Check_indById_if_the_id_exist() {

        //given
        Customer customer = new Customer(
                "zakaria",
                "oubijjane",
                "casablanca",
                "oubijjane48@gmail.com",
                "test123");

        //when
        when(this.customerDAO.findById(customer.getId())).thenReturn(Optional.of(customer));
        Customer byId = underTest.findById(customer.getId());

        //then
        verify(customerDAO).findById(customer.getId());
        assertThat(customerDAO.findById(customer.getId()).isPresent());
        assertThat(byId).isEqualTo(customerDAO.findById(customer.getId()).get());
    }
    @Test
    void Check_intById_if_the_id_does_not_exist() {
        //given
        int id = 0;

        //then
        assertThatThrownBy(() -> underTest.findById(id)).hasMessage("Did not find customer id - " + id);

    }

    @Test

    void save() {
        //given
        Customer customer = new Customer(
                "zakaria",
                "oubijjane",
                "casablanca",
                "oubijjane48@gmail.com",
                "test123");



        //when
        underTest.save(customer);

        //then
        ArgumentCaptor<Customer> customerCaptor = ArgumentCaptor.forClass(Customer.class);

        verify(customerDAO).save(customerCaptor.capture());

        Customer capturedCustomer = customerCaptor.getValue();

        assertThat(capturedCustomer).isEqualTo(customer);

    }

    @Test
    void Check_deleteById() {
        //given
        int id = 0;


        //when
        underTest.deleteById(id);

        //then
        verify(customerDAO).deleteById(id);

    }

    @Test
    void findByEmail_if_email_exist() {
        //given
        Customer customer = new Customer(
                "zakaria",
                "oubijjane",
                "casablanca",
                "oubijjane48@gmail.com",
                "test123");


        //when
        when(this.customerDAO.findByEmail(customer.getEmail())).thenReturn(customer);
        Customer customer1 = underTest.findByEmail(customer.getEmail());
        verify(customerDAO).findByEmail(customer.getEmail());

        //then
        verify(customerDAO).findByEmail(customer.getEmail());
        assertThat(customer1).isEqualTo(customer);

    }

    @Test
    void findCustomerByEmail() {
        Customer customer = new Customer(
                "zakaria",
                "oubijjane",
                "casablanca",
                "oubijjane48@gmail.com",
                "test123");


        //when
        when(this.customerDAO.findByEmail(customer.getEmail())).thenReturn(customer);
        Customer customer1 = underTest.FindCustomerByEmail(customer.getEmail());
        verify(customerDAO).findByEmail(customer.getEmail());

        //then
        verify(customerDAO).findByEmail(customer.getEmail());
        assertThat(customer1).isEqualTo(customer);

    }

    @Test
    void getOrders() {
        //given
        Customer customer = new Customer(
                "zakaria",
                "oubijjane",
                "casablanca",
                "oubijjane48@gmail.com",
                "test123");
        List<OrderList> orderLists = new ArrayList<>();
        orderLists.add(new OrderList());
        orderLists.add(new OrderList());


        customer.setOrders(orderLists);
        when(this.customerDAO.findById(customer.getId())).thenReturn(Optional.of(customer));
        List<OrderList> result = underTest.getOrders(customer.getId());

        verify(customerDAO, times(2)).findById(customer.getId());
        assertThat(result.size()).isEqualTo(2);
    }

}