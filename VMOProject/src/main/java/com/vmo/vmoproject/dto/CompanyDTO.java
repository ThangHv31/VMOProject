package com.vmo.vmoproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyDTO {
    @NotEmpty(message = "Segment type must not be null!")
    private String name;
    @NotEmpty(message = "address type must not be null!")
    private String address;
    @NotEmpty(message = "subDistrict type must not be null!")
    private String subDistrict;
    @NotEmpty(message = "Segment type must not be null!")
    private String district;
    @NotEmpty(message = "province type must not be null!")
    private String province;
    @NotEmpty(message = "postcode type must not be null!")
    @Size(min = 5, max = 5, message = "postcode type must have 5 digits!")
    private String postcode;
}
