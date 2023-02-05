package com.api.ecommerce.mappers.user;

import com.api.ecommerce.dtos.user.UserDto;
import com.api.ecommerce.dtos.user.UserListDto;
import com.api.ecommerce.dtos.user.UserListPaginationDto;
import com.api.ecommerce.entities.user.User;
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