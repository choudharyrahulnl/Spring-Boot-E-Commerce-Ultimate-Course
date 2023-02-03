package com.api.ecommerce.services;

import com.api.ecommerce.dtos.UserDto;
import com.api.ecommerce.dtos.UserListDto;
import com.api.ecommerce.dtos.UserListPaginationDto;
import com.api.ecommerce.dtos.UserStatusDto;
import com.api.ecommerce.entities.User;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface UserService {

    public UserDto save(UserDto userDto);

    public UserDto findById(Long id);

    public UserListPaginationDto findAll(int page, int size, Sort.Direction direction, String sortBy, String keyword);

    // For Export CSV
    List<UserDto> findAll();

    public UserDto update(UserDto userDto);

    public void delete(Long id);

    Boolean existsByEmail(String email);

    UserStatusDto updateStatus(Long id, Boolean status);

    User getUserByEmail(String email);
}
