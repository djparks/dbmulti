package net.parksy.dbmulti.repository;

import net.parksy.dbmulti.entity.Report;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ReportRepositoryTest {

    @Autowired
    private ReportRepository reportRepository;

    @Test
    @Transactional("reportingTransactionManager")
    public void testSaveAndReadReport() {
        Report report = Report.builder().build();

        Report savedReport = reportRepository.save(report);
        assertThat(savedReport.getId()).isNotNull();

        Optional<Report> foundReport = reportRepository.findById(savedReport.getId());
        assertThat(foundReport).isPresent();
    }
}
