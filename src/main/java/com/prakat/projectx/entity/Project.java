package com.prakat.projectx.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import jakarta.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "project", schema = "timesheet")
@SQLDelete(sql = "UPDATE timesheet.project SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
@Getter
@Setter
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "project_name")
    private String projectName;

    @Column(name = "project_value")
    private String projectValue;

    @Column(name = "approve_budget")
    private String approvedBudget;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    @Column(name = "project_type")
    private String projectType;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "employee_id")
    private List<ProjectHours> projectHours;

    @Column(name = "max_num_hours",columnDefinition = "INT default 0")
    private int maxNumberHours;

    @Column(name = "deleted",columnDefinition = "BOOLEAN default false")
    private boolean deleted;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "audit_id")
    private Audit audit;
}
