package com.application.repository;

import com.application.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,Long> {

    Optional<UserEntity> findByEmail(String email) ;

    UserEntity findByItemsId(long itemId);

  //  Optional<UserEntity> findByEmailAndIdIsNot(String email, Integer id);
}
