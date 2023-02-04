package com.api.ecommerce.mappers;

import com.api.ecommerce.dtos.UserDto;
import com.api.ecommerce.dtos.UserListDto;
import com.api.ecommerce.dtos.UserListPaginationDto;
import com.api.ecommerce.entities.users.User;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface UserListPaginationMapper {

    @Autowired
    UserListMapper userListMapper = null;

    User toEntity(UserListPaginationDto userDto);

    UserListPaginationDto toDto(int currentPage, long totalItems, int totalPages, int itemsPerPage, List<UserListDto> users);

    UserListPaginationDto toDto(User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    User partialUpdate(UserDto userDto, @MappingTarget User user);


}