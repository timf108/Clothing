package com.h2o.demo.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.h2o.demo.exception.ClothingException;
import com.h2o.demo.exception.ReviewException;
import com.h2o.demo.model.Clothing;
import com.h2o.demo.model.ClothingColor;
import com.h2o.demo.model.ClothingSize;
import com.h2o.demo.model.ClothingType;
import com.h2o.demo.model.Review;
import com.h2o.demo.repository.ClothingRepository;
import com.h2o.demo.repository.ReviewRepository;

@RestController
@RequestMapping("/clothes")
public class ClothingController {
	@Autowired
	private ClothingRepository clothingRepository;
	
	@Autowired
    private ReviewRepository reviewRepository;
	
	@GetMapping()
    public Page<Clothing> getAllClothes(Pageable pageable) {
        return clothingRepository.findAll(pageable);
    }
	
	@GetMapping("/search")
    public Page<Clothing> getClothes(@Valid @RequestBody Clothing clothingRequest,Pageable pageable) {
		 double avgRating = reviewRepository.getAverageRating(clothingRequest.getId());
		 int rating = (int)Math.ceil(avgRating);
	        if ((clothingRequest.isHotProduct() && rating < 4)
	        		|| (!clothingRequest.isHotProduct() && rating >= 4)) {
	        	new ClothingException("no matched clothing for hot/non-hot flag");
	        }
	        
	        if (rating != clothingRequest.getRating()) {
	        	new ClothingException("no matched clothing for rating");
	        }
		return clothingRepository.findMatchedOnes(clothingRequest.getDescription(),clothingRequest.getColor() ,clothingRequest.getType(),
				clothingRequest.getBrand(),clothingRequest.getSize(),pageable);
    }
	
	
	@PostMapping()
    public Clothing createPost(@Valid @RequestBody Clothing clothing) {
        return clothingRepository.save(clothing);
    }
	
	@DeleteMapping("/{clothId}")
    public ResponseEntity<?> deleteCloth(@PathVariable Long clothingId) {
        return clothingRepository.findById(clothingId).map(clothing -> {
        	clothingRepository.delete(clothing);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ClothingException("clothingId " + clothingId + " not found"));
    }
	
}
