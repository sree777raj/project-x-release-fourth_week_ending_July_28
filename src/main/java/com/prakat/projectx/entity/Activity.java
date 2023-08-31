package com.prakat.projectx.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name="activity", schema = "timesheet")
@SQLDelete(sql = "UPDATE timesheet.activity SET deleted = true WHERE activity_id=?")//added for soft delete
@Where(clause = "deleted=false")//added for soft delete
@Getter
@Setter
public class Activity {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "activity_id")
    private Long activityId;

    @NotNull
    @Column(name = "activity_type")
    private String activityType;

    @Column(name = "deleted", columnDefinition = "BOOLEAN default false")
    private boolean deleted;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "audit_id")
    private Audit audit;
}
