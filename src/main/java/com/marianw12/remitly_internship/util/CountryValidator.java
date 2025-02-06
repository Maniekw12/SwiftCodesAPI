package com.marianw12.remitly_internship.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

public class CountryValidator {

    private static final Set<String> ISO_COUNTRY_CODES = new HashSet<>(Arrays.asList(Locale.getISOCountries()));
    private static final Set<String> ISO_COUNTRY_NAMES = new HashSet<>(
            Arrays.stream(Locale.getISOCountries())
                    .map(x -> new Locale("en", x).getDisplayCountry(Locale.ENGLISH))
                    .map(String::toUpperCase)
                    .collect(Collectors.toSet())
    );

    public static boolean isValidIso2Code(String code) {
        return code != null && ISO_COUNTRY_CODES.contains(code.toUpperCase());
    }


    public static boolean isValidCountryName(String countryName) {
        return countryName != null && ISO_COUNTRY_NAMES.contains(countryName.toUpperCase());
    }

}
