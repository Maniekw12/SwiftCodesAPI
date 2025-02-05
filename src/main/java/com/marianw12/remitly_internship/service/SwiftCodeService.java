package com.marianw12.remitly_internship.service;


import com.marianw12.remitly_internship.entity.SwiftCodeEntity;
import com.marianw12.remitly_internship.exception.DuplicatedSwiftCodeException;
import com.marianw12.remitly_internship.exception.InvalidCountryException;
import com.marianw12.remitly_internship.exception.InvalidSwiftCodeException;
import com.marianw12.remitly_internship.exception.NonExistingSwiftCodeException;
import com.marianw12.remitly_internship.repository.SwiftCodeRepository;
import com.marianw12.remitly_internship.request.CreateSwiftCodeRequest;
import com.marianw12.remitly_internship.request.SwiftCodeBranchResponse;
import com.marianw12.remitly_internship.util.CountryValidator;
import com.marianw12.remitly_internship.util.SwiftCodeValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class SwiftCodeService {
    @Autowired
    SwiftCodeRepository swiftCodeRepository;

    public SwiftCodeBranchResponse getSwiftCode(String swiftCode) {
        validateSwiftCode(swiftCode);

        Optional<SwiftCodeEntity> result = swiftCodeRepository.findBySwiftCode(swiftCode.toUpperCase());

        if (result.isPresent()) {
            SwiftCodeEntity swiftCodeEntity = result.get();
            return SwiftCodeBranchResponse.builder()
                    .swiftCode(swiftCodeEntity.getSwiftCode())
                    .isHeadquarter(swiftCodeEntity.isHeadquarter())
                    .bankName(swiftCodeEntity.getBankName())
                    .countryISO2(swiftCodeEntity.getCountryIso2())
                    .address(swiftCodeEntity.getAddress())
                    .countryName(swiftCodeEntity.getCountryName())
                    .build();
        }
        throw new NonExistingSwiftCodeException("Swift code " + swiftCode + " not found");
    }

    public List<SwiftCodeBranchResponse> getCountrySwiftCodes(String countryCode) {
        validateCountryCode(countryCode);
        List<SwiftCodeEntity> countrySwiftCodes = swiftCodeRepository.findByCountryIso2(countryCode.toUpperCase());

        List<SwiftCodeBranchResponse> swiftCodeBranchResponses = new ArrayList<>();
        for (SwiftCodeEntity swiftCodeEntity : countrySwiftCodes) {
            swiftCodeBranchResponses.add(SwiftCodeBranchResponse.builder()
                    .swiftCode(swiftCodeEntity.getSwiftCode())
                    .isHeadquarter(swiftCodeEntity.isHeadquarter())
                    .bankName(swiftCodeEntity.getBankName())
                    .countryISO2(swiftCodeEntity.getCountryIso2())
                    .address(swiftCodeEntity.getAddress())
                    .countryName(swiftCodeEntity.getCountryName())
                    .build());
        }

        return swiftCodeBranchResponses;
    }

    public void createSwiftCode(CreateSwiftCodeRequest swiftCodeRequest) {
        validateSwiftCode(swiftCodeRequest.getSwiftCode());
        validateCountryCode(swiftCodeRequest.getCountryISO2());
        validateCountryName(swiftCodeRequest.getCountryName());
        if(swiftCodeRepository.existsById(swiftCodeRequest.getSwiftCode().toUpperCase())){
           throw new DuplicatedSwiftCodeException("Swift code: " + swiftCodeRequest.getSwiftCode() + " already exists");
        }


        //TODO SAVE AS UPPER CASE
        swiftCodeRepository.save(
                SwiftCodeEntity.
                        builder().
                        swiftCode(swiftCodeRequest.getSwiftCode()).
                        countryIso2(swiftCodeRequest.getCountryISO2()).
                        countryName(swiftCodeRequest.getCountryName()).
                        address(swiftCodeRequest.getAddress()).
                        isHeadquarter(swiftCodeRequest.isHeadquarter()).
                        bankName(swiftCodeRequest.getBankName()).
                        build()
        );


    }

    public void deleteSwiftCode(String swiftCode) {
        validateSwiftCode(swiftCode);
        if(!swiftCodeRepository.existsById(swiftCode.toUpperCase())){
            throw new NonExistingSwiftCodeException("Swift code " + swiftCode + " not found");
        }
        swiftCodeRepository.deleteById(swiftCode.toUpperCase());

    }


    private void validateSwiftCode(String swiftCode) {
        if (!SwiftCodeValidator.isValid(swiftCode)) {
            throw new InvalidSwiftCodeException("Invalid Swift Code: " + swiftCode);
        }
    }

    private void validateCountryCode(String countryCode) {
        if (!CountryValidator.isValidIso2Code(countryCode)) {
            throw new InvalidCountryException("Invalid Country Code: " + countryCode);
        }
    }

    private void validateCountryName(String countryName) {
        if (!CountryValidator.isValidCountryName(countryName)) {
            throw new InvalidCountryException("Invalid Country Name: " + countryName);
        }
    }


}
