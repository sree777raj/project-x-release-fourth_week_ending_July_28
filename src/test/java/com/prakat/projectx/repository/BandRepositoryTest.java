package com.prakat.projectx.repository;

import com.prakat.projectx.entity.Band;
import com.prakat.projectx.repo.BandRepository;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class BandRepositoryTest {
    @Autowired
    private BandRepository bandRepository;

    @Test
    public void bandCreate(){
        //Given
        Band band=new Band();
        band.setBandId(1L);
        band.setBandName("Band name");
        //When
        Band savedaBand=bandRepository.save(band);
        //Then
        Assertions.assertThat(savedaBand).isNotNull();
        Assertions.assertThat(savedaBand.getBandId()).isEqualTo(1L);

    }
}
