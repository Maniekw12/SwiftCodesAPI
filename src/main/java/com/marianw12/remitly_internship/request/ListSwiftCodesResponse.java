package com.marianw12.remitly_internship.request;

import java.util.List;
import lombok.Builder;


@Builder
public class ListSwiftCodesResponse {
    public String countryISO2;
    public String countryName;
    List<SwiftCodeBranchResponse> swiftCodes;
}
