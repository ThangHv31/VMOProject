package com.vmo.vmoproject.service;

import com.vmo.vmoproject.dto.BrandGrossProfitAuditLogDTO;

import java.util.List;

public interface IBrandGrossProfitAuditLogService{
    List<BrandGrossProfitAuditLogDTO> findBrandGrossProfitAuditLogByBrandId(String id);

}
