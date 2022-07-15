package com.vmo.vmoproject.service;

import com.vmo.vmoproject.constant.TypeOfEvent;
import com.vmo.vmoproject.dto.*;
import com.vmo.vmoproject.entities.*;
import com.vmo.vmoproject.exception.NotFoundException;
import com.vmo.vmoproject.mapper.BrandGrossProfitAuditLogMapper;
import com.vmo.vmoproject.repository.BrandGrossProfitAuditLogRepository;
import com.vmo.vmoproject.service.impl.BrandGrossProfitAuditLogService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BrandGrossProfitAuditLogServiceTest {
    @Mock
    BrandGrossProfitAuditLogRepository auditLogRepository;
    @Mock
    BrandGrossProfitAuditLogMapper auditLogMapper;
    MockMvc mockMvc;
    @Spy
    @InjectMocks
    BrandGrossProfitAuditLogService auditLogService;
    String email = "vmo.holding1@ascendcorp.com";
    SegmentDTO segmentDTO = new SegmentDTO("Name", 20.0);
    Segment segment = new Segment("Name", 20.0);
    CompanyDTO companyDTO = new CompanyDTO("VMO", "Ha Noi", "Ton That Thuyet", "IDMC 18"
            , "My Dinh 2", "1800");
    Company company = new Company("VMO", "Ha Noi", "Ton That Thuyet", "IDMC 18"
            , "My Dinh 2", "1800");

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(auditLogService).build();
    }

    @Test
    public void testGetAuditLog_success() {
        //Khoi Tao Data
        BrandGrossProfitAuditLogDTO auditLogDTO = buildAuditLogDTO();
        List<BrandGrossProfitAuditLogDTO> auditLogDTOList = new ArrayList<>();
        auditLogDTOList.add(auditLogDTO);
        BrandGrossProfitAuditLog auditLog = buildAuditLog();
        List<BrandGrossProfitAuditLog> auditLogList = new ArrayList<>();
        auditLogList.add(auditLog);
        //Mock du lieu
        when(auditLogMapper.toDTOList(any())).thenReturn(auditLogDTOList);
        when(auditLogRepository.findBrandGrossProfitAuditLogsByBrandId(anyString())).thenReturn(auditLogList);
        List<BrandGrossProfitAuditLogDTO> result = auditLogService.findById("1234567");
        Assert.assertEquals(result.size(), auditLogList.size());
    }

    @Test(expected = NotFoundException.class)
    public void testGetBGP_throwException() {
        when(auditLogRepository.findBrandGrossProfitAuditLogsByBrandId(anyString())).thenReturn(null);
        auditLogService.findById("1234567");
    }

    private BrandGrossProfitDTO builBrandGrossProfitDTO() {
        GrossProfitDTO grossProfitDTO = new GrossProfitDTO(20.0, ZonedDateTime.of(2020, 6, 13, 0, 0, 0
                , 0, ZoneId.of("Asia/Ho_Chi_Minh")), ZonedDateTime.of(2022, 6, 13, 0, 0, 0
                , 0, ZoneId.of("Asia/Ho_Chi_Minh")), List.of(segmentDTO));
        return new BrandGrossProfitDTO("62cbcae6e2deb10a6cdf6d67", "1234567", "BIDV", "ThangHv"
                , List.of(email), List.of(email), grossProfitDTO, "1234567891011", companyDTO, true, Instant.now(), null);
    }

    private BrandGrossProfit builBrandGrossProfit() {
        GrossProfit grossProfit = new GrossProfit(20.0, Date.from(Instant.now()), Date.from(Instant.now()), List.of(segment));
        return new BrandGrossProfit("62cbcae6e2deb10a6cdf6d67", "1234567", "BIDV", "ThangHv"
                , List.of(email), List.of(email), grossProfit, "1234567891011", company, true, Instant.now(), null);
    }

    private BrandGrossProfitAuditLog buildAuditLog() {
        BrandGrossProfitAuditLog auditLog = new BrandGrossProfitAuditLog();
        BrandGrossProfit brandGrossProfit = builBrandGrossProfit();
        auditLog.setEvent(TypeOfEvent.CREATE);
        auditLog.setBrandId(brandGrossProfit.getBrandId());
        auditLog.setGrossProfitNew(brandGrossProfit.getGrossProfit());
        return auditLog;
    }

    private BrandGrossProfitAuditLogDTO buildAuditLogDTO() {
        BrandGrossProfitAuditLogDTO auditLogDTO = new BrandGrossProfitAuditLogDTO();
        BrandGrossProfitDTO brandGrossProfitDTO = builBrandGrossProfitDTO();
        auditLogDTO.setEvent(TypeOfEvent.CREATE);
        auditLogDTO.setBrandId(brandGrossProfitDTO.getBrand_id());
        auditLogDTO.setGrossProfitNew(brandGrossProfitDTO.getGrossProfit());
        return auditLogDTO;
    }
}
