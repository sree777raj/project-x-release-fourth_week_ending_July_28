package com.prakat.projectx.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Table(name="project_type", schema = "timesheet")
@SQLDelete(sql = "UPDATE timesheet.project_type SET deleted = true WHERE project_type=?")
@Where(clause = "deleted=false")
@Setter
@Getter
public class ProjectType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "project_type")
    private String projectType;

    @Column(name = "deleted")
    private boolean deleted;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "audit_id")
    private Audit audit;

}
