package com.prakat.projectx.repo;

import com.prakat.projectx.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProjectRepository extends JpaRepository<Project,Long> {
}
