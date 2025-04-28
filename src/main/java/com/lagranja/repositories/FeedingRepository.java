package com.lagranja.repositories;

import com.lagranja.models.Feeding;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedingRepository extends JpaRepository<Feeding, Long> {
}
