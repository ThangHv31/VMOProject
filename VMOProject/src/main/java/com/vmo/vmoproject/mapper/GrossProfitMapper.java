package com.vmo.vmoproject.mapper;

import com.vmo.vmoproject.dto.GrossProfitDTO;
import com.vmo.vmoproject.entities.GrossProfit;
import org.mapstruct.Mapper;

@Mapper
public interface GrossProfitMapper extends EntityMapper<GrossProfitDTO, GrossProfit>{
}
