package org.dpnam28.workmanagement.infrastructure.repository;

import org.dpnam28.workmanagement.domain.entity.TaskAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskAssignmentJpaRepository extends JpaRepository<TaskAssignment, Long> {
    List<TaskAssignment> findByAssignedToTeacher_Id(Long teacherId);
    List<TaskAssignment> findByAssignedToFaculty_Id(Long facultyId);
}
