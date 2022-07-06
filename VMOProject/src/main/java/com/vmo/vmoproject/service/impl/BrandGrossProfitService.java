package com.vmo.vmoproject.service.impl;

import com.vmo.vmoproject.constant.TypeOfError;
import com.vmo.vmoproject.constant.TypeOfEvent;
import com.vmo.vmoproject.dto.BrandGrossProfitDTO;
import com.vmo.vmoproject.entities.BrandGrossProfit;
import com.vmo.vmoproject.entities.BrandGrossProfitAuditLog;
import com.vmo.vmoproject.exception.BadRequestException;
import com.vmo.vmoproject.mapper.BrandGrossProfitMapper;
import com.vmo.vmoproject.mapper.BrandGrossProfitMapperImpl;
import com.vmo.vmoproject.repository.BrandGrossProfitAuditLogRepository;
import com.vmo.vmoproject.repository.BrandGrossProfitRepository;
import com.vmo.vmoproject.service.IBrandGrossProfitService;
import com.vmo.vmoproject.validation.ValidateBrandGrossProfit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class BrandGrossProfitService implements IBrandGrossProfitService {
    @Autowired
    private BrandGrossProfitRepository brandGrossProfitRepository;
    @Autowired
    private BrandGrossProfitAuditLogRepository auditLogRepository;
    private final BrandGrossProfitMapper brandGrossProfitMapper = new BrandGrossProfitMapperImpl();

    @Override
    public BrandGrossProfitDTO create(String id, BrandGrossProfitDTO brandGrossProfitDTO) {
        ValidateBrandGrossProfit.validateBrandGrossProfit(brandGrossProfitDTO);
        if (brandIdNotExist(id)) {
            throw new BadRequestException(Arrays.asList(TypeOfError.GROSS_PROFIT_FOR_BRAND_ID_EXIST));
        }
        brandGrossProfitRepository.save(brandGrossProfitMapper.toEntity(brandGrossProfitDTO));
        BrandGrossProfit brandGrossProfit = brandGrossProfitRepository.findBrandGrossProfitByBrandId(id);
        //Khi them moi 1 Brand GrossProfit -> 1 ban Audit Log duoc tao
        BrandGrossProfitAuditLog auditLog = new BrandGrossProfitAuditLog();
        //Gan gia tri cho BGPAuditLog
        auditLog.setEvent(TypeOfEvent.CREATE);
        auditLog.setBrandId(id);
        auditLog.setGrossProfitNew(brandGrossProfit.getGrossProfit());
        auditLogRepository.save(auditLog);
        brandGrossProfitDTO.setId(brandGrossProfit.getId());        //gan lai id cho Dto
        return brandGrossProfitDTO;
    }

    @Override
    public BrandGrossProfitDTO findById(String id) {
        return brandGrossProfitMapper.toDTO(brandGrossProfitRepository.findBrandGrossProfitByBrandId(id));
    }

    @Override
    public BrandGrossProfitDTO update(String id, BrandGrossProfitDTO dto) {
        BrandGrossProfit brandGrossProfit = brandGrossProfitRepository.findBrandGrossProfitByBrandId(id);
        BrandGrossProfitAuditLog auditLog = new BrandGrossProfitAuditLog();
        if (brandGrossProfit == null) {
            throw new BadRequestException(Arrays.asList(TypeOfError.BRANDID_NOT_FOUND));
        } else {
            auditLog.setGrossProfitOld(brandGrossProfit.getGrossProfit());//set grossprofitOld cho auditlog
            BrandGrossProfit newBrandGrossProfit = brandGrossProfitMapper.toEntity(dto);
            newBrandGrossProfit.setId(brandGrossProfit.getId());
            brandGrossProfitRepository.save(newBrandGrossProfit);
            auditLog.setEvent(TypeOfEvent.UPDATE);
            auditLog.setBrandId(id);
            auditLog.setGrossProfitNew(newBrandGrossProfit.getGrossProfit());//set grossprofit New cho audit log
            auditLogRepository.save(auditLog);
            dto.setId(brandGrossProfit.getId());
        }
        return dto;
    }

    private boolean brandIdNotExist(String brandId) {
        if (brandGrossProfitRepository.findBrandGrossProfitByBrandId(brandId) != null) {
            return true;
        }
        return false;
    }
}
