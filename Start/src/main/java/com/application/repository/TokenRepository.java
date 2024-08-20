package com.application.repository;

import com.application.entity.TokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<TokenEntity,Long> {


    Optional<TokenEntity> findByTokenAndUserEmail(String token, String email)  ;


}
