package com.ndrewcoding.ichnofossil.model.repository;

import com.ndrewcoding.ichnofossil.model.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    @Query("SELECT p FROM Post p JOIN p.tags t WHERE t IN :tags")
    List<Post> findPostsByTag(@Param("tags") List<String> tags);
}
