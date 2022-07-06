package com.vmo.vmoproject.repository;

import com.vmo.vmoproject.entities.BrandGrossProfit;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BrandGrossProfitRepository extends MongoRepository<BrandGrossProfit, String> {
 BrandGrossProfit findBrandGrossProfitByBrandId(String brand_id);
}
