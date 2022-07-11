package com.vmo.vmoproject.validation;

import com.vmo.vmoproject.dto.BrandGrossProfitDTO;
import com.vmo.vmoproject.dto.SegmentDTO;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidateBrandGrossProfit {
    public static final String EMAIL_PATTERN = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
    public static final String BRAND_ID_PATTERN = "\\d{7,7}";
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
        if (expired_date != null && !expired_date.isAfter(effective_date) && !expired_date.isAfter(ZonedDateTime.now())) {
            return false;
        }
        return true;
    }

    public static boolean validateSegment(BrandGrossProfitDTO brandGrossProfitDTO) {
        double percent = brandGrossProfitDTO.getGrossProfit().getPercent();
        double segmentValue = 0;
        for (SegmentDTO segment : brandGrossProfitDTO.getGrossProfit().getSegments()) {
            segmentValue += segment.getValue();
        }
        if (percent == segmentValue) {
            return true;
        }
        return false;
    }
}
