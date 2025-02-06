package com.marianw12.remitly_internship.service;


import com.marianw12.remitly_internship.entity.SwiftCodeEntity;
import com.marianw12.remitly_internship.exception.DuplicatedSwiftCodeException;
import com.marianw12.remitly_internship.exception.InvalidCountryException;
import com.marianw12.remitly_internship.exception.InvalidSwiftCodeException;
import com.marianw12.remitly_internship.exception.NonExistingSwiftCodeException;
import com.marianw12.remitly_internship.mapper.SwiftCodeMapper;
import com.marianw12.remitly_internship.repository.SwiftCodeRepository;
import com.marianw12.remitly_internship.request.CreateSwiftCodeRequest;
import com.marianw12.remitly_internship.request.SwiftCodeBranchResponse;
import com.marianw12.remitly_internship.util.CountryValidator;
import com.marianw12.remitly_internship.util.SwiftCodeValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class SwiftCodeService {
    @Autowired
    SwiftCodeRepository swiftCodeRepository;
    @Autowired
    SwiftCodeMapper swiftCodeMapper;

    public SwiftCodeBranchResponse getSwiftCode(String swiftCode) {
        validateSwiftCode(swiftCode);

        Optional<SwiftCodeEntity> result = swiftCodeRepository.findBySwiftCode(swiftCode.toUpperCase());

        if (result.isPresent()) {
            SwiftCodeEntity swiftCodeEntity = result.get();

            if(SwiftCodeValidator.isHeadquarter(swiftCodeEntity.getSwiftCode())){
                String prefix = swiftCodeEntity.getSwiftCode().substring(0, 8);
                List<SwiftCodeEntity> branches = swiftCodeRepository.findBySwiftCodeStartingWith(prefix);
                branches = branches
                        .stream()
                        .filter(entity -> !SwiftCodeValidator.isHeadquarter(entity.getSwiftCode()))
                        .collect(Collectors.toList());

                return swiftCodeMapper.mapToResponse(swiftCodeEntity,branches);

            }
            return swiftCodeMapper.mapToResponse(swiftCodeEntity);
        }
        throw new NonExistingSwiftCodeException("Swift code " + swiftCode + " not found");
    }

    public List<SwiftCodeBranchResponse> getCountrySwiftCodes(String countryCode) {
        validateCountryCode(countryCode);
        List<SwiftCodeEntity> countrySwiftCodes = swiftCodeRepository.findByCountryIso2(countryCode.toUpperCase());

        return swiftCodeMapper.mapToResponse(countrySwiftCodes);
    }

    public void createSwiftCode(CreateSwiftCodeRequest swiftCodeRequest) {
        validateSwiftCode(swiftCodeRequest.getSwiftCode());
        validateCountryCode(swiftCodeRequest.getCountryISO2());
        validateCountryName(swiftCodeRequest.getCountryName());
        if(swiftCodeRepository.existsById(swiftCodeRequest.getSwiftCode().toUpperCase())){
           throw new DuplicatedSwiftCodeException("Swift code: " + swiftCodeRequest.getSwiftCode() + " already exists");
        }


        swiftCodeRepository.save(
                swiftCodeMapper.mapToEntity(swiftCodeRequest)
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
