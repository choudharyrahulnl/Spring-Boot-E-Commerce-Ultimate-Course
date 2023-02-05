package com.api.ecommerce.services.user;

import com.api.ecommerce.dtos.user.RoleDto;

import java.util.List;

public interface RoleService {

    public List<RoleDto> findAll();
}
