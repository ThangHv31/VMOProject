package com.vmo.vmoproject.validation;

import com.vmo.vmoproject.dto.BrandGrossProfitDTO;
import com.vmo.vmoproject.dto.SegmentDTO;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidateBrandGrossProfit {
    public static final String EMAIL_PATTERN = "^[a-zA-Z\\d_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z\\d.-]+$";
    public static final String BRAND_ID_PATTERN = "\\d{7}";

    public static boolean validateEmail(String email) {
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
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

    public static boolean validateBrandId(String number) {
        Pattern pattern = Pattern.compile(BRAND_ID_PATTERN);
        Matcher matcher = pattern.matcher(number);
        return matcher.matches();
    }

    public static boolean validateExpiredDate(ZonedDateTime effective_date, ZonedDateTime expired_date) {
        return expired_date.isAfter(effective_date) || expired_date.isAfter(ZonedDateTime.now());
    }

    public static boolean validatePercent(Double percent) {
        return percent > 0 && percent < 100;
    }

    public static boolean validateSegment(BrandGrossProfitDTO brandGrossProfitDTO) {
        double percent = brandGrossProfitDTO.getGrossProfit().getPercent();
        double segmentValue = 0;
        for (SegmentDTO segment : brandGrossProfitDTO.getGrossProfit().getSegments()) {
            segmentValue += segment.getValue();
        }
        return percent == segmentValue;
    }
}
