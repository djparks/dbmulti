package net.parksy.dbmulti.service;

import net.parksy.dbmulti.entity.Report;
import net.parksy.dbmulti.repository.ReportRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ReportServiceIntegrationTest {

    @Autowired
    private ReportService reportService;

    @Autowired
    private ReportRepository reportRepository;

    @BeforeEach
    public void setUp() {
        reportRepository.deleteAll();
    }

    @Test
    public void testFindAll() {
        // Given
        Report report = Report.builder()
                .report("Test Report")
                .build();
        reportRepository.save(report);

        // When
        List<Report> reports = reportService.findAll();

        // Then
        assertThat(reports).isNotEmpty();
        assertThat(reports.get(0).getReport()).isEqualTo("Test Report");
    }

    @Test
    public void testUpdateFirstReportName() {
        // Given
        Report report = Report.builder()
                .report("Original Report Name")
                .build();
        reportRepository.save(report);
        reportRepository.flush();

        // When
        reportService.updateFirstReportName();

        // Then
        List<Report> reports = reportRepository.findAll();
        assertThat(reports).isNotEmpty();
        assertThat(reports.get(0).getReport()).isEqualTo("Updated Report Name");
    }
}
