package com.prakat.projectx;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest
@ExtendWith(MockitoExtension.class)
class ProjectXApplicationTests {
	@Test
	void contextLoads() {
		assertTrue(true);
	}


}


