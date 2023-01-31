package com.api.ecommerce.mappers;

import com.api.ecommerce.dtos.UserDto;
import com.api.ecommerce.entities.User;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface UserMapper {
    User toEntity(UserDto userDto);

    @Mapping(target = "password", ignore = true)
    UserDto toDto(User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    User partialUpdate(UserDto userDto, @MappingTarget User user);
}