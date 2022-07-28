package com.vmo.vmoproject.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BrandGrossProfitAuditLogDTO{
    private String event;
    private String brandId;
    private GrossProfitDTO grossProfitOld;
    private GrossProfitDTO grossProfitNew;
    private Instant auditDate = Instant.now();
}
