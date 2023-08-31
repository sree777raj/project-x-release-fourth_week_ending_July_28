package com.prakat.projectx.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class SkillTest {

    @Mock
    private Audit audit;

    @Test
    void testGetSkillId() {
        Skill skill = new Skill();
        skill.setSkillId(1L);
        skill.setSkillName("java");
        assertEquals(1L, skill.getSkillId());

    }

    @Test
    void testGetSkillName() {
        Skill skill = new Skill();
        skill.setSkillName("Java");

        assertEquals("Java", skill.getSkillName());
    }

    @Test
    void testGetAudit() {
        Skill skill = new Skill();

        assertNull(skill.getAudit());
    }

    @Test
    void testSetAudit() {
        Skill skill = new Skill();

        skill.setAudit(audit);

        assertEquals(audit, skill.getAudit());
    }

    @Test
    void testAuditIdColumnMapping() {
        Skill skill = new Skill();
        assertNull(skill.getAudit());
        skill.setAudit(audit);
        assertEquals(audit, skill.getAudit());
    }


    @Test
    public void testDeleted() {
        Boolean expectedStatus = true;
        Skill skill = new Skill();
        skill.setDeleted(expectedStatus);
        Boolean actualStatus = skill.isDeleted();
        Assertions.assertEquals(expectedStatus, actualStatus);
    }

}
