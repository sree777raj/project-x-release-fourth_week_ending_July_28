package com.prakat.projectx.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import jakarta.validation.constraints.NotNull;

@Getter
@Setter
@Entity
@Table(name="skill", schema = "timesheet")
@SQLDelete(sql = "UPDATE timesheet.roles SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
public class Skill {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "skill_id")
    private Long skillId;

    @NotNull
    @Column(name = "skill_name")
    private String skillName;

    @Column(name = "deleted",columnDefinition = "BOOLEAN default false")
    private boolean deleted;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "audit_id")
    private Audit audit;

 

}
