package com.h2o.demo.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.h2o.demo.model.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long>{
	Page<Review> findByClothingId(Long clothingId, Pageable pageable);
    Optional<Review> findByIdAndClothingId(Long id, Long clothingId);
    
    @Query(value = "SELECT avg(case when count(*)>=5 then rating else -1 end) from review where clothing_id == ?1", nativeQuery = true)
    Double getAverageRating(long clothingId);
    
    
}
