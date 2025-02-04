package com.marianw12.remitly_internship.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "swift_codes")
public class SwiftCodeEntity {
    @Id
    String swiftCode; // TODO specify length
    String address;
    String bankName;
    String countryCode;
    String countryName;
    boolean isHeadQuarter;


    public String getSwiftCode() {
        return swiftCode;
    }

    public void setSwiftCode(String swiftCode) {
        this.swiftCode = swiftCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public boolean isHeadQuarter() {
        return isHeadQuarter;
    }

    public void setHeadQuarter(boolean headQuarter) {
        isHeadQuarter = headQuarter;
    }
}
