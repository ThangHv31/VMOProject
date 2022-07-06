package com.vmo.vmoproject.validation;

import com.vmo.vmoproject.constant.TypeOfError;
import com.vmo.vmoproject.dto.BrandGrossProfitDTO;
import com.vmo.vmoproject.dto.SegmentDTO;
import com.vmo.vmoproject.exception.BadRequestException;
import com.vmo.vmoproject.exception.Errors;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidateBrandGrossProfit {
    public static final String DATE_PATTERN = "\\d{2}-\\d{2}-\\d{4}";
    public static final String EMAIL_PATTERN = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
    public static final String BRAND_ID_PATTERN = "\\d{7,7}";
    public static final String PERCENT_PATTERN = "\\d{1,2}";
    public static final String TAX_ID_PATTERN = "\\d{13,13}";

    public static boolean validateEmail(String email) {
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static boolean validateBrandGrossProfit(BrandGrossProfitDTO dto) {
        List<Errors> errorsList = new ArrayList<>();
        if (validateListEmails(dto.getSettlementReportEmails()) == false) {
            errorsList.add(TypeOfError.SETTLEMENT_REPORT_EMAIL_INCORRECT_EMAIL_FORMAT);
        }
        if (validateListEmails(dto.getDailyReportEmails()) == false) {
            errorsList.add(TypeOfError.DAILY_REPORT_EMAIL_INCORRECT_EMAIL_FORMAT);
        }
        if (checkExpiredDate(dto.getGrossProfit().getEffectiveDate(), dto.getGrossProfit().getExpiredDate()) == false) {
            errorsList.add(TypeOfError.EFFECTIVEDATE_MUST_BE_BEFORE_EXPIREDDATE);
        }
        if (ckeckSegment(dto) == false) {
            errorsList.add(TypeOfError.SUM_OF_VALUE_IN_SECTIONS_IS_NOT_EQUAL_TO_PERCENT);
            throw new BadRequestException(errorsList);
        }
        return true;
    }

    public static boolean validateBrandId(String number) {
        Pattern pattern = Pattern.compile(BRAND_ID_PATTERN);
        Matcher matcher = pattern.matcher(number);
        return matcher.matches();
    }

    public static boolean validateTaxId(String number) {
        Pattern pattern = Pattern.compile(TAX_ID_PATTERN);
        Matcher matcher = pattern.matcher(number);
        return matcher.matches();
    }
    public static boolean validatePercent(String number) {
        Pattern pattern = Pattern.compile(PERCENT_PATTERN);
        Matcher matcher = pattern.matcher(number);
        return matcher.matches();
    }
    public static boolean validateListEmails(List<String> emails) {
        for (String mail : emails) {
            if (!validateEmail(mail)) {
                return false;
            }
        }
        return true;
    }

    public static boolean checkExpiredDate(ZonedDateTime effective_date, ZonedDateTime expired_date) {
        if (expired_date != null && !expired_date.isAfter(effective_date) && !expired_date.isAfter(ZonedDateTime.now())) {
            return false;
        }
        return true;
    }

    private static boolean ckeckSegment(BrandGrossProfitDTO brandGrossProfitDTO) {
        double percent =brandGrossProfitDTO.getGrossProfit().getPercent();
        double segmentValue = 0;
        for (SegmentDTO segment : brandGrossProfitDTO.getGrossProfit().getSegments()) {
            segmentValue += segment.getValue();
        }
        if (percent == segmentValue) {
            return true;
        }
        return false;
    }

    private static boolean checkEmptyData(BrandGrossProfitDTO dto) {
        return true;
    }
}
