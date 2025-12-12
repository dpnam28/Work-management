package org.dpnam28.workmanagement.infrastructure.repository;

import org.dpnam28.workmanagement.domain.entity.Semester;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SemesterJpaRepository extends JpaRepository<Semester, Long> {
    boolean existsBySemesterName(String semesterName);
}
