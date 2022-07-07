package com.vmo.vmoproject.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Company {
    private String name;
    private String address;
    private String subDistrict;
    private String district;
    private String province;
    private String postcode;
}
