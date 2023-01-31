package com.api.ecommerce.services;

import com.api.ecommerce.dtos.RoleDto;

import java.util.List;

public interface RoleService {

    public List<RoleDto> findAll();
}
