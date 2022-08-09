package com.vmo.vmoproject.service;

import com.vmo.vmoproject.dto.BrandGrossProfitDTO;

import java.util.List;

public interface IBrandGrossProfitService {
    BrandGrossProfitDTO createBrandGrossProfit(String id, BrandGrossProfitDTO dto);

    BrandGrossProfitDTO findBrandGrossProfitByBrandId(String id);

    BrandGrossProfitDTO updateBrandGrossProfit(String id, BrandGrossProfitDTO dto);
    List<BrandGrossProfitDTO> findBrandGrossProfitsByPayeeName(String payeeName);
}
