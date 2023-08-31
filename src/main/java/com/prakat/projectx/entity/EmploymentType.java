package com.prakat.projectx.entity;

import com.prakat.projectx.enums.EmployeesStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name="employment_type_table", schema = "timesheet")
@SQLDelete(sql = "UPDATE timesheet.employment_type_table SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
@Getter
@Setter
public class EmploymentType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="employment_id")
    private Long id;

    @NotNull
    @Column(name="employment_type")
    private String employmentType;

    @Column(name = "employment_status",columnDefinition = "VARCHAR(255) default 'ACTIVE'")
    @Enumerated(EnumType.STRING)
    private EmployeesStatus status;

    @Column(name = "deleted",columnDefinition = "BOOLEAN default false")
    private boolean deleted;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "audit_id")
    private Audit audit;
}
