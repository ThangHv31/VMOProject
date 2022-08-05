package com.vmo.vmoproject.response;

import com.vmo.vmoproject.dto.BrandGrossProfitAuditLogDTO;
import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuditLogPagingResponse {
    private int totalPage;
    private int page;
    private List<BrandGrossProfitAuditLogDTO> auditLogDTOList;
}
