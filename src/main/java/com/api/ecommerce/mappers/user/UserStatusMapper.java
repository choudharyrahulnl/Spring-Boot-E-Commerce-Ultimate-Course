package com.api.ecommerce.mappers.user;

import com.api.ecommerce.dtos.user.UserStatusDto;
import com.api.ecommerce.entities.user.User;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface UserStatusMapper {
    User toEntity(UserStatusDto userStatusDto);

    UserStatusDto toDto(User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    User partialUpdate(UserStatusDto userStatusDto, @MappingTarget User user);
}