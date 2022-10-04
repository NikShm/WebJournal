package com.webjournal.repository;

import com.webjournal.entity.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TagRepository extends JpaRepository<Tag, Integer> {
    @Query("SELECT t FROM Post p JOIN p.tags t WHERE p.publishedAt BETWEEN ?1 AND CURRENT_DATE GROUP BY t ORDER BY count(t) DESC")
    List<Tag> findActualTags(Pageable page, LocalDateTime date);
}
