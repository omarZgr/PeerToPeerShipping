package com.application.entity;


import com.application.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "_confirmation")
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@SuperBuilder
public class ConfirmationEntity extends BaseEntity {

    private boolean confirmSender ;
    private boolean confirmTraveler ;

    private LocalDateTime dateConfirmationSender ;
    private LocalDateTime dateConfirmationTraveler ;

    @Column(columnDefinition = "TEXT") // Specifies the column type as TEXT

    private String photo ;
    private String descriptionSender ;
    private String descriptionTraveler ;

    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity userSender;

    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity userTraveler;


    @OneToOne(fetch = FetchType.LAZY)
    private ItemEntity item;
}
