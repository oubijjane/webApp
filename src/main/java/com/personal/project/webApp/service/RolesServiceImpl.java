package com.personal.project.webApp.service;

import com.personal.project.webApp.dao.RolesDAO;
import com.personal.project.webApp.entity.Product;
import com.personal.project.webApp.entity.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RolesServiceImpl implements RolesService{

    private RolesDAO rolesDAO;

    @Autowired
    public RolesServiceImpl(RolesDAO rolesDAO) {
        this.rolesDAO = rolesDAO;
    }

    @Override
    public List<Roles> findAll() {
        return rolesDAO.findAll();
    }

    @Override
    public Roles findById(int id) {
        Optional<Roles> result = rolesDAO.findById(id);
        Roles role = null;
        if (result.isPresent()) {
            role = result.get();
        }
        else {
            // we didn't find the employee
            throw new RuntimeException("Did not find role id - " + id);
        }
        return role;
    }

    @Override
    public Roles save(Roles role) {
        return rolesDAO.save(role);
    }

    @Override
    public void deleteById(int id) {
        rolesDAO.deleteById(id);

    }

    @Override
    public List<Roles> findByEmail(String email) {

        return rolesDAO.findByEmail(email);
    }
}
