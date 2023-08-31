package com.prakat.projectx.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDate;
import java.time.LocalTime;
@Setter
@Getter
@Entity
@Table(name = "timesheet_entry", schema = "timesheet")
@SQLDelete(sql = "UPDATE timesheet.timesheet_entry SET deleted = true WHERE timesheet_entry_id=?")
@Where(clause = "deleted=false")
public class TimesheetEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "timesheet_entry_id")
    private Long timesheetEntryId;

    @Column(name = "entry_date")
    private LocalDate entryDate;

    @Column(name = "time_sheet_date")
    private LocalDate timeSheetDate;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "project_id")
    private Project project;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "job_id")
    private JobType jobType;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "activity_id")
    private Activity activity;

    @Column(name = "comments")
    private String comments;

    @Column(name = "no_of_hours")
    private double noOfHours;


    @Column(name = "deleted", columnDefinition = "BOOLEAN default false")
    private boolean deleted;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "audit_id")
    private Audit audit;

    @Column(name = "start_time")
    private LocalTime startTime;
    @Column(name = "end_time")
    private LocalTime endTime;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(
                    name = "date",
                    referencedColumnName = "date"),
            @JoinColumn(
                    name = "employee_id",
                    referencedColumnName = "employee_id")
    })
    private Timesheet timesheet;

//    @ManyToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "timesheet_id",nullable = false)
//    private Timesheet timesheet;
}
