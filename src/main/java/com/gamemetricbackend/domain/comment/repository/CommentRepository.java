package com.gamemetricbackend.domain.comment.repository;

import com.gamemetricbackend.domain.comment.entitiy.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment,Long>, CommentRepositoryQuery {

}
