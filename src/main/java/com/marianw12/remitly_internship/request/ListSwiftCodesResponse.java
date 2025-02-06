package com.marianw12.remitly_internship.request;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ListSwiftCodesResponse {
    private String countryISO2;
    private String countryName;
    private List<SwiftCodeBranchResponse> swiftCodes;
}
