package com.marianw12.remitly_internship.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class SwiftCodeHeadquarterResponse extends SwiftCodeBranchResponse {
    private List<SwiftCodeBranchResponse> branches;
}
