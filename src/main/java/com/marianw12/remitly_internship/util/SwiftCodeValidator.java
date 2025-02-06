package com.marianw12.remitly_internship.util;

import java.util.regex.Pattern;

public class SwiftCodeValidator {
    private static final Pattern SWIFT_CODE_PATTERN = Pattern.compile("^[A-Z]{4}[A-Z]{2}[A-Z0-9]{2}([A-Z0-9]{3})$");
    private static final String HEADQUARTER_SUFFIX = "XXX";

    public static boolean isValid(String swiftCode) {
        return swiftCode != null && SWIFT_CODE_PATTERN.matcher(swiftCode).matches();
    }

    public static boolean isHeadquarter(String swiftCode) {
        return swiftCode != null && swiftCode.endsWith(HEADQUARTER_SUFFIX);
    }

}
