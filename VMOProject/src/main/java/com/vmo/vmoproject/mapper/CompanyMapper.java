package com.vmo.vmoproject.mapper;

import com.vmo.vmoproject.dto.CompanyDTO;
import com.vmo.vmoproject.entities.Company;
import org.mapstruct.Mapper;

@Mapper
public interface CompanyMapper extends EntityMapper<CompanyDTO, Company> {
}
