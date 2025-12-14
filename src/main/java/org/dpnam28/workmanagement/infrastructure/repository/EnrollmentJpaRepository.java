package org.dpnam28.workmanagement.infrastructure.repository;

import org.dpnam28.workmanagement.domain.entity.Enrollment;
import org.dpnam28.workmanagement.domain.entity.EnrollmentId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnrollmentJpaRepository extends JpaRepository<Enrollment, EnrollmentId> {
    boolean existsByStudent_IdAndClassSubject_Id(Long studentId, Long classSubjectId);
    long countByClassSubject_Id(Long classSubjectId);
    List<Enrollment> findByClassSubject_Id(Long classSubjectId);
    List<Enrollment> findByStudent_Id(Long studentId);
}
