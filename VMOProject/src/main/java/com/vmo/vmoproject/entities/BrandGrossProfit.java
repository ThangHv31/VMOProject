package com.vmo.vmoproject.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "brand_gross_profit")
public class BrandGrossProfit extends BaseEntity {
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

    public BrandGrossProfit(String id, String brandId, String bankCode, String payeeName
            , List<String> settlementReportEmails, List<String> dailyReportEmails, GrossProfit grossProfit
            , String taxId, Company companyInfo, Boolean enabled) {
        super(id);
        this.brandId = brandId;
        this.bankCode = bankCode;
        this.payeeName = payeeName;
        this.settlementReportEmails = settlementReportEmails;
        this.dailyReportEmails = dailyReportEmails;
        this.grossProfit = grossProfit;
        this.taxId = taxId;
        this.companyInfo = companyInfo;
        this.enabled = enabled;
    }
}
