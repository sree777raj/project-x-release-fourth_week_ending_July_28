package com.prakat.projectx.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import jakarta.validation.constraints.NotNull;

@Entity
@Setter
@Getter
@Table(name="gender",schema = "timesheet")
@SQLDelete(sql = "UPDATE timesheet.table_product SET deleted = true WHERE gender_id=?")
@Where(clause = "deleted=false")
public class Gender {
    @Id
    @Column(name = "gender_id")
    private Character genderId;

    @NotNull
    @Column(name = "gender")
    private String gender;

    @Column(name = "deleted",columnDefinition = "BOOLEAN default false")
    private boolean deleted;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "audit_id")
    private Audit audit;
}
