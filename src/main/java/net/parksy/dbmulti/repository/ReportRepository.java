package net.parksy.dbmulti.repository;

import net.parksy.dbmulti.config.ReportingRepository;
import net.parksy.dbmulti.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;

@ReportingRepository
public interface ReportRepository extends JpaRepository<Report, Long> {
}
