package com.flasblog.back.persistence.repository;

import com.flasblog.back.persistence.entity.CommentaryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/** CommentaryRepository. */
@Repository
public interface CommentaryRepository extends JpaRepository<CommentaryEntity, String> {}
