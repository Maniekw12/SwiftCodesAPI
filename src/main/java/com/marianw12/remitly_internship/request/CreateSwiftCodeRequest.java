package com.marianw12.remitly_internship.request;

public class CreateSwiftCodeRequest {
    public String address;
    public String bankName;
    public String countryISO2;
    public String countryName;
    public boolean isHeadquarter;
    public String swiftCode;

    public CreateSwiftCodeRequest(String bankName, String address, String countryISO2, String countryName, boolean isHeadquarter, String swiftCode) {
        this.bankName = bankName;
        this.address = address;
        this.countryISO2 = countryISO2;
        this.countryName = countryName;
        this.isHeadquarter = isHeadquarter;
        this.swiftCode = swiftCode;
    }

    public String getCountryISO2() {
        return countryISO2;
    }

    public String getBankName() {
        return bankName;
    }

    public String getAddress() {
        return address;
    }

    public String getCountryName() {
        return countryName;
    }

    public boolean isHeadquarter() {
        return isHeadquarter;
    }

    public String getSwiftCode() {
        return swiftCode;
    }
}





