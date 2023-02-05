package com.api.ecommerce.mappers.user;

import com.api.ecommerce.dtos.user.UserDto;
import com.api.ecommerce.dtos.user.UserListDto;
import com.api.ecommerce.entities.user.User;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface UserListMapper {
    User toEntity(UserListDto userDto);

    UserListDto toDto(User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    User partialUpdate(UserDto userDto, @MappingTarget User user);
}