package org.dpnam28.workmanagement.infrastructure.repository;

import org.dpnam28.workmanagement.domain.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportJpaRepository extends JpaRepository<Report, Long> {
}
