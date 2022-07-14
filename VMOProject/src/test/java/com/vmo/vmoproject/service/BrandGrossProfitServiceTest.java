package com.vmo.vmoproject.service;

import com.vmo.vmoproject.constant.TypeOfEvent;
import com.vmo.vmoproject.dto.BrandGrossProfitDTO;
import com.vmo.vmoproject.dto.CompanyDTO;
import com.vmo.vmoproject.dto.GrossProfitDTO;
import com.vmo.vmoproject.dto.SegmentDTO;
import com.vmo.vmoproject.entities.*;
import com.vmo.vmoproject.exception.BadRequestException;
import com.vmo.vmoproject.exception.NotFoundException;
import com.vmo.vmoproject.mapper.BrandGrossProfitMapper;
import com.vmo.vmoproject.repository.BrandGrossProfitAuditLogRepository;
import com.vmo.vmoproject.repository.BrandGrossProfitRepository;
import com.vmo.vmoproject.service.impl.BrandGrossProfitService;
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
import java.util.Arrays;
import java.util.Date;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BrandGrossProfitServiceTest {
    @Mock
    BrandGrossProfitRepository brandGrossProfitRepository;
    @Mock
    BrandGrossProfitAuditLogRepository auditLogRepository;
    @Mock
    BrandGrossProfitMapper brandGrossProfitMapper;
    MockMvc mockMvc;
    @Spy
    @InjectMocks
    BrandGrossProfitService brandGrossProfitService;
    String email = "vmo.holding1@ascendcorp.com";
    SegmentDTO segmentDTO = new SegmentDTO("Name", 20.0);
    Segment segment = new Segment("Name", 20.0);
    CompanyDTO companyDTO = new CompanyDTO("VMO", "Ha Noi", "Ton That Thuyet", "IDMC 18"
            , "My DInh 2", "1800");
    Company company = new Company("VMO", "Ha Noi", "Ton That Thuyet", "IDMC 18"
            , "My DInh 2", "1800");

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(brandGrossProfitService).build();
    }

    @Test
    public void testCreate_success() {
        //Khoi Tao Data
        BrandGrossProfitDTO brandGrossProfitDTO = builBrandGrossProfitDTO();
        BrandGrossProfit brandGrossProfit = builBrandGrossProfit();
        BrandGrossProfitAuditLog auditLog = buildAuditLog();
        //Mock du lieu
        doReturn(false).when(brandGrossProfitService).isExistBrandGrossProfit(anyString());
        doReturn(true).when(brandGrossProfitService).validateBrandGrossProfit(any());
        when(brandGrossProfitMapper.toEntity(any(BrandGrossProfitDTO.class))).thenReturn(brandGrossProfit);
        when(brandGrossProfitRepository.save(any(BrandGrossProfit.class))).thenReturn(brandGrossProfit);
        when(brandGrossProfitRepository.findBrandGrossProfitByBrandId(anyString())).thenReturn(brandGrossProfit);
        when(auditLogRepository.save(any())).thenReturn(auditLog);
        BrandGrossProfitDTO result = brandGrossProfitService.create(brandGrossProfitDTO.getBrand_id(), brandGrossProfitDTO);
        //Kiem tra ket qua
        Assert.assertEquals(result.getBrand_id(), brandGrossProfit.getBrandId());
        Assert.assertEquals(brandGrossProfit.getTaxId(), result.getTaxId());
        Assert.assertEquals(auditLog.getEvent(), "CREATE");
    }

    @Test(expected = BadRequestException.class)
    public void testCreate_whenBrandIdIsExist_thenThrowBadRequest() {
        BrandGrossProfitDTO brandGrossProfitDTO = builBrandGrossProfitDTO();
        doReturn(true).when(brandGrossProfitService).isExistBrandGrossProfit(anyString());
        brandGrossProfitService.create(brandGrossProfitDTO.getBrand_id(), brandGrossProfitDTO);
    }

    @Test
    public void testUpdate_success() {
        //Khoi Tao Data
        BrandGrossProfitDTO brandGrossProfitDTO = builBrandGrossProfitDTO();
        BrandGrossProfit brandGrossProfit = builBrandGrossProfit();
        BrandGrossProfitAuditLog auditLog = buildAuditLog();
        auditLog.setEvent(TypeOfEvent.UPDATE);
        //Mock du lieu
        doReturn(true).when(brandGrossProfitService).isExistBrandGrossProfit(anyString());
        doReturn(true).when(brandGrossProfitService).validateBrandGrossProfit(any());
        when(brandGrossProfitMapper.toEntity(any(BrandGrossProfitDTO.class))).thenReturn(brandGrossProfit);
        when(brandGrossProfitRepository.save(any(BrandGrossProfit.class))).thenReturn(brandGrossProfit);
        when(brandGrossProfitRepository.findBrandGrossProfitByBrandId(anyString())).thenReturn(brandGrossProfit);
        when(auditLogRepository.save(any())).thenReturn(auditLog);
        BrandGrossProfitDTO result = brandGrossProfitService.update("1234567", brandGrossProfitDTO);
        //Kiem tra ket qua
        Assert.assertEquals(result.getBrand_id(), brandGrossProfit.getBrandId());
        Assert.assertEquals(brandGrossProfit.getId(), result.getId());
        Assert.assertEquals(auditLog.getEvent(), "UPDATE");
    }

    @Test(expected = NotFoundException.class)
    public void testUpdate_throwException() {
        BrandGrossProfitDTO brandGrossProfitDTO = builBrandGrossProfitDTO();
        doReturn(false).when(brandGrossProfitService).isExistBrandGrossProfit(anyString());
        brandGrossProfitService.update("1234567", brandGrossProfitDTO);
    }

    @Test
    public void testGetBGP_success() {
        //Khoi Tao Data
        BrandGrossProfitDTO brandGrossProfitDTO = builBrandGrossProfitDTO();
        BrandGrossProfit brandGrossProfit = builBrandGrossProfit();
        BrandGrossProfitAuditLog auditLog = buildAuditLog();
        //Mock du lieu
        doReturn(true).when(brandGrossProfitService).isExistBrandGrossProfit(anyString());
        when(brandGrossProfitMapper.toDTO(any(BrandGrossProfit.class))).thenReturn(brandGrossProfitDTO);
        when(brandGrossProfitRepository.findBrandGrossProfitByBrandId(anyString())).thenReturn(brandGrossProfit);
        BrandGrossProfitDTO result = brandGrossProfitService.findById("1234567");
        //kiem tra ket qua
        Assert.assertEquals(result.getId(), brandGrossProfit.getId());
    }

    @Test(expected = NotFoundException.class)
    public void testGetBGP_throwException() {
        doReturn(false).when(brandGrossProfitService).isExistBrandGrossProfit(anyString());
        brandGrossProfitService.findById("1234567");
    }

    @Test
    public void testValidate_successs() {
        BrandGrossProfitDTO brandGrossProfitDTO = builBrandGrossProfitDTO();
        doReturn(true).when(brandGrossProfitService).validateBankCode(any());
        brandGrossProfitService.validateBrandGrossProfit(brandGrossProfitDTO);
    }

    @Test(expected = BadRequestException.class)
    public void testValidate_throwExeption() {
        BrandGrossProfitDTO brandGrossProfitDTO = builBrandGrossProfitDTO();
        brandGrossProfitDTO.setBrand_id("111");
        brandGrossProfitDTO.setDailyReportEmails(Arrays.asList("mail"));
        brandGrossProfitDTO.setSettlementReportEmails(Arrays.asList("mail"));
        brandGrossProfitDTO.getGrossProfit().setExpiredDate(ZonedDateTime.of(2019, 6, 13, 0, 0, 0
                , 0, ZoneId.of("Asia/Ho_Chi_Minh")));
        brandGrossProfitDTO.getGrossProfit().setPercent(25.0);
        doReturn(false).when(brandGrossProfitService).validateBankCode(anyString());
        brandGrossProfitService.validateBrandGrossProfit(brandGrossProfitDTO);
    }

    private BrandGrossProfitDTO builBrandGrossProfitDTO() {
        GrossProfitDTO grossProfitDTO = new GrossProfitDTO(20.0, ZonedDateTime.of(2020, 6, 13, 0, 0, 0
                , 0, ZoneId.of("Asia/Ho_Chi_Minh")), ZonedDateTime.of(2022, 6, 13, 0, 0, 0
                , 0, ZoneId.of("Asia/Ho_Chi_Minh")), Arrays.asList(segmentDTO));
        BrandGrossProfitDTO brandGrossProfitDTO = new BrandGrossProfitDTO("62cbcae6e2deb10a6cdf6d67", "1234567", "BIDV", "ThangHv"
                , Arrays.asList(email), Arrays.asList(email), grossProfitDTO, "1234567891011", companyDTO, true, Instant.now(), null);
        return brandGrossProfitDTO;
    }

    private BrandGrossProfit builBrandGrossProfit() {
        GrossProfitDTO grossProfitDTO = new GrossProfitDTO(20.0, ZonedDateTime.of(2020, 6, 13, 0, 0, 0
                , 0, ZoneId.of("Asia/Ho_Chi_Minh")), ZonedDateTime.of(2022, 6, 13, 0, 0, 0
                , 0, ZoneId.of("Asia/Ho_Chi_Minh")), Arrays.asList(segmentDTO));
        GrossProfit grossProfit = new GrossProfit(20.0, Date.from(Instant.now()), Date.from(Instant.now()), Arrays.asList(segment));
        BrandGrossProfit brandGrossProfit = new BrandGrossProfit("62cbcae6e2deb10a6cdf6d67", "1234567", "BIDV", "ThangHv"
                , Arrays.asList(email), Arrays.asList(email), grossProfit, "1234567891011", company, true, Instant.now(), null);
        return brandGrossProfit;
    }

    private BrandGrossProfitAuditLog buildAuditLog() {
        BrandGrossProfitAuditLog auditLog = new BrandGrossProfitAuditLog();
        BrandGrossProfit brandGrossProfit = builBrandGrossProfit();
        auditLog.setEvent(TypeOfEvent.CREATE);
        auditLog.setBrandId(brandGrossProfit.getBrandId());
        auditLog.setGrossProfitNew(brandGrossProfit.getGrossProfit());
        return auditLog;
    }
}