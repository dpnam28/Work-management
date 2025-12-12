package org.dpnam28.workmanagement.infrastructure.repository;

import org.dpnam28.workmanagement.domain.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleJpaRepository extends JpaRepository<Schedule, Long> {
    boolean existsByClassSubject_IdAndTime(Long classSubjectId, String time);
    boolean existsByClassSubject_IdAndTimeAndIdNot(Long classSubjectId, String time, Long id);
}
