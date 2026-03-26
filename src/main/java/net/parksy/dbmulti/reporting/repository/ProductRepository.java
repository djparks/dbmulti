package net.parksy.dbmulti.reporting.repository;

import net.parksy.dbmulti.config.ReportingRepository;
import net.parksy.dbmulti.reporting.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

@ReportingRepository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByName(String name);
}
