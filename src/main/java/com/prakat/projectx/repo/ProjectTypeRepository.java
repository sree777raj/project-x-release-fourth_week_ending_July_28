package com.prakat.projectx.repo;

import com.prakat.projectx.entity.Project;
import com.prakat.projectx.entity.ProjectType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectTypeRepository extends JpaRepository<ProjectType, Long> {
}
