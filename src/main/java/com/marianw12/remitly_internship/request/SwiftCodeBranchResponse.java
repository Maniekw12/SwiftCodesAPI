package com.marianw12.remitly_internship.request;


import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class SwiftCodeBranchResponse {
    public String address;
    public String bankName;
    public String countryISO2;
    public String countryName;
    public boolean isHeadquarter;
    public String swiftCode;
}
