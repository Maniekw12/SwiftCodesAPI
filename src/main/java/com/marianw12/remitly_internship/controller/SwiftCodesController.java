package com.marianw12.remitly_internship.controller;

import com.marianw12.remitly_internship.request.CreateSwiftCodeRequest;
import com.marianw12.remitly_internship.service.SwiftCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RequestMapping("/v1/swift-codes")
@RestController
public class SwiftCodesController {

    @Autowired
    private SwiftCodeService swiftCodeService;

    @GetMapping("/{swiftCode}")
    public String getSwiftCodes(@PathVariable String swiftCode) {
        return swiftCodeService.getSwiftCode(swiftCode);
    }

    @GetMapping("/country/{countryCode}")
    public String getCountryCodes(@PathVariable String countryCode) {
        return swiftCodeService.getCountrySwiftCode(countryCode);
    }

    @PostMapping()
    public String createSwiftCode(@RequestBody CreateSwiftCodeRequest swiftCode) {
        return swiftCodeService.createSwiftCode(swiftCode);
    }

    @DeleteMapping("/{swiftCode}")
    public String deleteSwiftCode(@PathVariable String swiftCode) {
        return swiftCodeService.deleteSwiftCode(swiftCode);
    }

}
