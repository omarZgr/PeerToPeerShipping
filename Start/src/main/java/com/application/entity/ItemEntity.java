package com.application.entity;


import com.application.common.BaseEntity;
import com.application.enums.StatusItem;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.List;

@Entity
@Table(name = "_item")
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@SuperBuilder
public class ItemEntity extends BaseEntity {

    private String nom ;
    private String description ;
    private String type ;
    private String photo ;
    private String villeDepart ;
    private String villeDestination ;
    private float montant ;
    private StatusItem statusItem ;
    private boolean isEnable ;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    private UserEntity user;

    @OneToMany(mappedBy = "item",fetch = FetchType.LAZY)
    private List<MeetEntity> meetEntities;

    @OneToOne(mappedBy = "item",fetch = FetchType.LAZY)
    private ConfirmationEntity confirmation;
}
