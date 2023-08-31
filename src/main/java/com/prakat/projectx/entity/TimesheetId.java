package com.prakat.projectx.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
@Setter
@Getter
@Embeddable
public class TimesheetId implements Serializable {

    @Column(name = "date")
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;


}
