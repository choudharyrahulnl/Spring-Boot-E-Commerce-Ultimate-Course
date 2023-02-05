package com.api.ecommerce.services.impl.user;

import com.api.ecommerce.dtos.user.UserDto;
import com.api.ecommerce.repositories.user.UserRepository;
import com.api.ecommerce.services.user.CSVExporterService;
import com.api.ecommerce.services.user.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import java.io.IOException;
import java.util.List;

@Service
@Transactional
@Slf4j
public class CSVExporterServiceImpl extends AbstractExporter implements CSVExporterService {

    private UserRepository userRepository;
    private UserService userService;

    public CSVExporterServiceImpl(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @Override
    public void exportToCSV(HttpServletResponse response) {
        super.setResponseHeader(response, "csv", "text/csv");

        // Create CSV
        try {
            ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);

            // Write Header
            String[] csvHeader = {"Id", "Email", "FirstName", "LastName", "Roles", "Enabled"};
            csvWriter.writeHeader(csvHeader);

            // Write Data
            String[] nameMapping = {"id", "email", "firstName", "lastName", "roles", "enabled"};
            List<UserDto> users = userService.findAll();
            for (UserDto userDto : users) {
                csvWriter.write(userDto, nameMapping);
            }

            csvWriter.close();

        } catch (IOException e) {
            log.error("ExportToCSV Error: " + e.getMessage());
        }

    }
}
