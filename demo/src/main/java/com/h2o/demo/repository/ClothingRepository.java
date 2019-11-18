package com.h2o.demo.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.h2o.demo.model.Clothing;
import com.h2o.demo.model.ClothingColor;
import com.h2o.demo.model.ClothingSize;
import com.h2o.demo.model.ClothingType;

@Repository
public interface ClothingRepository extends JpaRepository<Clothing, Long> {

	 @Query(value = "SELECT * from clothing where description like '?1%' and color == ?2 and type == ?3 and brand == ?4 and size == ?4", nativeQuery = true)
	 Page<Clothing> findMatchedOnes(String decription, ClothingColor color ,ClothingType type,String brand,ClothingSize size,Pageable pageable);
}
