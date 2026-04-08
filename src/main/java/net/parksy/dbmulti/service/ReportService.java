package net.parksy.dbmulti.service;

import jakarta.persistence.EntityManager;
import net.parksy.dbmulti.entity.Report;
import net.parksy.dbmulti.repository.ReportRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ReportService {
    private final ReportRepository reportRepository;
    private final EntityManager entityManager;

    public ReportService(ReportRepository reportRepository, @Qualifier("reportingEntityManagerFactory") EntityManager entityManager) {
        this.reportRepository = reportRepository;
        this.entityManager = entityManager;
    }

    @Transactional("reportingTransactionManager")
    public List<Report> findAll() {
        return reportRepository.findAll();
    }

    @Transactional("reportingTransactionManager")
    public void updateFirstReportName() {
        List<Report> reports = reportRepository.findAll();
        if (!reports.isEmpty()) {
            Report firstReport = reports.get(0);
            firstReport.setReport("Updated Report Name");
            reportRepository.save(firstReport);
            entityManager.flush();

        }

    }
}
