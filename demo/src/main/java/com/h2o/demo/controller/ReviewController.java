package com.h2o.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.h2o.demo.exception.ClothingException;
import com.h2o.demo.exception.ReviewException;
import com.h2o.demo.model.Review;
import com.h2o.demo.repository.ClothingRepository;
import com.h2o.demo.repository.ReviewRepository;

import javax.validation.Valid;

@RestController
@RequestMapping("/clothes")
public class ReviewController {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ClothingRepository clothingRepository;

    @GetMapping("/{clothingId}/reviews")
    public Page<Review> getAllReviewsByClothId(@PathVariable (value = "clothingId") Long clothingId,
                                                Pageable pageable) {
        return reviewRepository.findByClothingId(clothingId, pageable);
    }

    @PostMapping("/{clothingId}/reviews")
    public Review createReview(@PathVariable (value = "clothingId") Long clothingId,
                                 @Valid @RequestBody Review review) {
    	
    	 if(!clothingRepository.existsById(clothingId)) {
             throw new ClothingException("clothingId " + clothingId + " not found");
         }
        return clothingRepository.findById(clothingId).map(clothing -> {
        	review.setClothing(clothing);
            return reviewRepository.save(review);
        }).orElseThrow(() -> new ClothingException("clothingId " + clothingId + " not found"));
    }

    @PutMapping("/{clothingId}/reviews/{reviewId}")
    public Review updateReview(@PathVariable (value = "clothingId") Long clothingId,
                                 @PathVariable (value = "reviewId") Long reviewId,
                                 @Valid @RequestBody Review reviewRequest) {
        if(!clothingRepository.existsById(clothingId)) {
            throw new ClothingException("clothingId " + clothingId + " not found");
        }
        double avgRating = reviewRepository.getAverageRating(clothingId);
        int rating = (int)Math.ceil(avgRating);
        if (rating > 0 && rating <= 2) {
        	new ReviewException("review has been prohibited and no future review is allowed!");
        }
        return reviewRepository.findById(reviewId).map(review -> {
        	review.setText(reviewRequest.getText());
        	review.setRating(reviewRequest.getRating());
            return reviewRepository.save(review);
        }).orElseThrow(() -> new ReviewException("reviewId " + reviewId + "not found"));
    }

    @DeleteMapping("/{clothingId}/reviews/{reviewId}")
    public ResponseEntity<?> deleteComment(@PathVariable (value = "clothingId") Long clothingId,
                              @PathVariable (value = "reviewId") Long reviewId) {
        return reviewRepository.findByIdAndClothingId(reviewId, clothingId).map(review -> {
        	reviewRepository.delete(review);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ReviewException("review not found with id " + reviewId + " and clothingId " + clothingId));
    }
}