package com.vmo.vmoproject.controller;

import com.vmo.vmoproject.dto.BrandGrossProfitDTO;
import com.vmo.vmoproject.service.impl.BrandGrossProfitService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/api/brands")
public class BrandGrossProfitController {
    private final BrandGrossProfitService brandGrossProfitService;

    public BrandGrossProfitController(BrandGrossProfitService brandGrossProfitService) {
        this.brandGrossProfitService = brandGrossProfitService;
    }

    @GetMapping("/{brandId}/gross-profit")
    public ResponseEntity<BrandGrossProfitDTO> getBrandGrossProfit(@PathVariable("brandId") String brandId) {
        return ResponseEntity.ok().body(brandGrossProfitService.findBrandGrossProfitByBrandId(brandId));
    }

    @PostMapping("/{brandId}/gross-profit")
    public ResponseEntity<BrandGrossProfitDTO> createBrandGrossProfit(@PathVariable("brandId") String brandId, @Valid @RequestBody BrandGrossProfitDTO brandGrossProfitDTO) {
        return ResponseEntity.ok().body(brandGrossProfitService.createBrandGrossProfit(brandId, brandGrossProfitDTO));
    }

    @PutMapping("/{brandId}/gross-profit")
    public ResponseEntity<BrandGrossProfitDTO> updateBrandGrossProfit(@PathVariable("brandId") String brandId, @Valid @RequestBody BrandGrossProfitDTO brandGrossProfitDTO) {
        return ResponseEntity.ok().body(brandGrossProfitService.updateBrandGrossProfit(brandId, brandGrossProfitDTO));
    }
    @GetMapping("/gross-profit")
    public ResponseEntity<List<BrandGrossProfitDTO>> findBrandGrossProfitByPayeeName(@RequestParam(value = "payeeName") String payeeName) {
        return ResponseEntity.ok().body(brandGrossProfitService.findBrandGrossProfitsByPayeeName(payeeName));
    }
}
