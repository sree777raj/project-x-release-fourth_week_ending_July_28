package com.prakat.projectx.entity;

import com.prakat.projectx.enums.EmployeesStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Setter
@Table(name="employee", schema = "timesheet")
@SQLDelete(sql = "UPDATE timesheet.employee SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "emp_id")
    private String empId;

    @Column(name = "status",columnDefinition = "VARCHAR(255) default 'ACTIVE'")
    @Enumerated(EnumType.STRING)
    private EmployeesStatus status;

    @Column(name = "deleted", columnDefinition = "BOOLEAN default false")
    private boolean deleted;

    @ManyToOne(cascade= CascadeType.ALL)
    @JoinColumn(name = "gender")
    private Gender gender;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "band_id")
    private Band bandId;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(name = "employee_skill_mapping",schema = "timesheet",
            joinColumns = {@JoinColumn(name = "id")},
            inverseJoinColumns = {@JoinColumn(name = "skill_id")})
    private List<Skill> skillId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "location_id")
    private WorkLocation locationId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "employment_type")
    private EmploymentType employmentType;

    @ManyToOne
    @JoinColumn(name = "manager_id")
    private Employee manager;

    @OneToMany(mappedBy = "manager")
    private List<Employee> subordinates = new ArrayList();

    @Column(name = "ctc")
    private String ctc;

    @Column(name = "dob")
    private LocalDate dob;

    @Column(name = "joining_date")
    private LocalDate joiningDate;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "audit_id")
    private Audit audit;



}


