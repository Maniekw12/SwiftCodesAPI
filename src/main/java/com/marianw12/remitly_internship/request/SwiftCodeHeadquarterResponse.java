package com.marianw12.remitly_internship.request;

import lombok.AllArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.util.List;

@SuperBuilder
@AllArgsConstructor
public class SwiftCodeHeadquarterResponse extends SwiftCodeBranchResponse {
    public List<SwiftCodeBranchResponse> branches;
}
