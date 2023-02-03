package com.api.ecommerce.services;

import jakarta.servlet.http.HttpServletResponse;

public interface ExcelExporterService {

     void exportToExcel(HttpServletResponse response);
}
