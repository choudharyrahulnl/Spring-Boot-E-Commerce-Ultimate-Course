package com.api.ecommerce.mappers;

import com.api.ecommerce.dtos.UserStatusDto;
import com.api.ecommerce.entities.users.User;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface UserStatusMapper {
    User toEntity(UserStatusDto userStatusDto);

    UserStatusDto toDto(User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    User partialUpdate(UserStatusDto userStatusDto, @MappingTarget User user);
}