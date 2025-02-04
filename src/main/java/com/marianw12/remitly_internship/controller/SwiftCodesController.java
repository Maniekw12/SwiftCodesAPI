package com.marianw12.remitly_internship.controller;

import com.marianw12.remitly_internship.request.CreateSwiftCodeRequest;
import org.springframework.web.bind.annotation.*;


@RequestMapping("/v1/swift-codes")
@RestController
public class SwiftCodesController {

    @GetMapping("/{swiftCode}")
    public String getSwiftCodes(@PathVariable String swiftCode) {
        return swiftCode;
    }

    @GetMapping("/country/{countryCode}")
    public String getCountryCodes(@PathVariable String countryCode) {
        return countryCode;
    }

    @PostMapping()
    public String createSwiftCode(@RequestBody CreateSwiftCodeRequest swiftCode) {
        return "OK";
    }

    @DeleteMapping("/{swiftCode}")
    public String deleteSwiftCode(@PathVariable String swiftCode) {
        return "OK";
    }

}
