package com.vmo.vmoproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BrandGrossProfitAuditLogDTO {
    private String event;
    private String brandId;
    private GrossProfitDTO grossProfitOld;
    private GrossProfitDTO grossProfitNew;
    private Instant auditDate = Instant.now();
}
