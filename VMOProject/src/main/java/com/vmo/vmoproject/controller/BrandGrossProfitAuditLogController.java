package com.vmo.vmoproject.controller;

import com.vmo.vmoproject.dto.BrandGrossProfitAuditLogDTO;
import com.vmo.vmoproject.response.AuditLogPagingResponse;
import com.vmo.vmoproject.service.impl.BrandGrossProfitAuditLogService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/api/brands")
public class BrandGrossProfitAuditLogController {
    private final BrandGrossProfitAuditLogService auditLogService;

    public BrandGrossProfitAuditLogController(BrandGrossProfitAuditLogService auditLogService) {
        this.auditLogService = auditLogService;
    }

    @GetMapping("/{brandId}/gross-profit/audit-log")
    public ResponseEntity<List<BrandGrossProfitAuditLogDTO>> getAuditLogByBrandId(@PathVariable("brandId") String brandId) {
        return ResponseEntity.ok().body(auditLogService.findBrandGrossProfitAuditLogByBrandId(brandId));
    }

    @GetMapping("/{brandId}/gross-profit/audit-logs")
    public ResponseEntity<AuditLogPagingResponse> getAuditLogPaging(@PathVariable("brandId") String brandId
            , @RequestParam(value = "page", required = false) int page, @RequestParam(value = "limit", required = false) int limit) {
        return ResponseEntity.ok().body(auditLogService.findAuditLogsByBrandIdPaging(brandId, page, limit));
    }
    @GetMapping("/gross-profit/audit-log")
    public ResponseEntity<List<BrandGrossProfitAuditLogDTO>> getAuditLogByEvent(@RequestParam(value = "event") String event) {
        return ResponseEntity.ok().body(auditLogService.findBrandGrossProfitAuditLogsByEvent(event));
    }
}
