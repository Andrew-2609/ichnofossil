package com.ndrewcoding.ichnofossil.model.repository;

import com.ndrewcoding.ichnofossil.model.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

}
