package com.api.ecommerce.services;

import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

public interface CSVExporterService {

     void exportToCSV(HttpServletResponse response);
}
