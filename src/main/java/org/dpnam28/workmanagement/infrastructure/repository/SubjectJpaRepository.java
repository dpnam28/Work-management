package org.dpnam28.workmanagement.infrastructure.repository;

import org.dpnam28.workmanagement.domain.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubjectJpaRepository extends JpaRepository<Subject, Long> {
    boolean existsBySubjectName(String subjectName);
}
