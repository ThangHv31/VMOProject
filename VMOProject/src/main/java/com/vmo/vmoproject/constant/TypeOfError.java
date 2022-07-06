package com.vmo.vmoproject.constant;

import com.vmo.vmoproject.exception.Errors;

public class TypeOfError {
    public static final Errors BRANDID_NOT_FOUND = new Errors("GP-001", "brand id not found in brand content type");
    public static final Errors GROSS_PROFIT_FOR_BRAND_ID_EXIST = new Errors("GP-002", "Gross profit for brand id already exist. Cannot be create.");

    public static final Errors BANKCODE_INVALID = new Errors("GP-003", "Invalid bank code");
    public static final Errors SETTLEMENT_REPORT_EMAIL_INCORRECT_EMAIL_FORMAT = new Errors("GP-004", "settlement report email is incorrect email format");
    public static final Errors DAILY_REPORT_EMAIL_INCORRECT_EMAIL_FORMAT = new Errors("GP-005", "daily report email is incorrect email format");
    public static final Errors EFFECTIVEDATE_MUST_BE_BEFORE_EXPIREDDATE = new Errors("GP-006", "effectiveDate must be before expiredDate");
    public static final Errors SUM_OF_VALUE_IN_SECTIONS_IS_NOT_EQUAL_TO_PERCENT = new Errors("GP-007", "Total value in sections must equal to percent");
    public static final Errors BRAND_ID_MUST_HAVE_7_DIGITS = new Errors("brand_id must have 7 digits");
    public static final Errors TAX_ID_MUST_HAVE_13_DIGITS = new Errors("tax_id must have 13 digits");
    public static final Errors NOT_EMPTY = new Errors("NOT-EMPTY", "data cannot be empty");

}
