package com.personal.project.webApp.service;

import com.personal.project.webApp.dao.RolesDAO;
import com.personal.project.webApp.entity.Customer;
import com.personal.project.webApp.entity.Roles;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RolesServiceImplTest {

    @Mock
    private RolesDAO rolesDAO;

    private RolesService underTest;

    @BeforeEach
    void setUp() {
        underTest = new RolesServiceImpl(rolesDAO);
    }

    @Test
    void Should_findAll() {

        underTest.findAll();

        verify(rolesDAO).findAll();
    }

    @Test
    void Check_findById_if_the_id_exist() {

        Roles roles = new Roles();
        int id = roles.getId();

        when(this.rolesDAO.findById(id)).thenReturn(Optional.of(roles));
        Roles byId = underTest.findById(id);

        verify(rolesDAO).findById(id);
        assertThat(rolesDAO.findById(id).isPresent()).isTrue();
        assertThat(byId).isEqualTo(rolesDAO.findById(id).get());
    }

    @Test
    void Check_findById_if_the_id_does_not_exist() {

        int id = 0;

        assertThatThrownBy(() -> underTest.findById(id)).hasMessage("Did not find role id - " + id);
    }

    @Test
    void save() {

        Roles roles = new Roles();

        underTest.save(roles);

        ArgumentCaptor<Roles> rolesArgumentCaptor = ArgumentCaptor.forClass(Roles.class);
        verify(rolesDAO).save(rolesArgumentCaptor.capture());

        Roles rolesCaptured = rolesArgumentCaptor.getValue();

        assertThat(rolesCaptured).isEqualTo(roles);

    }

    @Test
    void deleteById() {
        underTest.deleteById(anyInt());

        verify(rolesDAO).deleteById(anyInt());
    }

    @Test
    void findByEmail() {
        Customer customer = new Customer();
        customer.setEmail("test@gmail.com");

        Roles roles1 = new Roles();
        roles1.setCustomer(customer);

        Roles roles2 = new Roles();
        roles2.setCustomer(customer);

        Roles roles3 = new Roles();
        roles3.setCustomer(customer);

        List<Roles> rolesList = new ArrayList<>();
        rolesList.add(roles1);
        rolesList.add(roles1);
        rolesList.add(roles1);

        when(this.rolesDAO.findByEmail(customer.getEmail())).thenReturn(rolesList);

        List<Roles> result = underTest.findByEmail(customer.getEmail());

        verify(rolesDAO).findByEmail(customer.getEmail());

        assertThat(result).isEqualTo(rolesList);
    }
}