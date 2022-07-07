package com.vmo.vmoproject.service;

import com.vmo.vmoproject.dto.BrandGrossProfitDTO;

public interface IBrandGrossProfitService {
    BrandGrossProfitDTO create(String id, BrandGrossProfitDTO dto);

    BrandGrossProfitDTO findById(String id);

    BrandGrossProfitDTO update(String id, BrandGrossProfitDTO dto);
}
