package com.prakat.projectx.repo;

import com.prakat.projectx.entity.Employee;
import com.prakat.projectx.entity.Timesheet;
import com.prakat.projectx.entity.TimesheetId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface to manage Timesheet entities in the database.
 * It extends JpaRepository to inherit basic CRUD operations from Spring Data JPA.
 */
@Repository
public interface TimesheetRepository extends JpaRepository<Timesheet, Long> {



    //Optional<Timesheet> findByDateAndEmployee(LocalDate date, Employee employee);

   // Optional<Timesheet> findByDateAndEmployee(LocalDate date, Employee employee);

    Optional<Timesheet> findByTimesheetId(TimesheetId timesheetId);
}







