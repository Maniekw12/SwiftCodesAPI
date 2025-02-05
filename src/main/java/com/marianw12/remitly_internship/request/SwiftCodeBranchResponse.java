package com.marianw12.remitly_internship.request;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor

public class SwiftCodeBranchResponse {
    public String address;
    public String bankName;
    public String countryISO2;
    public String countryName;
    public boolean isHeadquarter;
    public String swiftCode;
}
