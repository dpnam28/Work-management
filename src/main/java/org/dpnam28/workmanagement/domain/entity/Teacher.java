package org.dpnam28.workmanagement.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "teachers")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Teacher {
    @Id
    @Column(name = "id_teacher")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "id_teacher")
    @ToString.Exclude
    private User user;

    @ManyToOne
    @JoinColumn(name = "faculty_id", nullable = false)
    private Faculty faculty;

    @Column(name = "teacher_code", nullable = false, unique = true)
    private String teacherCode;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private PositionType position;

    @Builder.Default
    @OneToMany(mappedBy = "createdBy")
    private List<Plan> createdPlans = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "approvedBy")
    private List<Plan> approvedPlans = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "assignBy")
    private List<TaskAssignment> assignmentsByMe = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "assignedToTeacher")
    private List<TaskAssignment> assignmentsForMe = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "createdBy")
    private List<Report> reports = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "createdBy")
    private List<Meeting> meetings = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "teacher")
    private List<TeachingAssignment> teachingAssignments = new ArrayList<>();
}
