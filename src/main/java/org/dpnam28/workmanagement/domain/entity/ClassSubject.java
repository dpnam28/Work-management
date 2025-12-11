package org.dpnam28.workmanagement.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "class_subjects")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClassSubject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "class_code", nullable = false, unique = true)
    private String classCode;

    @ManyToOne
    @JoinColumn(name = "id_subject", nullable = false)
    private Subject subject;

    @ManyToOne
    @JoinColumn(name = "id_semester", nullable = false)
    private Semester semester;

    @Column(name = "max_student", nullable = false)
    private Integer maxStudent;

    @Builder.Default
    @OneToMany(mappedBy = "classSubject")
    private List<Schedule> schedules = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "classSubject")
    private List<TeachingAssignment> teachingAssignments = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "classSubject")
    private List<Enrollment> enrollments = new ArrayList<>();
}
