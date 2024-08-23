package com.application.repository;

import com.application.entity.CommentaireEntity;
import com.application.entity.MeetEntity;
import com.application.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface MeetRepository extends JpaRepository<MeetEntity,Long> {
    Optional<MeetEntity> findByItemId(long itemId);
    Optional<MeetEntity> findByIdAndConfirmMeeting(long itemId, boolean confirmMeeting);
    Page<MeetEntity> findAllByUserSenderEquals(Pageable pageable, UserEntity userEntitySender) ;
    Page<MeetEntity> findAllByUserSenderEqualsAndConfirmMeeting(Pageable pageable, UserEntity userEntitySender, boolean confirmMeeting) ;

    List<MeetEntity> findAllByUserTravelerId(long userTravelerId) ;


    List<MeetEntity> findByItemIdAndConfirmMeeting(long itemId, boolean b);

    Optional<MeetEntity> findByItemIdAndUserSenderIdAndUserTravelerId(long itemId, Integer id, Integer id1);
}
