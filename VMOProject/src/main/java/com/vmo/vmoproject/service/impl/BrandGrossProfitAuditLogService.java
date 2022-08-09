package com.vmo.vmoproject.service.impl;

import com.vmo.vmoproject.constant.TypeOfError;
import com.vmo.vmoproject.dto.BrandGrossProfitAuditLogDTO;
import com.vmo.vmoproject.exception.NotFoundException;
import com.vmo.vmoproject.mapper.BrandGrossProfitAuditLogMapper;
import com.vmo.vmoproject.repository.BrandGrossProfitAuditLogRepository;
import com.vmo.vmoproject.response.AuditLogPagingResponse;
import com.vmo.vmoproject.service.IBrandGrossProfitAuditLogService;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandGrossProfitAuditLogService implements IBrandGrossProfitAuditLogService {
    private final BrandGrossProfitAuditLogRepository auditLogRepository;
    private final BrandGrossProfitAuditLogMapper auditLogMapper;

    public BrandGrossProfitAuditLogService(BrandGrossProfitAuditLogRepository auditLogRepository, BrandGrossProfitAuditLogMapper auditLogMapper) {
        this.auditLogRepository = auditLogRepository;
        this.auditLogMapper = auditLogMapper;
    }

    /**
     * Find Brand Gross Profit Audit Logs by brand_Id
     * Author: Hoàng Văn Thắng
     */
    @Override
    public List<BrandGrossProfitAuditLogDTO> findBrandGrossProfitAuditLogByBrandId(String brandId) {
        if (auditLogRepository.findBrandGrossProfitAuditLogsByBrandId(brandId).size() == 0) {
            throw new NotFoundException(TypeOfError.BRAND_ID_NOT_FOUND);
        }
        return auditLogMapper.toDTOList(auditLogRepository.findBrandGrossProfitAuditLogsByBrandId(brandId));
    }

    @Override
    public AuditLogPagingResponse findAuditLogsByBrandIdPaging(String brandId, int page, int limit) {
        if (auditLogRepository.findBrandGrossProfitAuditLogsByBrandId(brandId).size() == 0) {
            throw new NotFoundException(TypeOfError.BRAND_ID_NOT_FOUND);
        }
        AuditLogPagingResponse response = new AuditLogPagingResponse();
        List<BrandGrossProfitAuditLogDTO> auditLogDTOList = findBrandGrossProfitAuditLogByBrandId(brandId);
        response.setPage(page);
        response.setTotalPage((int) Math.ceil((double) auditLogDTOList.size() / limit));
        response.setAuditLogDTOList(auditLogMapper.toDTOList(auditLogRepository
                .findBrandGrossProfitAuditLogsByBrandId(brandId, PageRequest.of(page - 1, limit))));
        return response;
    }


}
