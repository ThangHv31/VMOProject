package com.vmo.vmoproject.controller;

import com.vmo.vmoproject.dto.BrandGrossProfitAuditLogDTO;
import com.vmo.vmoproject.service.impl.BrandGrossProfitAuditLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/api/brands")
public class BrandGrossProfitAuditLogController {
    @Autowired
    private BrandGrossProfitAuditLogService auditLogService;

    @GetMapping("/{brandId}/gross-profit/audit-log")
    public ResponseEntity<List<BrandGrossProfitAuditLogDTO>> getAuditLog(@PathVariable("brandId") String brandId) {
        return ResponseEntity.ok().body(auditLogService.findById(brandId));
    }
}
