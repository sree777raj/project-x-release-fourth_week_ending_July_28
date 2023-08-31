package com.prakat.projectx.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prakat.projectx.entity.Band;

public interface BandRepository extends JpaRepository<Band,Long> {

}
