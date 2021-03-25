package com.aktarulahsan.erp.util;

import com.aktarulahsan.erp.core.config.CustomHttpServletRequest;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.springframework.util.Assert;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.*;

public interface CommonFunctions {




    default Response getSuccessResponse(String message) {
        Response response = new Response();
        response.setSuccess(true);
        response.setMessage(message);
        return response;
    }

    default Response getSuccessResponse(String message, Response response) {
        response.setSuccess(true);
        response.setMessage(message);
        return response;
    }

    default Response getErrorResponse(String message) {
        Response response = new Response();
        response.setSuccess(false);
        response.setMessage(message);
        return response;
    }

    default Response getErrorResponse(String message, Response response) {
        response.setSuccess(false);
        response.setMessage(message);
        return response;
    }


    String SECURED_READ_SCOPE = "#oauth2.hasScope('read')";

    default String strsingleQuotation(String val) {
        String[] values = val.split(",");
        StringBuilder str = new StringBuilder();
        for (int j = 0; j < values.length; j++) {
            if (j > 0) {
                str.append(",");
            }
            String valuesPattern = "'";
            valuesPattern += values[j];
            valuesPattern += "'";
            str.append(valuesPattern);

        }
        return str.toString();
    }

    String SECURED_WRITE_SCOPE = "#oauth2.hasScope('write')";
    String SECURED_PATTERN = "/api/**";



    default HttpServletRequest getCurrentRequest() {

        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        Assert.state(requestAttributes != null, "Could not find current request via RequestContextHolder");
        Assert.isInstanceOf(ServletRequestAttributes.class, requestAttributes);
        HttpServletRequest servletRequest = ((ServletRequestAttributes) requestAttributes).getRequest();
        Assert.state(servletRequest != null, "Could not find current HttpServletRequest");

        return servletRequest;
    }

    default CustomHttpServletRequest userAgent() {

        return new CustomHttpServletRequest(getCurrentRequest());

    }

    @SuppressWarnings("unchecked")
    default <T> List<T> objectMapperReadArrayValue(String mapperArrStr, Class<T> clazz) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {

            return objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).readValue(mapperArrStr, TypeFactory.defaultInstance().constructCollectionType(List.class, clazz));

        } catch (JsonParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        } catch (JsonMappingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    long salutationList = 1005l;

    default String buildStr(String str, Map<String, Object> map) {
        String replaceStr = str;
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if (entry.getValue() instanceof String) {
                replaceStr = replaceStr.replaceAll(entry.getKey(), strsingleQuotation(entry.getValue().toString()));
            } else {
                replaceStr = replaceStr.replaceAll(entry.getKey(), entry.getValue().toString());
            }


        }
        return replaceStr;
    }

    /**
     * @param str
     * @param searchStr
     * @return
     */
    default boolean containsIgnoreCase(String str, String searchStr) {
        if (str != null) {
            return str.toLowerCase().contains(searchStr.toLowerCase());
        } else {
            return false;
        }

    }

    /**
     * @param length
     * @return long
     */

    default long generateRandom(int length) {
        while (true) {
            long numb = (long) (Math.random() * 100000000 * 1000000);
            if (String.valueOf(numb).length() == length)
                return numb;
        }
    }

    default Date thisWeekFirstDate(Date date) {
        Calendar cal = Calendar.getInstance();
        if (date != null) {
            cal.setTime(date);
        } else {
            cal.setTime(new Date());
        }
        cal.set(Calendar.DAY_OF_WEEK, cal.getActualMinimum(Calendar.DAY_OF_WEEK));
        return cal.getTime();
    }

    default Date thisWeekLastDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        if (date != null) {
            calendar.setTime(date);
        } else {
            calendar.setTime(new Date());
        }
        calendar.set(Calendar.DAY_OF_WEEK, 7);
        return calendar.getTime();
    }

    default Date thisMonthFirstDate(Date date) {
        Calendar cal = Calendar.getInstance();
        if (date != null) {
            cal.setTime(date);
        } else {
            cal.setTime(new Date());
        }
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
        return cal.getTime();
    }

    default Date thisMonthLastDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        if (date != null) {
            calendar.setTime(date);
        } else {
            calendar.setTime(new Date());
        }
        calendar.add(Calendar.MONTH, 1); // add one month
        calendar.set(Calendar.DAY_OF_MONTH, 1); // set value day 1
        calendar.add(Calendar.DATE, -1); // minus one date
        return calendar.getTime();
    }

    default Date getYear(Date dateParam) {
        //return deateParse(dateFormat(dateParam, "yyyy"),"yyyy");
        LocalDate Ldate = LocalDate.of(Integer.parseInt(dateFormat(dateParam, "yyyy")), 0, 0);

        Date date = Date.from(Ldate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        return date;

    }


    default Date thisYearFirstDate() {
        // Current Date
        LocalDate currentDate = LocalDate.now();
        LocalDate date = LocalDate.of(currentDate.getYear(), 01, 01);
        Date thisYearFirstDate = null;
        try {
            thisYearFirstDate = new SimpleDateFormat("yyyy-MM-dd").parse(date.toString());
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return thisYearFirstDate;
    }

    default Date thisYearLastDate() {
        // Current Date
        LocalDate currentDate = LocalDate.now();
        LocalDate date = LocalDate.of(currentDate.getYear(), 12, 31);
        Date thisYearLastDate = null;
        try {
            thisYearLastDate = new SimpleDateFormat("yyyy-MM-dd").parse(date.toString());
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return thisYearLastDate;
    }

    default Date thisYearFirstDate(Date date) {

        Calendar calendar = Calendar.getInstance();

        if (date != null) {
            calendar.setTime(date);
        } else {
            calendar.setTime(new Date());
        }
        calendar.set(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 1);

        return clearTime(calendar.getTime());
    }

    default Date thisYearLastDate(Date date) {

        Calendar calendar = Calendar.getInstance();

        if (date != null) {
            calendar.setTime(date);
        } else {
            calendar.setTime(new Date());
        }
        calendar.set(Calendar.MONTH, 11);
        calendar.set(Calendar.DAY_OF_MONTH, 31);

        return clearTime(calendar.getTime());
    }

    default Date oneDayPlus(Date date) {
        Date givenDate = date;
        LocalDate localDate = givenDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        Date oneDayPlusDate = null;
        try {
            oneDayPlusDate = new SimpleDateFormat("yyyy-MM-dd").parse(localDate.plusDays(1).toString());

        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return oneDayPlusDate;
    }

    default Date oneDayPlusClearTime(Date date) {
        Date givenDate = date;
        LocalDateTime localDateTime = givenDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        Date oneDayPlusDate = Date.from(localDateTime.plusDays(1).atZone(ZoneId.systemDefault()).toInstant());
        return oneDayPlusDate;
    }

    default Date clearTime(Date date) {
        Date givenDate = date;
        LocalDate localDate = givenDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        Date localDateClearTime = null;
        try {
            localDateClearTime = new SimpleDateFormat("yyyy-MM-dd").parse(localDate.toString());
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return localDateClearTime;
    }

    default Date deateParse(String dateStr, String dateFormat) {

        Date parseDate = null;

        try {
            parseDate = new SimpleDateFormat(dateFormat).parse(dateStr);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return parseDate;
    }


    default String dateFormat(Date dateStr, String dateFormat) {
        String parseDate = new SimpleDateFormat(dateFormat).format(dateStr);
        return parseDate;
    }



    default BigDecimal convertLongToBigDecimmal(Long logval) {

        if (logval != null) {

            return new BigDecimal(logval);
        }

        return null;
    }

    default BigDecimal convertIntToBigDecimmal(Integer logval) {

        if (logval != null) {

            return new BigDecimal(logval);
        }

        return null;
    }

    default BigDecimal convertFloatToBigDecimmal(Float floatgval) {

        if (floatgval != null) {

            return new BigDecimal(floatgval);
        }

        return null;
    }

    default String objectToJson(Object content) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(content);


        } catch (JsonParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        } catch (JsonMappingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
        // return null;
    }


    default String procedureQuery(String procedureName, int procedureLength) {

        StringBuilder procdureCall = new StringBuilder();
        procdureCall.append("{call ");
        procdureCall.append(procedureName);
        procdureCall.append("(");

        int totalLenght = procedureLength;

        for (int i = 0; i < totalLenght; i++) {
            procdureCall.append("?");
            if (i != totalLenght - 1) {
                procdureCall.append(",");
            }
        }

        procdureCall.append(")}");

        return procdureCall.toString();
    }

    default Date addHourMinutesSeconds(int hour, int minute, int second, Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        calendar.add(Calendar.HOUR_OF_DAY, hour);
        calendar.add(Calendar.MINUTE, minute);
        calendar.add(Calendar.SECOND, second);

        return calendar.getTime();
    }


    default String imageBase64(byte[] imageValue) {

        if (imageValue != null) {

            return Base64.getEncoder().encodeToString(imageValue);
        }

        return null;
    }

    default String imageBase64(Blob imageValue) {

        return imageBase64(blobToByteArray(imageValue));

    }

    default byte[] blobToByteArray(Blob imgBlob) {

        byte[] imageBytes = null;

        try {
            imageBytes = imgBlob.getBytes(1, (int) imgBlob.length());
            return imageBytes;

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return imageBytes;
        }

    }

    default <T> T objectMapperReadValue(String content, Class<T> valueType) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {

            return objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).readValue(content, valueType);

        } catch (JsonParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        } catch (JsonMappingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
        //  return null;
    }

    //lookup detail related
    long prefixLookupNo = 5033l;
    long unitsLookupNo = 5032l;
    long eligibilityLookupNo = 5036l;
    long serviceCategoryLookupNo = 5031l;
    long personCategoryLookupNo = 5037l;
    long rankLookupNo = 5038l;
    long corpsLookupNo = 5039l;
    long nationalityLookupNo = 5041l;
    long priorityLookupNo = 5047l;
    long bloodGroupLookupNo = 1004l;
    long maritalStatusLookupNo = 1084l;
    long religionLookupNo = 1006l;
    long medicalCategoryLookupNo = 5040l;
    long districtLookupNo = 1001l;
    long relationListNo = 1021l;
    long familyLookupNo = 5044l;
    long cneLookupNo = 5045l;
    long reLookupNo = 5046l;
    long jobTypeNo = 1015l;
    long employeeTypeNo = 1007l;
    long hrTypeNo = 1014l;
    long upazilaLookupNo = 5043l;
    long countryLookupNo = 1002l;
    long genderList = 5007l;
    long patientCondition = 1074L;
    long arrivalCondition = 1072L;
    long accompainedBy = 1076L;
    long foodLookupNo = 5049L;

    default <T> T objectMapperReadValueWithDate(String content, Class<T> valueType) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S"));
        try {

            return objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).readValue(content, valueType);

        } catch (JsonParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        } catch (JsonMappingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
        //  return null;
    }

    @SuppressWarnings("unchecked")
    default <T> List<T> getListFromObject(Object data, Class<T> clazz) {

        if (data == null) {
            return null;
        }

        return (List<T>) data;
    }

    @SuppressWarnings("unchecked")
    default <T> T getValueFromObject(Object data, Class<T> clazz) {
        if (data == null) {
            return null;
        }
        return (T) data;
    }


    default LocalDateTime convertToLocalDateTimeViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }

    default LocalDate utilDateCovertToLocalDate(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    default Long durationBetweenTwoDate(Date dateParam1, Date dateParam2) {

        Duration duration = Duration.between(convertToLocalDateTimeViaInstant(dateParam1), convertToLocalDateTimeViaInstant(dateParam2));
        Long different = Math.abs(duration.toMillis());

        return different;

    }


    default String periodBetweenTwoDate(Date dateParam1, Date dateParam2) {
        if (dateParam1 != null && dateParam2 != null) {
            Period diff = Period.between(utilDateCovertToLocalDate(dateParam1), utilDateCovertToLocalDate(dateParam2));
            StringBuilder periodOfDateBetween = new StringBuilder();

            periodOfDateBetween.append(diff.getYears() + " Y ");
            periodOfDateBetween.append(diff.getMonths() + " M ");
            periodOfDateBetween.append(diff.getDays() + " D ");

            return periodOfDateBetween.toString();
        } else {
            return null;
        }
    }

}
