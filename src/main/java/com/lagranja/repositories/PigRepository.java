package com.lagranja.repositories;

import com.lagranja.models.Pig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PigRepository extends JpaRepository<Pig, Long> {
}

