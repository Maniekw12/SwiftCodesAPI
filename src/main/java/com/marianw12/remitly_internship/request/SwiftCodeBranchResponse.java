package com.marianw12.remitly_internship.request;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class SwiftCodeBranchResponse {
    private String address;
    private String bankName;
    private String countryISO2;
    private String countryName;
    @JsonProperty("isHeadquarter")
    @Getter(AccessLevel.NONE)
    private boolean isHeadquarter;
    private String swiftCode;

    @JsonProperty("isHeadquarter")
    public boolean isHeadquarter() {
        return isHeadquarter;
    }
}