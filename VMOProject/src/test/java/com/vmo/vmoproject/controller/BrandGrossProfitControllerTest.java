package com.vmo.vmoproject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.vmo.vmoproject.dto.BrandGrossProfitDTO;
import com.vmo.vmoproject.dto.CompanyDTO;
import com.vmo.vmoproject.dto.GrossProfitDTO;
import com.vmo.vmoproject.dto.SegmentDTO;
import com.vmo.vmoproject.service.impl.BrandGrossProfitService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.Silent.class)
@SpringBootTest
public class BrandGrossProfitControllerTest {
    @Mock
    BrandGrossProfitService brandGrossProfitService;
    MockMvc mockMvc;
    ObjectMapper mapper = new ObjectMapper();
    @InjectMocks
    BrandGrossProfitController brandGrossProfitController;
    String email = "vmo.holding1@ascendcorp.com";
    SegmentDTO segmentDTO = new SegmentDTO("Name", 20.0);
    CompanyDTO companyDTO = new CompanyDTO("VMO", "Ha Noi", "Ton That Thuyet", "IDMC 18"
            , "My DInh 2", "18000");

    @Before
    public void setUp() {
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        this.mockMvc = MockMvcBuilders.standaloneSetup(brandGrossProfitController).build();
    }
    @Test
    public void testGetBGP_thenSuccess() throws Exception{
        BrandGrossProfitDTO brandGrossProfitDTO = buildBrandGrossProfitDTO();
        when(brandGrossProfitService.findBrandGrossProfitByBrandId(anyString())).thenReturn(brandGrossProfitDTO);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/brands/1234567/gross-profit")
                        .contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(brandGrossProfitDTO)))
                .andExpect(status().isOk()).andReturn();
    }
    @Test
    public void testCreateBGP_thenSuccess() throws Exception{
        BrandGrossProfitDTO brandGrossProfitDTO = buildBrandGrossProfitDTO();
        when(brandGrossProfitService.createBrandGrossProfit(anyString(), any(BrandGrossProfitDTO.class))).thenReturn(brandGrossProfitDTO);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/brands/1234567/gross-profit")
                        .contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(brandGrossProfitDTO)))
                .andExpect(status().isOk()).andReturn();
    }
    @Test
    public void testUpdateBGP_thenSuccess() throws Exception{
        BrandGrossProfitDTO brandGrossProfitDTO = buildBrandGrossProfitDTO();
        when(brandGrossProfitService.updateBrandGrossProfit(anyString(), any(BrandGrossProfitDTO.class))).thenReturn(brandGrossProfitDTO);
        mockMvc.perform(MockMvcRequestBuilders.put("/api/brands/1234567/gross-profit")
                        .contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(brandGrossProfitDTO)))
                .andExpect(status().isOk()).andReturn();
    }
    @Test
    public void testFindBGPByPayeeName_thenSuccess() throws Exception{
        BrandGrossProfitDTO brandGrossProfitDTO = buildBrandGrossProfitDTO();
        when(brandGrossProfitService.findBrandGrossProfitsByPayeeName(anyString())).thenReturn(List.of(brandGrossProfitDTO));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/brands/gross-profit?payeeName=thangHv")
                        .contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(brandGrossProfitDTO)))
                .andExpect(status().isOk()).andReturn();
    }
    private BrandGrossProfitDTO buildBrandGrossProfitDTO(){
        GrossProfitDTO grossProfitDTO = new GrossProfitDTO(20.0, ZonedDateTime.of(2020, 6, 13, 0, 0, 0
                , 0, ZoneId.of("Asia/Ho_Chi_Minh")), ZonedDateTime.of(2022, 6, 13, 0, 0, 0
                , 0, ZoneId.of("Asia/Ho_Chi_Minh")), List.of(segmentDTO));
        return new BrandGrossProfitDTO("62cache6e2deb10a6cdf6d67", "1234567", "BIDV", "ThangHv"
                , List.of(email), List.of(email), grossProfitDTO, "1234567891011", companyDTO, true, Instant.now(), Instant.now());
    }
}
