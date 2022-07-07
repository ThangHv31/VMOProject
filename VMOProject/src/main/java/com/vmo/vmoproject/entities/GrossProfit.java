package com.vmo.vmoproject.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GrossProfit {
    private Double percent;
    private Instant effectiveDate;
    private Instant expiredDate;
    private List<Segment> segments;
}
