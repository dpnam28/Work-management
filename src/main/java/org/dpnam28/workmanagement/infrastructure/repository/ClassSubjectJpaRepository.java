package org.dpnam28.workmanagement.infrastructure.repository;

import org.dpnam28.workmanagement.domain.entity.ClassSubject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClassSubjectJpaRepository extends JpaRepository<ClassSubject, Long> {
    List<ClassSubject> findBySemester_Id(Long semesterId);
}
