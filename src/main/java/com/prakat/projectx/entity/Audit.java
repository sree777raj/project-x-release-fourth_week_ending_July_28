package com.prakat.projectx.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name="audit", schema = "timesheet")
@Getter
@Setter
public class Audit {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "audit_id")
    private Long auditId;
	
	@Column(name = "created_by_emp_id")
    private Long createdByEmpId;

	@Column(name = "created_on")
    private LocalDate createdOn;

	@Column(name = "updated_by_emp_id")
    private Long updatedByEmpId;
	
    @Column(name = "updated_on")
    private LocalDateTime updatedOn;
    
    @Column(name = "process_name",columnDefinition = "VARCHAR(255) default 'N/A'")
    private String processName;
    
    @Column(name = "created_by_db_user",columnDefinition = "BIGINT default 0")
    private Long createdByDbUser;

    @Column(name = "updated_by_db_user",columnDefinition = "BIGINT default 0")
    private Long updatedByDbUser;

}
