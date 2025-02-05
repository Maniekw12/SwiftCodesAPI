package com.marianw12.remitly_internship.controller;

import com.marianw12.remitly_internship.request.BaseResponse;
import com.marianw12.remitly_internship.request.CreateSwiftCodeRequest;
import com.marianw12.remitly_internship.request.SwiftCodeBranchResponse;
import com.marianw12.remitly_internship.service.SwiftCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequestMapping("/v1/swift-codes")
@RestController
public class SwiftCodesController {

    @Autowired
    private SwiftCodeService swiftCodeService;

    @GetMapping("/{swiftCode}")
    public SwiftCodeBranchResponse getSwiftCodes(@PathVariable String swiftCode) {
        return swiftCodeService.getSwiftCode(swiftCode);
    }

    @GetMapping("/country/{countryCode}")
    public List<SwiftCodeBranchResponse> getCountryCodes(@PathVariable String countryCode) {
        return swiftCodeService.getCountrySwiftCodes(countryCode);
    }

    @PostMapping()
    public BaseResponse createSwiftCode(@RequestBody CreateSwiftCodeRequest swiftCode) {
        swiftCodeService.createSwiftCode(swiftCode);
        return new BaseResponse("Swift code created: "+ swiftCode.getSwiftCode());
    }

    @DeleteMapping("/{swiftCode}")
    public BaseResponse deleteSwiftCode(@PathVariable String swiftCode) {
        swiftCodeService.deleteSwiftCode(swiftCode);
        return new BaseResponse("Swift code deleted: "+ swiftCode);
    }

}
