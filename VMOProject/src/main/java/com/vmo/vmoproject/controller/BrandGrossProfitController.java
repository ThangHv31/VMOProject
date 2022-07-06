package com.vmo.vmoproject.controller;

import com.vmo.vmoproject.dto.BrandGrossProfitDTO;
import com.vmo.vmoproject.dto.GrossProfitDTO;
import com.vmo.vmoproject.service.impl.BrandGrossProfitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/api/brands")
public class BrandGrossProfitController {
    @Autowired
    private BrandGrossProfitService brandGrossProfitService;

    @GetMapping("/{brandId}/gross-profit")
    public ResponseEntity<BrandGrossProfitDTO> getBrandGrossProfit(@PathVariable("brandId") String brandId) {
        return ResponseEntity.ok().body(brandGrossProfitService.findById(brandId));
    }

    @PostMapping("/{brandId}/gross-profit")
    public ResponseEntity<BrandGrossProfitDTO> createBrandGrossProfit(@PathVariable("brandId") String brandId, @Valid @RequestBody BrandGrossProfitDTO brandGrossProfitDTO) {
        brandGrossProfitService.create(brandId, brandGrossProfitDTO);
        return ResponseEntity.ok().body(brandGrossProfitDTO);
    }

    @PutMapping("/{brandId}/gross-profit")
    public ResponseEntity<BrandGrossProfitDTO> updateBrandGrossProfit(@PathVariable("brandId") String brandId, @RequestBody BrandGrossProfitDTO brandGrossProfitDTO) {
        brandGrossProfitService.update(brandId, brandGrossProfitDTO);
        return ResponseEntity.ok().body(brandGrossProfitDTO);
    }
}
