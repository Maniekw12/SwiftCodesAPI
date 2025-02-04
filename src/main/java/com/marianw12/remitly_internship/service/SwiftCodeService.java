package com.marianw12.remitly_internship.service;


import com.marianw12.remitly_internship.request.CreateSwiftCodeRequest;
import org.springframework.stereotype.Service;

@Service
public class SwiftCodeService {

    public String getSwiftCode(String swiftCode){
        return "swiftCode=" + swiftCode;
    }

    public String getCountrySwiftCode(String swiftCode){
        return "countrySwiftCode=" + swiftCode;
    }

    public String createSwiftCode(CreateSwiftCodeRequest swiftCodeRequest){
        return "swiftCode=" + swiftCodeRequest.getSwiftCode();
    }

    public String deleteSwiftCode(String swiftCode){
        return "swiftCode=" + swiftCode;
    }


}
