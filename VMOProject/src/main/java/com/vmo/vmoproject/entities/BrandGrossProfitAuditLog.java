package com.vmo.vmoproject.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "brand_gross_profit_audit_log")
public class BrandGrossProfitAuditLog {
    @Field(name = "event")
    private String event;
    @Field(name = "brand_id")
    private String brandId;
    @Field(name = "gross_profit_old")
    private GrossProfit grossProfitOld;
    @Field(name = "gross_profit_new")
    private GrossProfit grossProfitNew;
    @Field(name = "audit_date")
    private Instant auditDate = Instant.now();
}
