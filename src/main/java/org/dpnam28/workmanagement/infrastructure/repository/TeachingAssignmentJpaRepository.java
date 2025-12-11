package org.dpnam28.workmanagement.infrastructure.repository;

import org.dpnam28.workmanagement.domain.entity.TeachingAssignment;
import org.dpnam28.workmanagement.domain.entity.TeachingAssignmentId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeachingAssignmentJpaRepository extends JpaRepository<TeachingAssignment, TeachingAssignmentId> {
    List<TeachingAssignment> findByClassSubject_Id(Long classSubjectId);
    boolean existsByTeacher_IdAndClassSubject_Id(Long teacherId, Long classSubjectId);
}
