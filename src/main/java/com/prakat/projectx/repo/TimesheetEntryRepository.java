package com.prakat.projectx.repo;

import com.prakat.projectx.entity.TimesheetEntry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimesheetEntryRepository extends JpaRepository<TimesheetEntry,Long> {
}
