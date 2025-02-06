package com.marianw12.remitly_internship.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class CountryValidatorTest {

    @Test
    public void shouldReturnTrueWhenCorrectCode() {
        boolean isValid = CountryValidator.isValidIso2Code("PL");
        assertTrue(isValid);
    }

    @Test
    public void shouldReturnFalseWhenIncorrectCode() {
        boolean isValid = CountryValidator.isValidIso2Code("PX");
        assertFalse(isValid);
    }

    @Test
    public void shouldReturnFalseWhenIncorrectCountryCode() {
        boolean isValid = CountryValidator.isValidIso2Code(null);
        assertFalse(isValid);
    }

    @Test
    public void shouldReturnFalseWhenEmptyCountryCode() {
        boolean isValid = CountryValidator.isValidIso2Code("");
        assertFalse(isValid);
    }

    @Test
    public void shouldReturnTrueWhenCountryNameIsValid() {
        boolean isValid = CountryValidator.isValidCountryName("Poland");
        assertTrue(isValid);

        boolean isValidLowerCase = CountryValidator.isValidCountryName("germany");
        assertTrue(isValidLowerCase);

        boolean isValidUpperCase = CountryValidator.isValidCountryName("FRANCE");
        assertTrue(isValidUpperCase);
    }

    @Test
    public void shouldReturnFalseWhenCountryNameIsInvalid() {
        boolean isValid = CountryValidator.isValidCountryName("Fance");
        assertFalse(isValid);
    }

    @Test
    public void shouldReturnFalseWhenCountryNameIsNull() {
        boolean isValid = CountryValidator.isValidCountryName(null);
        assertFalse(isValid);
    }

    @Test
    public void shouldReturnFalseWhenCountryNameIsEmpty() {
        boolean isValid = CountryValidator.isValidCountryName("");
        assertFalse(isValid);
    }

}
