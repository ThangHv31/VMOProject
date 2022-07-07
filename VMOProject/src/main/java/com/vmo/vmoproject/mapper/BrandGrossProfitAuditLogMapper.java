package com.vmo.vmoproject.mapper;

import com.vmo.vmoproject.dto.BrandGrossProfitAuditLogDTO;
import com.vmo.vmoproject.entities.BrandGrossProfitAuditLog;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BrandGrossProfitAuditLogMapper extends EntityMapper<BrandGrossProfitAuditLogDTO, BrandGrossProfitAuditLog> {
}
