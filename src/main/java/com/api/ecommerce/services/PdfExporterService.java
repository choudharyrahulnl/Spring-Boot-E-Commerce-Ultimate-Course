package com.api.ecommerce.services;

import jakarta.servlet.http.HttpServletResponse;

public interface PdfExporterService {

     void exportToPdf(HttpServletResponse response);
}
