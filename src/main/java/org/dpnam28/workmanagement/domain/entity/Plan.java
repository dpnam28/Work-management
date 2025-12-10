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
@Table(name = "plans")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Plan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "plan_title", nullable = false)
    private String planTitle;

    @Column(name = "plan_description")
    private String planDescription;

    @ManyToOne
    @JoinColumn(name = "created_by")
    private Teacher createdBy;

    @ManyToOne
    @JoinColumn(name = "approved_by")
    private Teacher approvedBy;

    @ManyToOne
    @JoinColumn(name = "id_faculty")
    private Faculty faculty;

    @Column(nullable = false)
    private String status;

    @Builder.Default
    @OneToMany(mappedBy = "plan")
    private List<Task> tasks = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "plan")
    private List<Report> reports = new ArrayList<>();
}
