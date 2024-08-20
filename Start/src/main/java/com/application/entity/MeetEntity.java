package com.application.entity;

import com.application.common.BaseEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "_meet")
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@SuperBuilder
public class MeetEntity extends BaseEntity {

    private Date dateMeeting ;
    private boolean validSender ;
    private boolean validTraveler ;
    private String location ;
    private String photo ;
    private boolean confirmMeeting ;

    private LocalDateTime dateAsked ;
    private LocalDateTime dateConfirmation ;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    private UserEntity userSender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    private UserEntity userTraveler;

    @ManyToOne(fetch = FetchType.LAZY)
    private ItemEntity item;

}
