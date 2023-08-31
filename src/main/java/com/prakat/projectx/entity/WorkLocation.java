package com.prakat.projectx.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import jakarta.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@Table(name="work_location", schema = "timesheet")
@SQLDelete(sql = "UPDATE timesheet.work_location SET deleted = true WHERE location_id=?")
@Where(clause = "deleted=false")
public class WorkLocation {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "location_id")
    private Long locationId;
	
    @NotNull
    @Column(name = "location_name")
    private String locationName;

    @Column(name = "deleted",columnDefinition = "BOOLEAN default false")
    private boolean deleted;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "audit_id")
    private Audit audit;

}
