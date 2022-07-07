package com.vmo.vmoproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GrossProfitDTO {
    private Double percent;
    @NotNull(message = "effectiveDate must not be null!")
    private ZonedDateTime effectiveDate;
    private ZonedDateTime expiredDate;
    @Valid
    @NotNull(message = "Gross Profit must not be null!")
    private List<SegmentDTO> segments;
}
