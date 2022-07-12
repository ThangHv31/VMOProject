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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.vmo.vmoproject.validation.ValidateBrandGrossProfit.*;

@Service
@PropertySource("classpath:bankCode.properties")
public class BrandGrossProfitService implements IBrandGrossProfitService {
    @Autowired
    private BrandGrossProfitRepository brandGrossProfitRepository;
    @Autowired
    private BrandGrossProfitAuditLogRepository auditLogRepository;
    @Autowired
    private BrandGrossProfitMapper brandGrossProfitMapper;
    @Value("${bankCode}")
    List<String> bankCodeList;
    /**
     * Create Brand Gross Profit
     * Author: Hoàng Văn Thắng - 06/07/2022
     * Update: 07/07/2022 "validate mot so truong - them id cho DTO"
     *         11/07/2022 "them validate brand_id"
     */
    @Override
    public BrandGrossProfitDTO create(String id, BrandGrossProfitDTO brandGrossProfitDTO) {
        brandGrossProfitDTO.setBrand_id(id);
        validateBrandGrossProfit(brandGrossProfitDTO);
        if (brandGrossProfitRepository.findBrandGrossProfitByBrandId(id) != null) {
            throw new BadRequestException(Arrays.asList(TypeOfError.GROSS_PROFIT_FOR_BRAND_ID_EXIST));
        }
        brandGrossProfitDTO.setCreatedDate(Instant.now());
        brandGrossProfitRepository.save(brandGrossProfitMapper.toEntity(brandGrossProfitDTO));
        BrandGrossProfit brandGrossProfit = brandGrossProfitRepository.findBrandGrossProfitByBrandId(id);
        //Khi them moi 1 BrandGrossProfit -> 1 ban BrandGrossProfitAuditLog duoc tao voi Type = CREATE
        BrandGrossProfitAuditLog auditLog = new BrandGrossProfitAuditLog();
        auditLog.setEvent(TypeOfEvent.CREATE);                              //
        auditLog.setBrandId(id);                                            //  Gan gia tri cho BrandGrossProfitAuditLog
        auditLog.setGrossProfitNew(brandGrossProfit.getGrossProfit());      //
        auditLogRepository.save(auditLog);
        brandGrossProfitDTO.setId(brandGrossProfit.getId());                //gan lai id cho Dto
        return brandGrossProfitDTO;
    }
    /**
     * Find Brand Gross Profit by brand_Id
     * Author: Hoàng Văn Thắng
     */
    @Override
    public BrandGrossProfitDTO findById(String id) {
        if (brandGrossProfitRepository.findBrandGrossProfitByBrandId(id) == null) {
            throw new NotFoundException(TypeOfError.BRAND_ID_NOT_FOUND);
        }
        return brandGrossProfitMapper.toDTO(brandGrossProfitRepository.findBrandGrossProfitByBrandId(id));
    }
    /**
     * Update Brand Gross Profit
     * Author: Hoàng Văn Thắng
     */
    @Override
    public BrandGrossProfitDTO update(String id, BrandGrossProfitDTO brandGrossProfitDTO) {
        brandGrossProfitDTO.setBrand_id(id);
        brandGrossProfitDTO.setUpdatedDate(Instant.now());
        validateBrandGrossProfit(brandGrossProfitDTO);
        BrandGrossProfit brandGrossProfit = brandGrossProfitRepository.findBrandGrossProfitByBrandId(id);
        BrandGrossProfitAuditLog auditLog = new BrandGrossProfitAuditLog();
        if (brandGrossProfit == null) {                                                     //neu khong tim thay brandGrossProfit
            throw new BadRequestException(Arrays.asList(TypeOfError.BRAND_ID_NOT_FOUND));   // -> thong bao loi
        } else {
            auditLog.setGrossProfitOld(brandGrossProfit.getGrossProfit());                  //set grossprofitOld cho auditlog
            BrandGrossProfit newBrandGrossProfit = brandGrossProfitMapper.toEntity(brandGrossProfitDTO);
            newBrandGrossProfit.setId(brandGrossProfit.getId());
            brandGrossProfitRepository.save(newBrandGrossProfit);
            auditLog.setEvent(TypeOfEvent.UPDATE);
            auditLog.setBrandId(id);
            auditLog.setGrossProfitNew(newBrandGrossProfit.getGrossProfit());               //set grossprofit New cho audit log
            auditLogRepository.save(auditLog);
            brandGrossProfitDTO.setId(brandGrossProfit.getId());
        }
        return brandGrossProfitDTO;
    }
    /**
     * Validate Brand Gross Profit
     * Author: Hoàng Văn Thắng
     */
    public boolean validateBrandGrossProfit(BrandGrossProfitDTO dto) {
        List<Errors> errorsList = new ArrayList<>();
        if (validateBrandId(dto.getBrand_id()) == false){
            errorsList.add(TypeOfError.BRAND_ID_INVALID);
        }
        if (validateListEmails(dto.getSettlementReportEmails()) == false) {
            errorsList.add(TypeOfError.SETTLEMENT_REPORT_EMAIL_INCORRECT_EMAIL_FORMAT);
        }
        if (validateListEmails(dto.getDailyReportEmails()) == false) {
            errorsList.add(TypeOfError.DAILY_REPORT_EMAIL_INCORRECT_EMAIL_FORMAT);
        }
        if (validateBankCode(dto.getBankCode()) == false) {
            errorsList.add(TypeOfError.BANK_CODE_INVALID);
        }
        if (validateExpiredDate(dto.getGrossProfit().getEffectiveDate(), dto.getGrossProfit().getExpiredDate()) == false) {
            errorsList.add(TypeOfError.EFFECTIVE_DATE_MUST_BE_BEFORE_EXPIRED_DATE);
        }
        if (validateSegment(dto) == false) {
            errorsList.add(TypeOfError.SUM_OF_VALUE_IN_SECTIONS_IS_NOT_EQUAL_TO_PERCENT);
        }
        if (errorsList.size()>0){
            throw new BadRequestException(errorsList);
        }
        return true;
    }

    private boolean validateBankCode(String bankCode) {
        for (int i = 0; i < bankCodeList.size(); i++) {
            if (bankCodeList.get(i).equals(bankCode.trim())) {
                return true;
            }
        }
        return false;
    }
}
