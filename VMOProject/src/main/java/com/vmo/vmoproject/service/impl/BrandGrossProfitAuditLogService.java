package com.vmo.vmoproject.service.impl;

import com.vmo.vmoproject.constant.TypeOfError;
import com.vmo.vmoproject.dto.BrandGrossProfitAuditLogDTO;
import com.vmo.vmoproject.exception.NotFoundException;
import com.vmo.vmoproject.mapper.BrandGrossProfitAuditLogMapper;
import com.vmo.vmoproject.repository.BrandGrossProfitAuditLogRepository;
import com.vmo.vmoproject.service.IBrandGrossProfitAuditLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class BrandGrossProfitAuditLogService implements IBrandGrossProfitAuditLogService {
    @Autowired
    private BrandGrossProfitAuditLogRepository auditLogRepository;
    @Autowired
    private BrandGrossProfitAuditLogMapper auditLogMapper;

    @Override
    public List<BrandGrossProfitAuditLogDTO> findById(String id) {
        if (auditLogRepository.findBrandGrossProfitAuditLogsByBrandId(id) == null) {
            throw new NotFoundException(TypeOfError.BRAND_ID_NOT_FOUND);
        }
        return auditLogMapper.toDTOList(auditLogRepository.findBrandGrossProfitAuditLogsByBrandId(id));
    }

}
