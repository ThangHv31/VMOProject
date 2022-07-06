package com.vmo.vmoproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SegmentDTO {
    @NotBlank(message = "Segment type must not be null!")
    private String type;
    private Double value;
}
