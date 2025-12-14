package org.dpnam28.workmanagement.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "task_assignments")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskAssignment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_task", nullable = false)
    private Task task;

    @ManyToOne
    @JoinColumn(name = "assign_by")
    private Teacher assignBy;

    @ManyToOne
    @JoinColumn(name = "assigned_to_teacher")
    private Teacher assignedToTeacher;

    @ManyToOne
    @JoinColumn(name = "assigned_to_faculty")
    private Faculty assignedToFaculty;

    @Column(name = "assign_date", nullable = false)
    private LocalDateTime assignDate;
}
