package com.prakat.projectx.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Getter
@Setter
@Entity
@Table(name="project_hours", schema = "timesheet")
@SQLDelete(sql = "UPDATE timesheet.project_hours SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
public class ProjectHours {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "band_id")
    private Band band;
    @Column(name = "approved_hours",columnDefinition = "INT default 0")
    private Integer approvedHours;

    @Column(name = "deleted",columnDefinition = "BOOLEAN default false")
    private boolean deleted;

    @OneToOne
    @JoinColumn(name = "audit_id")
    private Audit audit;
}
