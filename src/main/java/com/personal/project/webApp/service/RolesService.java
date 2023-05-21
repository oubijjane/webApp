package com.personal.project.webApp.service;

import com.personal.project.webApp.entity.Product;
import com.personal.project.webApp.entity.Roles;

import java.util.List;

public interface RolesService {
    List<Roles> findAll();

    Roles findById(int id);

    Roles save(Roles role);

    void deleteById(int id);
}
