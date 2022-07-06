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
@Document(collection = "brandGrossProfitAuditLog")
public class BrandGrossProfitAuditLog {
    @Field(name = "event")
    private String event;
    @Field(name = "brand_id")
    private String brandId;
    @Field(name = "audit_date")
    private Instant auditDate = Instant.now();
    @Field(name = "gross_profit_old")
    private GrossProfit grossProfitOld;
    @Field(name = "gross_profit_new")
    private GrossProfit grossProfitNew;
}
