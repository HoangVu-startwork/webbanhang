package com.example.webbanhang.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.webbanhang.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {}
