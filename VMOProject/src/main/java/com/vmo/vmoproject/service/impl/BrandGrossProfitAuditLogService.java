package com.vmo.vmoproject.service.impl;

import com.vmo.vmoproject.dto.BrandGrossProfitAuditLogDTO;
import com.vmo.vmoproject.mapper.BrandGrossProfitAuditLogMapper;
import com.vmo.vmoproject.mapper.BrandGrossProfitAuditLogMapperImpl;
import com.vmo.vmoproject.repository.BrandGrossProfitAuditLogRepository;
import com.vmo.vmoproject.service.IBrandGrossProfitAuditLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class BrandGrossProfitAuditLogService implements IBrandGrossProfitAuditLogService {
    @Autowired
    private BrandGrossProfitAuditLogRepository auditLogRepository;
    private final BrandGrossProfitAuditLogMapper auditLogMapper = new BrandGrossProfitAuditLogMapperImpl();

    @Override
    public BrandGrossProfitAuditLogDTO create(String id, BrandGrossProfitAuditLogDTO e) {
        return null;
    }

    @Override
    public BrandGrossProfitAuditLogDTO findById(String id) {
        return auditLogMapper.toDTO(auditLogRepository.findBrandGrossProfitAuditLogByBrandId(id));
    }

    @Override
    public BrandGrossProfitAuditLogDTO update(String id, BrandGrossProfitAuditLogDTO dto) {
        return null;
    }

}
