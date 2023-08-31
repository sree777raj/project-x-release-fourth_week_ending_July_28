package com.prakat.projectx.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name="job_type", schema = "timesheet")
@SQLDelete(sql = "UPDATE timesheet.job_type SET deleted = true WHERE job_type=?")
@Where(clause = "deleted=false")
@Setter
@Getter
public class JobType {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "job_id")
    private Long jobId;
	
    @NotNull
    @Column(name = "job_type")
    private String jobType;

    @Column(name = "deleted",columnDefinition = "BOOLEAN default false")
    private boolean deleted;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "audit_id")
    private Audit audit;

}
