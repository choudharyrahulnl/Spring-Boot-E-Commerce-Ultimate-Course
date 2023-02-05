package com.api.ecommerce.services.user;

import jakarta.servlet.http.HttpServletResponse;

public interface PdfExporterService {

     void exportToPdf(HttpServletResponse response);
}
