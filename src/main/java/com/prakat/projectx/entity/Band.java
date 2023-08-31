package com.prakat.projectx.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Getter
@Setter
@Table(name="band", schema = "timesheet")
@SQLDelete(sql = "UPDATE timesheet.band SET deleted = true WHERE band_id=?")//added for soft delete
@Where(clause = "deleted=false")//added for soft delete

public class Band {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "band_id")
    private Long bandId;

    @NotNull
    @Column(name = "band_name" )
    private String bandName;

    @Column(name = "deleted", columnDefinition = "BOOLEAN default false")
    private boolean deleted;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "audit_id")
    private Audit audit;

}
