package com.vmo.vmoproject.service.impl;

import com.vmo.vmoproject.constant.TypeOfError;
import com.vmo.vmoproject.constant.TypeOfEvent;
import com.vmo.vmoproject.dto.BrandGrossProfitDTO;
import com.vmo.vmoproject.entities.BrandGrossProfit;
import com.vmo.vmoproject.entities.BrandGrossProfitAuditLog;
import com.vmo.vmoproject.exception.BadRequestException;
import com.vmo.vmoproject.exception.Errors;
import com.vmo.vmoproject.exception.NotFoundException;
import com.vmo.vmoproject.mapper.BrandGrossProfitMapper;
import com.vmo.vmoproject.repository.BrandGrossProfitAuditLogRepository;
import com.vmo.vmoproject.repository.BrandGrossProfitRepository;
import com.vmo.vmoproject.service.IBrandGrossProfitService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static com.vmo.vmoproject.validation.ValidateBrandGrossProfit.*;

@Service
@PropertySource("classpath:bankCode.properties")
public class BrandGrossProfitService implements IBrandGrossProfitService {
    private final BrandGrossProfitRepository brandGrossProfitRepository;
    private final BrandGrossProfitAuditLogRepository auditLogRepository;
    private final BrandGrossProfitMapper brandGrossProfitMapper;
    @Value("${bankCode}")
    List<String> bankCodeList;

    public BrandGrossProfitService(BrandGrossProfitRepository brandGrossProfitRepository, BrandGrossProfitAuditLogRepository auditLogRepository, BrandGrossProfitMapper brandGrossProfitMapper) {
        this.brandGrossProfitRepository = brandGrossProfitRepository;
        this.auditLogRepository = auditLogRepository;
        this.brandGrossProfitMapper = brandGrossProfitMapper;
    }

    /**
     * Create Brand Gross Profit
     * Author: Hoàng Văn Thắng
     */
    @Override
    public BrandGrossProfitDTO createBrandGrossProfit(String brandId, BrandGrossProfitDTO brandGrossProfitDTO) {
        if (isExistBrandGrossProfit(brandId)) {
            throw new BadRequestException(List.of(TypeOfError.GROSS_PROFIT_FOR_BRAND_ID_EXIST));
        }
        brandGrossProfitDTO.setBrand_id(brandId);
        validateBrandGrossProfit(brandGrossProfitDTO);
        brandGrossProfitRepository.save(brandGrossProfitMapper.toEntity(brandGrossProfitDTO));
        BrandGrossProfit brandGrossProfit = brandGrossProfitRepository.findBrandGrossProfitByBrandId(brandId);
        BrandGrossProfitAuditLog auditLog = new BrandGrossProfitAuditLog();
        auditLog.setEvent(TypeOfEvent.CREATE);
        auditLog.setBrandId(brandId);
        auditLog.setGrossProfitNew(brandGrossProfit.getGrossProfit());
        auditLogRepository.save(auditLog);
        return brandGrossProfitMapper.toDTO(brandGrossProfit);
    }
    /**
     * Find Brand Gross Profit by brand_Id
     * Author: Hoàng Văn Thắng
     */
    @Override
    public BrandGrossProfitDTO findBrandGrossProfitByBrandId(String brandId) {
        if (!isExistBrandGrossProfit(brandId)) {
            throw new NotFoundException(TypeOfError.BRAND_ID_NOT_FOUND);
        }
        return brandGrossProfitMapper.toDTO(brandGrossProfitRepository.findBrandGrossProfitByBrandId(brandId));
    }
    /**
     * Update Brand Gross Profit
     * Author: Hoàng Văn Thắng
     */
    @Override
    public BrandGrossProfitDTO updateBrandGrossProfit(String brandId, BrandGrossProfitDTO brandGrossProfitDTO) {
        if (!isExistBrandGrossProfit(brandId)) {
            throw new NotFoundException(TypeOfError.BRAND_ID_NOT_FOUND);
        }
        brandGrossProfitDTO.setBrand_id(brandId);
        validateBrandGrossProfit(brandGrossProfitDTO);
        BrandGrossProfit brandGrossProfit = brandGrossProfitRepository.findBrandGrossProfitByBrandId(brandId);
        BrandGrossProfitAuditLog auditLog = new BrandGrossProfitAuditLog();
        auditLog.setGrossProfitOld(brandGrossProfit.getGrossProfit());
        BrandGrossProfit newBrandGrossProfit = brandGrossProfitMapper.toEntity(brandGrossProfitDTO);
        newBrandGrossProfit.setId(brandGrossProfit.getId());
        brandGrossProfitRepository.save(newBrandGrossProfit);
        auditLog.setEvent(TypeOfEvent.UPDATE);
        auditLog.setBrandId(brandId);
        auditLog.setGrossProfitNew(newBrandGrossProfit.getGrossProfit());
        auditLogRepository.save(auditLog);
        return brandGrossProfitMapper.toDTO(brandGrossProfitRepository.findBrandGrossProfitByBrandId(brandId));
    }
    /**
     * Validate Brand Gross Profit
     * Author: Hoàng Văn Thắng
     */
    public boolean validateBrandGrossProfit(BrandGrossProfitDTO dto) {
        List<Errors> errorsList = new ArrayList<>();
        if (!validateBrandId(dto.getBrand_id())){
            errorsList.add(TypeOfError.BRAND_ID_INVALID);
        }
        if (!validateListEmails(dto.getSettlementReportEmails())) {
            errorsList.add(TypeOfError.SETTLEMENT_REPORT_EMAIL_INCORRECT_EMAIL_FORMAT);
        }
        if (!validateListEmails(dto.getDailyReportEmails())) {
            errorsList.add(TypeOfError.DAILY_REPORT_EMAIL_INCORRECT_EMAIL_FORMAT);
        }
        if (!validateBankCode(dto.getBankCode())) {
            errorsList.add(TypeOfError.BANK_CODE_INVALID);
        }
        if (!validateExpiredDate(dto.getGrossProfit().getEffectiveDate(), dto.getGrossProfit().getExpiredDate())) {
            errorsList.add(TypeOfError.EFFECTIVE_DATE_MUST_BE_BEFORE_EXPIRED_DATE);
        }
        if (!validateSegment(dto)) {
            errorsList.add(TypeOfError.SUM_OF_VALUE_IN_SECTIONS_IS_NOT_EQUAL_TO_PERCENT);
        }
        if (errorsList.size()>0){
            throw new BadRequestException(errorsList);
        }
        return true;
    }
    public boolean isExistBrandGrossProfit(String brandId) {
        return brandGrossProfitRepository.findBrandGrossProfitByBrandId(brandId) != null;
    }
    public boolean validateBankCode(String bankCode) {
        for (String s : bankCodeList) {
            if (s.equals(bankCode.trim())) {
                return true;
            }
        }
        return false;
    }
}
