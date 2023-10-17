package com.personal.project.webApp.service;

import com.personal.project.webApp.dao.ProductDAO;
import com.personal.project.webApp.entity.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductDAO productDAO;

    private ProductService underTest;

    @BeforeEach
    void setUp() {
        underTest = new ProductServiceImpl(productDAO);
    }

    @Test
    void findAll() {
        //when
        underTest.findAll();

        //then
        verify(productDAO).findAll();
    }

    @Test
    void Check_findById_if_the_id_exist() {
        Product product = new Product();
        int id = product.getId();

        when(productDAO.findById(id)).thenReturn(Optional.of(product));
        Product byId = underTest.findById(id);

        verify(productDAO).findById(id);
        assertThat(productDAO.findById(id).isPresent()).isTrue();
        assertThat(byId).isEqualTo(productDAO.findById(id).get());


    }
    @Test
    void Check_findById_if_the_id_does_not_exist() {
        int id = 0;
        assertThatThrownBy(() -> underTest.findById(0)).hasMessage("Did not find product id - " + id);
    }

    @Test
    void save() {
        Product product = new Product();

        underTest.save(product);
        ArgumentCaptor<Product> argumentCaptor = ArgumentCaptor.forClass(Product.class);
        verify(productDAO).save(argumentCaptor.capture());

        Product productCaptured = argumentCaptor.getValue();
        assertThat(productCaptured).isEqualTo(product);
    }

    @Test
    void deleteById() {
        underTest.deleteById(anyInt());

        verify(productDAO).deleteById(anyInt());
    }
}