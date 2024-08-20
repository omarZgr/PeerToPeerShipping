package com.application.repository;

import com.application.entity.ReclamationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ReclamationRepository extends JpaRepository<ReclamationEntity,Long> {
}
