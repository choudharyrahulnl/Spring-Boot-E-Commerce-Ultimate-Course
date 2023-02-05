package com.api.ecommerce.mappers.user;

import com.api.ecommerce.dtos.user.UserDto;
import com.api.ecommerce.entities.user.User;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface UserMapper {
    User toEntity(UserDto userDto);

    @Mapping(target = "password", ignore = true)
    UserDto toDto(User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    User partialUpdate(UserDto userDto, @MappingTarget User user);
}