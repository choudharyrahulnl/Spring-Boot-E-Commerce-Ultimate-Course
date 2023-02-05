package com.api.ecommerce.services.user;

import jakarta.servlet.http.HttpServletResponse;

public interface ExcelExporterService {

     void exportToExcel(HttpServletResponse response);
}
