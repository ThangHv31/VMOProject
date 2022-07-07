package com.vmo.vmoproject.mapper;

import com.vmo.vmoproject.dto.BrandGrossProfitDTO;
import com.vmo.vmoproject.entities.BrandGrossProfit;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BrandGrossProfitMapper extends EntityMapper<BrandGrossProfitDTO, BrandGrossProfit> {
    @Override
    @Mapping(source = "brandId",target = "brand_id")
    BrandGrossProfitDTO toDTO(BrandGrossProfit entity);

    @Override
    @Mapping(source = "brand_id", target = "brandId")
    BrandGrossProfit toEntity(BrandGrossProfitDTO dto);
}
