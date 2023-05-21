package com.personal.project.webApp.dao;

import com.personal.project.webApp.entity.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolesDAO extends JpaRepository<Roles, Integer> {
}
