package com.vmo.vmoproject.controller;

import com.vmo.vmoproject.dto.BrandGrossProfitDTO;
import com.vmo.vmoproject.dto.CompanyDTO;
import com.vmo.vmoproject.dto.GrossProfitDTO;
import com.vmo.vmoproject.dto.SegmentDTO;
import com.vmo.vmoproject.service.impl.BrandGrossProfitService;
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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BrandGrossProfitControllerTest {
    @Mock
    BrandGrossProfitService brandGrossProfitService;
    MockMvc mockMvc;
    @InjectMocks
    BrandGrossProfitController brandGrossProfitController;
    String email = "vmo.holding1@ascendcorp.com";
    SegmentDTO segmentDTO = new SegmentDTO("Name", 20.0);
    CompanyDTO companyDTO = new CompanyDTO("VMO", "Ha Noi", "Ton That Thuyet", "IDMC 18"
            , "My DInh 2", "1800");

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(brandGrossProfitController).build();
    }
    @Test
    public void testGetBGP_success(){
        BrandGrossProfitDTO brandGrossProfitDTO = builBrandGrossProfitDTO();
        when(brandGrossProfitService.findById(anyString())).thenReturn(brandGrossProfitDTO);
        ResponseEntity<BrandGrossProfitDTO> response = brandGrossProfitController.getBrandGrossProfit("1234567");
        Assert.assertEquals(brandGrossProfitDTO, response.getBody());
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
    }
    @Test
    public void testCreateBGP_success(){
        BrandGrossProfitDTO brandGrossProfitDTO = builBrandGrossProfitDTO();
        when(brandGrossProfitService.create(anyString(), any(BrandGrossProfitDTO.class))).thenReturn(brandGrossProfitDTO);
        ResponseEntity<BrandGrossProfitDTO> response = brandGrossProfitController.createBrandGrossProfit("1234567", brandGrossProfitDTO);
        Assert.assertEquals(brandGrossProfitDTO, response.getBody());
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
    }
    @Test
    public void testUpdateBGP_success() {
        BrandGrossProfitDTO dto = builBrandGrossProfitDTO();
        when(brandGrossProfitService.update(anyString(), any(BrandGrossProfitDTO.class))).thenReturn(dto);
        ResponseEntity<BrandGrossProfitDTO> response = brandGrossProfitController.updateBrandGrossProfit("1000000", dto);
        Assert.assertEquals(dto, response.getBody());
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
    }
    private BrandGrossProfitDTO builBrandGrossProfitDTO(){
        GrossProfitDTO grossProfitDTO = new GrossProfitDTO(20.0, ZonedDateTime.of(2020, 6, 13, 0, 0, 0
                , 0, ZoneId.of("Asia/Ho_Chi_Minh")), ZonedDateTime.of(2022, 6, 13, 0, 0, 0
                , 0, ZoneId.of("Asia/Ho_Chi_Minh")), List.of(segmentDTO));
        return new BrandGrossProfitDTO("62cbcae6e2deb10a6cdf6d67", "1234567", "BIDV", "ThangHv"
                , List.of(email), List.of(email), grossProfitDTO, "1234567891011", companyDTO, true, Instant.now(), null);
    }
}
