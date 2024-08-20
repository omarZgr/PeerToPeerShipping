package com.application.repository;

import com.application.entity.CommentaireEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CommentaireRepository extends JpaRepository<CommentaireEntity,Long> {
}
