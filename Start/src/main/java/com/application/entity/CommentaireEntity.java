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
@Table(name = "_commentaire")
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@SuperBuilder
public class CommentaireEntity extends BaseEntity {

    private String commentaire ;
    private int rate ;

    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity userTraveler;
}
