package com.marianw12.remitly_internship.mapper;

import com.marianw12.remitly_internship.entity.SwiftCodeEntity;
import com.marianw12.remitly_internship.request.CreateSwiftCodeRequest;
import com.marianw12.remitly_internship.request.SwiftCodeBranchResponse;
import com.marianw12.remitly_internship.request.SwiftCodeHeadquarterResponse;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class SwiftCodeMapper {

    public List<SwiftCodeBranchResponse> mapToResponse(List<SwiftCodeEntity> swiftCodeEntities) {
        List<SwiftCodeBranchResponse> swiftCodeBranchResponses = new ArrayList<>();

        for (SwiftCodeEntity swiftCodeEntity : swiftCodeEntities) {
            swiftCodeBranchResponses.add(mapToResponse(swiftCodeEntity));
        }
        return swiftCodeBranchResponses;

    }

    public SwiftCodeBranchResponse mapToResponse(SwiftCodeEntity swiftCodeEntity) {
        return SwiftCodeBranchResponse.builder()
                .swiftCode(swiftCodeEntity.getSwiftCode())
                .isHeadquarter(swiftCodeEntity.isHeadquarter())
                .bankName(swiftCodeEntity.getBankName())
                .countryISO2(swiftCodeEntity.getCountryIso2())
                .address(swiftCodeEntity.getAddress())
                .countryName(swiftCodeEntity.getCountryName())
                .build();
    }

   public SwiftCodeEntity mapToEntity(CreateSwiftCodeRequest swiftCodeRequest) {

       return SwiftCodeEntity.
               builder().
               swiftCode(toUpperCase(swiftCodeRequest.getSwiftCode())).
               countryIso2(toUpperCase(swiftCodeRequest.getCountryISO2())).
               countryName(toUpperCase(swiftCodeRequest.getCountryName())).
               address(toUpperCase(swiftCodeRequest.getAddress())).
               isHeadquarter(swiftCodeRequest.isHeadquarter()).
               bankName(toUpperCase(swiftCodeRequest.getBankName())).
               build();
   }

   public SwiftCodeHeadquarterResponse mapToResponse(SwiftCodeEntity swiftCodeEntity, List<SwiftCodeEntity> branches) {
       List<SwiftCodeBranchResponse> branchResponses = mapToResponse(branches);
       return SwiftCodeHeadquarterResponse
               .builder()
               .swiftCode(swiftCodeEntity.getSwiftCode())
               .isHeadquarter(swiftCodeEntity.isHeadquarter())
               .bankName(swiftCodeEntity.getBankName())
               .countryISO2(swiftCodeEntity.getCountryIso2())
               .address(swiftCodeEntity.getAddress())
               .countryName(swiftCodeEntity.getCountryName())
               .branches(branchResponses).build();
   }


   private String toUpperCase(String str) {
        return str != null ? str.toUpperCase() : "";
   }


}
