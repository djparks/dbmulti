package net.parksy.dbmulti.service;

import net.parksy.dbmulti.entity.Report;
import net.parksy.dbmulti.repository.ReportRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class ReportService {
    private final ReportRepository reportRepository;
    public ReportService(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    @Transactional
    public List<Report> findAll() {
        return reportRepository.findAll();
    }

    @Transactional
    public void updateFirstReportName() {
        List<Report> reports = reportRepository.findAll();
        if (!reports.isEmpty()) {
            Report firstReport = reports.get(0);
            firstReport.setReport("Updated Report Name");
            reportRepository.save(firstReport);

        }

    }
}
