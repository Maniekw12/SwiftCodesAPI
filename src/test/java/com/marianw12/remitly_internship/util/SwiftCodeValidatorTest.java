package com.marianw12.remitly_internship.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SwiftCodeValidatorTest {
    @Test
    public void shouldReturnTrueForValidSwiftCode() {
        assertTrue(SwiftCodeValidator.isValid("AAISALTRXXX"));
    }

    @Test
    public void shouldReturnTrueForValidSwiftCodeWithBranch() {
        assertTrue(SwiftCodeValidator.isValid("BCHICLR10R2"));
    }

    @Test
    public void shouldReturnFalseForNullSwiftCode() {
        assertFalse(SwiftCodeValidator.isValid(null));
    }

    @Test
    public void shouldReturnFalseForEmptySwiftCode() {
        assertFalse(SwiftCodeValidator.isValid(""));
    }

    @Test
    public void shouldReturnFalseForLowerCaseSwiftCode() {
        assertFalse(SwiftCodeValidator.isValid("BchICLR10R2"));
        assertFalse(SwiftCodeValidator.isValid("bchiclr10r2"));
        assertFalse(SwiftCodeValidator.isValid("aaisALTRXXX"));
    }

    @Test
    public void shouldReturnFalseForTooShortSwiftCode() {
        assertFalse(SwiftCodeValidator.isValid("AAISA"));
    }

    @Test
    public void shouldReturnFalseForSwiftCodeStartingWithNumbers() {
        assertFalse(SwiftCodeValidator.isValid("21ISALTRXXX"));
    }

    @Test
    public void shouldReturnFalseForTooLongSwiftCode() {
        assertFalse(SwiftCodeValidator.isValid("AAISALTRXXXAAISALTRXXX"));
    }

    @Test
    public void shouldReturnFalseForSwiftCodeWithIllegalCharacters() {
        assertFalse(SwiftCodeValidator.isValid("AAA FR12"));
        assertFalse(SwiftCodeValidator.isValid("AAAAFR1*XX"));
        assertFalse(SwiftCodeValidator.isValid("AAAA_FR12"));
    }

    @Test
    public void shouldReturnFalseForSwiftCodeWithLeadingOrTrailingSpace() {
        assertFalse(SwiftCodeValidator.isValid(" AAAAFR12XXX"));
        assertFalse(SwiftCodeValidator.isValid("AAAAFR12XXX "));
    }

    @Test
    public void shouldReturnTrueWhenSwiftCodeIsHeadquarter() {
        assertTrue(SwiftCodeValidator.isHeadquarter("AAISALTRXXX"));
    }

    @Test
    public void shouldReturnFalseWhenSwiftCodeIsNotHeadquarter() {
        assertFalse(SwiftCodeValidator.isHeadquarter("BCHICLR10R2"));
        assertFalse(SwiftCodeValidator.isHeadquarter("AAISALTRSXX"));
    }

    @Test
    public void shouldReturnFalseWhenSwiftCodeIsHeadquarterButInvalid() {
        assertFalse(SwiftCodeValidator.isHeadquarter("BchICLR10R2"));
        assertFalse(SwiftCodeValidator.isHeadquarter("bchiclr10r2"));
    }

    @Test
    public void shouldReturnFalseWhenSwiftCodeIsNull() {
        assertFalse(SwiftCodeValidator.isHeadquarter(null));
    }

    @Test
    public void shouldReturnFalseWhenSwiftCodeIsEmpty() {
        assertFalse(SwiftCodeValidator.isHeadquarter(""));
    }


}
