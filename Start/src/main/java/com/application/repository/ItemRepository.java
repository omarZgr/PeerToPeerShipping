package com.application.repository;

import com.application.entity.ItemEntity;
import com.application.front.response.sender.AllItemsResponse;
import com.application.front.response.traveler.ItemTravelerResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface ItemRepository extends JpaRepository<ItemEntity,Long> {

    Page<ItemEntity> findAllByVilleDepartAndVilleDestinationAndIsEnable(
            Pageable pageable
            ,String villeDepart
            ,String villeDestination,boolean isEnable) ;

    Optional<ItemEntity> findByIdAndIsEnable(long itemId, boolean isEnable);

    Page<ItemEntity> findAllByUserId(Pageable pageable, Integer id);
}
