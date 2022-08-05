package com.vmo.vmoproject.request;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuditLogPagingRequest {
    private int page;
    private int limit;
}
