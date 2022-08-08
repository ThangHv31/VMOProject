package com.vmo.vmoproject.repository;

import com.vmo.vmoproject.entities.BrandGrossProfit;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface BrandGrossProfitRepository extends MongoRepository<BrandGrossProfit, String> {
    BrandGrossProfit findBrandGrossProfitByBrandId(String brand_id);
    List<BrandGrossProfit> findBrandGrossProfitsByPayeeName(String payeeName);
}
