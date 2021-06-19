package com.ndrewcoding.ichnofossil.service;

import com.ndrewcoding.ichnofossil.model.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface PostService {
    Optional<Post> findById(Long id);

    Post save(Post post);

    Page<Post> findALl(Pageable pageable);
}
