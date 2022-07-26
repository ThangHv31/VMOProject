package com.vmo.vmoproject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.vmo.vmoproject.constant.TypeOfEvent;
import com.vmo.vmoproject.dto.*;
import com.vmo.vmoproject.service.impl.BrandGrossProfitAuditLogService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)

public class BrandGrossProfitAuditLogControllerTest {
    @Mock
    BrandGrossProfitAuditLogService auditLogService;
    MockMvc mockMvc;
    ObjectMapper mapper = new ObjectMapper();
    @InjectMocks
    BrandGrossProfitAuditLogController auditLogController;
    String email = "vmo.holding1@ascendcorp.com";
    SegmentDTO segmentDTO = new SegmentDTO("Name", 20.0);
    CompanyDTO companyDTO = new CompanyDTO("VMO", "Ha Noi", "Ton That Thuyet", "IDMC 18"
            , "My DInh 2", "1800");

    @Before
    public void setUp() {
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        this.mockMvc = MockMvcBuilders.standaloneSetup(auditLogController).build();
    }
    @Test
    public void testGetBGP_thenSuccess() throws Exception{
        List<BrandGrossProfitAuditLogDTO> auditLogDTOList = buildListAuditLogDTO();
        when(auditLogService.findBrandGrossProfitAuditLogByBrandId(anyString())).thenReturn(auditLogDTOList);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/brands/1234567/gross-profit/audit-log")
                        .contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(auditLogDTOList)))
                .andExpect(status().isOk()).andReturn();
    }
    private BrandGrossProfitDTO buildBrandGrossProfitDTO(){
        GrossProfitDTO grossProfitDTO = new GrossProfitDTO(20.0, ZonedDateTime.of(2020, 6, 13, 0, 0, 0
                , 0, ZoneId.of("Asia/Ho_Chi_Minh")), ZonedDateTime.of(2022, 6, 13, 0, 0, 0
                , 0, ZoneId.of("Asia/Ho_Chi_Minh")), List.of(segmentDTO));
        return new BrandGrossProfitDTO("62cbcae6e2deb10a6cdf6d67", "1234567", "BIDV", "ThangHv"
                , List.of(email), List.of(email), grossProfitDTO, "1234567891011", companyDTO, true, Instant.now(), null);
    }
    private List<BrandGrossProfitAuditLogDTO> buildListAuditLogDTO(){
        BrandGrossProfitAuditLogDTO auditLogDTO = new BrandGrossProfitAuditLogDTO();
        BrandGrossProfitDTO brandGrossProfitDTO = buildBrandGrossProfitDTO();
        auditLogDTO.setEvent(TypeOfEvent.CREATE);
        auditLogDTO.setBrandId(brandGrossProfitDTO.getBrand_id());
        auditLogDTO.setGrossProfitNew(brandGrossProfitDTO.getGrossProfit());
        return new ArrayList<>(List.of(auditLogDTO));
    }
}
