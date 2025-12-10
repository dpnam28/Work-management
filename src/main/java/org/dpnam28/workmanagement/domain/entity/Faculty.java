package org.dpnam28.workmanagement.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "faculties")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Faculty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "faculty_name", nullable = false)
    private String facultyName;

    @Column(name = "faculty_code", nullable = false, unique = true)
    private String facultyCode;

    @Builder.Default
    @OneToMany(mappedBy = "faculty")
    private List<Major> majors = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "faculty")
    private List<Teacher> teachers = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "faculty")
    private List<Subject> subjects = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "faculty")
    private List<Plan> plans = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "faculty")
    private List<Meeting> meetings = new ArrayList<>();
}
