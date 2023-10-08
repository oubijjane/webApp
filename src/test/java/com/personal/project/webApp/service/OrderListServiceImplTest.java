package com.personal.project.webApp.service;

import com.personal.project.webApp.dao.OrderListDAO;
import com.personal.project.webApp.entity.OrderList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderListServiceImplTest {

    @Mock
    private OrderListDAO orderListDAO;


    private OrderListService underTest;

    @BeforeEach
    void setUp() {
        underTest = new OrderListServiceImpl(orderListDAO);
    }

    @Test
    void Should_findAll() {
        //when
        underTest.findAll();

        //then
        verify(orderListDAO).findAll();
    }

    @Test
    void Check_findById_if_the_id_exist() {
        //given
        OrderList orderList = new OrderList(1, null, null);
        int id = orderList.getId();
        //when
        when(this.orderListDAO.findById(id)).thenReturn(Optional.of(orderList));
        OrderList byId = underTest.findById(id);

        //then
        verify(orderListDAO).findById(id);
        assertThat(orderListDAO.findById(id).isPresent()).isTrue();
        assertThat(byId).isEqualTo(orderListDAO.findById(id).get());
    }

    @Test
    void Check_findById_if_the_id_does_not_exist() {
        //given
        int id = 0;

        //then
        assertThatThrownBy(() -> underTest.findById(id)).hasMessage("Did not find orderList id - " + id);
    }

    @Test
    void save() {
        //given
        OrderList orderList = new OrderList(1, null, null);

        //when
        underTest.save(orderList);

        //then
        ArgumentCaptor<OrderList> argumentCaptor = ArgumentCaptor.forClass(OrderList.class);
        verify(orderListDAO).save(argumentCaptor.capture());

        OrderList orderListCaptured = argumentCaptor.getValue();

        assertThat(orderListCaptured).isEqualTo(orderList);

    }

    @Test
    void deleteById() {
        //when
        underTest.deleteById(0);

        //then
        verify(orderListDAO).deleteById(0);
    }
}