package com.vmo.vmoproject.service.impl;

import com.vmo.vmoproject.constant.TypeOfError;
import com.vmo.vmoproject.constant.TypeOfEvent;
import com.vmo.vmoproject.dto.BrandGrossProfitDTO;
import com.vmo.vmoproject.entities.BrandGrossProfit;
import com.vmo.vmoproject.entities.BrandGrossProfitAuditLog;
import com.vmo.vmoproject.exception.BadRequestException;
import com.vmo.vmoproject.exception.Errors;
import com.vmo.vmoproject.mapper.BrandGrossProfitMapper;
import com.vmo.vmoproject.mapper.BrandGrossProfitMapperImpl;
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
    private final BrandGrossProfitMapper brandGrossProfitMapper = new BrandGrossProfitMapperImpl();
    @Value("${bankCode}")
    List<String> bankCodeList;
    @Override
    public BrandGrossProfitDTO create(String id, BrandGrossProfitDTO brandGrossProfitDTO) {
        validateBrandGrossProfit(brandGrossProfitDTO);
        if (brandIdNotExist(id)) {
            throw new BadRequestException(Arrays.asList(TypeOfError.GROSS_PROFIT_FOR_BRAND_ID_EXIST));
        }
        brandGrossProfitDTO.setCreatedDate(Instant.now());
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
        validateBrandGrossProfit(dto);
        BrandGrossProfit brandGrossProfit = brandGrossProfitRepository.findBrandGrossProfitByBrandId(id);
        BrandGrossProfitAuditLog auditLog = new BrandGrossProfitAuditLog();
        if (brandGrossProfit == null) {
            throw new BadRequestException(Arrays.asList(TypeOfError.BRANDID_NOT_FOUND));
        } else {
            auditLog.setGrossProfitOld(brandGrossProfit.getGrossProfit());//set grossprofitOld cho auditlog
            dto.setUpdatedDate(Instant.now());
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
    private boolean validateBrandGrossProfit(BrandGrossProfitDTO dto) {
        List<Errors> errorsList = new ArrayList<>();
        if (validateListEmails(dto.getSettlementReportEmails()) == false) {
            errorsList.add(TypeOfError.SETTLEMENT_REPORT_EMAIL_INCORRECT_EMAIL_FORMAT);
        }
        if (validateListEmails(dto.getDailyReportEmails()) == false) {
            errorsList.add(TypeOfError.DAILY_REPORT_EMAIL_INCORRECT_EMAIL_FORMAT);
        }
        if (checkBankCode(dto.getBankCode()) == false) {
            errorsList.add(TypeOfError.BANKCODE_INVALID);
        }
        if (checkExpiredDate(dto.getGrossProfit().getEffectiveDate(), dto.getGrossProfit().getExpiredDate()) == false) {
            errorsList.add(TypeOfError.EFFECTIVEDATE_MUST_BE_BEFORE_EXPIREDDATE);
        }
        if (ckeckSegment(dto) == false) {
            errorsList.add(TypeOfError.SUM_OF_VALUE_IN_SECTIONS_IS_NOT_EQUAL_TO_PERCENT);
            throw new BadRequestException(errorsList);
        }
        return true;
    }
    private boolean checkBankCode(String bankCode) {
        for(int i = 0; i<bankCodeList.size();i++){
            if(! bankCodeList.get(i).equals(bankCode.trim())){
                return false;
            }
        }
        return true;
    }
    private boolean brandIdNotExist(String brandId) {
        if (brandGrossProfitRepository.findBrandGrossProfitByBrandId(brandId) != null) {
            return true;
        }
        return false;
    }
}
