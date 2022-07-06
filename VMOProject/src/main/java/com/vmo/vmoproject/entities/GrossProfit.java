package com.vmo.vmoproject.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GrossProfit {
    private Double percent;
    private ZonedDateTime effectiveDate;
    private ZonedDateTime expiredDate;
    private List<Segment> segments;
}
