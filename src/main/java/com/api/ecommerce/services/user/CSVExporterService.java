package com.api.ecommerce.services.user;

import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

public interface CSVExporterService {

     void exportToCSV(HttpServletResponse response);
}
