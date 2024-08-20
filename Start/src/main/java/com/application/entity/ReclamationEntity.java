package com.application.entity;


import com.application.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "_reclamation")
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@SuperBuilder
public class ReclamationEntity extends BaseEntity {

    private String reclamation ;

    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity userTraveler;

    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity userSender;
}
