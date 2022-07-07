package com.vmo.vmoproject.repository;

import com.vmo.vmoproject.entities.BrandGrossProfitAuditLog;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface BrandGrossProfitAuditLogRepository extends MongoRepository<BrandGrossProfitAuditLog, String> {
    List<BrandGrossProfitAuditLog> findBrandGrossProfitAuditLogsByBrandId(String brand_id);
}
