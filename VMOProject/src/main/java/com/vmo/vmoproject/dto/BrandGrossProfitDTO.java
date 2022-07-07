package com.vmo.vmoproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Instant;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BrandGrossProfitDTO {
    private String id;
    @NotEmpty(message = "brand_id must not be empty!")
    @Size(max = 7, min = 7, message = "brand_id must have 7 digits")
    private String brand_id;
    @NotBlank(message = "bankCode must not be null!")
    private String bankCode;
    private List<String> settlementReportEmails;
    private List<String> dailyReportEmails;
    @Valid
    @NotNull(message = "Gross Profit must not be null!")
    private GrossProfitDTO grossProfit;
    @NotEmpty(message = "tax_id must not be empty!")
    @Size(min = 13, max = 13, message = "tax_id must have 13 digits")
    private String taxId;
    @Valid
    @NotNull(message = "Gross Profit must not be null!")
    private CompanyDTO companyInfo;
    private Boolean enabled;
    private Instant createdDate;
    private Instant updatedDate;
}
