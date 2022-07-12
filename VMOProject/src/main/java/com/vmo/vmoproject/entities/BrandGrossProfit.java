package com.vmo.vmoproject.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.Instant;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "brand_gross_profit")
public class BrandGrossProfit {
    @Id
    private String id;
    @Field(name = "brand_id")
    private String brandId;
    @Field(name = "bank_code")
    private String bankCode;
    @Field(name = "payee_name")
    private String payeeName;
    @Field(name = "settlement_report_emails")
    private List<String> settlementReportEmails;
    @Field(name = "daily_report_emails")
    private List<String> dailyReportEmails;
    @Field(name = "gross_profit")
    private GrossProfit grossProfit;
    @Field(name = "tax_id")
    private String taxId;
    @Field(name = "company_info")
    private Company companyInfo;
    @Field(name = "enabled")
    private Boolean enabled = true;
    @Field(name = "created_date")
    private Instant createdDate;
    @Field(name = "updated_date")
    private Instant updatedDate;
}
