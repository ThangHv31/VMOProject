package com.vmo.vmoproject.service;

import com.vmo.vmoproject.dto.BrandGrossProfitAuditLogDTO;
import com.vmo.vmoproject.response.AuditLogPagingResponse;

import java.util.List;

public interface IBrandGrossProfitAuditLogService {
    List<BrandGrossProfitAuditLogDTO> findBrandGrossProfitAuditLogByBrandId(String id);

    AuditLogPagingResponse findAuditLogsByBrandIdPaging(String brandId, int page, int limit);
}
