package com.marianw12.remitly_internship.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateSwiftCodeRequest {
    public String address;
    public String bankName;
    public String countryISO2;
    public String countryName;
    public boolean isHeadquarter;
    public String swiftCode;
}



