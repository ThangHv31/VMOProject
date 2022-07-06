package com.vmo.vmoproject.repository;

import com.vmo.vmoproject.entities.BrandGrossProfitAuditLog;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BrandGrossProfitAuditLogRepository extends MongoRepository<BrandGrossProfitAuditLog, String> {
    BrandGrossProfitAuditLog findBrandGrossProfitAuditLogByBrandId(String brand_id);
}
