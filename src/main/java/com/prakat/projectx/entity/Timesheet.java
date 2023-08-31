package com.prakat.projectx.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

/**
 * Entity class representing a timesheet in the application.
 * This class is used to map timesheet-related information to the database table.
 */

@Setter
@Getter
@Entity
@Table(name = "timesheet",schema = "timesheet")
@SQLDelete(sql = "UPDATE timesheet.timesheet SET deleted = true WHERE timesheet_id=?")
@Where(clause = "deleted=false")
public class Timesheet {

    @EmbeddedId
    private TimesheetId timesheetId;

//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "timesheet_id", columnDefinition = "bigint")
//    private  Long id;

    @Column(name = "comments")
    private String comments;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "timesheet")
    private List<TimesheetEntry>timesheetEntryId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "audit_id")
    private Audit audit;

    @Column(name = "deleted",columnDefinition = "BOOLEAN default false")
    private boolean deleted;


}
