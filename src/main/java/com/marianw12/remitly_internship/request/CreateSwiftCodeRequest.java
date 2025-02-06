package com.marianw12.remitly_internship.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateSwiftCodeRequest {
    private String address;
    private String bankName;
    private String countryISO2;
    private String countryName;
    private boolean isHeadquarter;
    private String swiftCode;
}