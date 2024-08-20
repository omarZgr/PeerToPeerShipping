package com.application.repository;

import com.application.entity.ConfirmationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface ConfirmationRepository extends JpaRepository<ConfirmationEntity,Long> {
    Optional<ConfirmationEntity> findByItemId(long itemId);

    Optional<ConfirmationEntity> findByUserTravelerIdAndUserSenderIdAndConfirmSender(long userTravelerId, Integer id, boolean b);
}
