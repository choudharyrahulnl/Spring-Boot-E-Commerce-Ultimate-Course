package com.api.ecommerce.dtos.user;

import lombok.Data;

import java.util.List;

@Data
public class UserListPaginationDto {

    private final int currentPage;
    private final int totalItems;
    private final int totalPages;
    private final int itemsPerPage;
    private final List<UserListDto> users;
}
