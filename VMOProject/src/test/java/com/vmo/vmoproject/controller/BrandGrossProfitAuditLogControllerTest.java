package com.vmo.vmoproject.controller;

import com.vmo.vmoproject.constant.TypeOfEvent;
import com.vmo.vmoproject.dto.*;
import com.vmo.vmoproject.entities.Company;
import com.vmo.vmoproject.entities.Segment;
import com.vmo.vmoproject.service.impl.BrandGrossProfitAuditLogService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)

public class BrandGrossProfitAuditLogControllerTest {
    @Mock
    BrandGrossProfitAuditLogService auditLogService;
    MockMvc mockMvc;
    @InjectMocks
    BrandGrossProfitAuditLogController auditLogController;
    String email = "vmo.holding1@ascendcorp.com";
    SegmentDTO segmentDTO = new SegmentDTO("Name", 20.0);
    Segment segment = new Segment("Name", 20.0);
    CompanyDTO companyDTO = new CompanyDTO("VMO", "Ha Noi", "Ton That Thuyet", "IDMC 18"
            , "My DInh 2", "1800");
    Company company = new Company("VMO", "Ha Noi", "Ton That Thuyet", "IDMC 18"
            , "My DInh 2", "1800");

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(auditLogController).build();
    }
    @Test
    public void testGetBGP_success(){
        BrandGrossProfitAuditLogDTO auditLogDTO = buildAuditLogDTO();
        List<BrandGrossProfitAuditLogDTO> auditLogDTOList = new ArrayList<>();
        auditLogDTOList.add(auditLogDTO);
        when(auditLogService.findById(anyString())).thenReturn(auditLogDTOList);
        ResponseEntity<List<BrandGrossProfitAuditLogDTO>> response = auditLogController.getAuditLog("1234567");
        Assert.assertEquals(auditLogDTOList.size(), 1);
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
    }
    private BrandGrossProfitDTO builBrandGrossProfitDTO(){
        GrossProfitDTO grossProfitDTO = new GrossProfitDTO(20.0, ZonedDateTime.of(2020, 6, 13, 0, 0, 0
                , 0, ZoneId.of("Asia/Ho_Chi_Minh")), ZonedDateTime.of(2022, 6, 13, 0, 0, 0
                , 0, ZoneId.of("Asia/Ho_Chi_Minh")), Arrays.asList(segmentDTO));
        BrandGrossProfitDTO brandGrossProfitDTO = new BrandGrossProfitDTO("62cbcae6e2deb10a6cdf6d67", "1234567", "BIDV", "ThangHv"
                , Arrays.asList(email), Arrays.asList(email), grossProfitDTO, "1234567891011", companyDTO, true, Instant.now(), null);
        return brandGrossProfitDTO;
    }
    private BrandGrossProfitAuditLogDTO buildAuditLogDTO(){
        BrandGrossProfitAuditLogDTO auditLogDTO = new BrandGrossProfitAuditLogDTO();
        BrandGrossProfitDTO brandGrossProfitDTO = builBrandGrossProfitDTO();
        auditLogDTO.setEvent(TypeOfEvent.CREATE);
        auditLogDTO.setBrandId(brandGrossProfitDTO.getBrand_id());
        auditLogDTO.setGrossProfitNew(brandGrossProfitDTO.getGrossProfit());
        return auditLogDTO;
    }
}
