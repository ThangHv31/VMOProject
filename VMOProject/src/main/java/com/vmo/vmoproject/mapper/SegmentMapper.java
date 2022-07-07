package com.vmo.vmoproject.mapper;

import com.vmo.vmoproject.dto.SegmentDTO;
import com.vmo.vmoproject.entities.Segment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SegmentMapper extends EntityMapper<SegmentDTO, Segment> {
}
