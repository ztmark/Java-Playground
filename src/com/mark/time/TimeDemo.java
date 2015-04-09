package com.mark.time;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 * Author: Mark
 * Date  : 2015/4/9
 * Time  : 14:20
 */
public class TimeDemo {

    public static void main(String[] args) {
//        m1();
//        dateEqual();
//        monthDay();
//        localTime();
//        dateAdd();
//        clock();
//        dateCompare();
//        zone();
//        yearMonth();
//        isLeap();
//        period();
//        zoneStamp();
//        format();
    }

    private static void format() {
        String tomorrow = "20150410"; // 2015410 will rise a exception
        LocalDate formatted = LocalDate.parse(tomorrow, DateTimeFormatter.BASIC_ISO_DATE);
        System.out.println(formatted);

        String today = "2015/01/09";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate todayInFormatted = LocalDate.parse(today, formatter);
        System.out.println(todayInFormatted);

        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy MM dd hh:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String s = now.format(formatter1);
        System.out.println(s);
    }

    private static void zoneStamp() {
        LocalDateTime now = LocalDateTime.now();
        ZoneOffset offset = ZoneOffset.of("-08:30");
        OffsetDateTime offsetDateTime = OffsetDateTime.of(now, offset);
        System.out.println(offsetDateTime);

        Instant timestamp = Instant.now();
        System.out.println(timestamp); // UTC
    }

    private static void period() {
        LocalDate today = LocalDate.now();
        System.out.println(today);
        LocalDate birthday = LocalDate.of(2015, 12, 03);
        System.out.println(birthday);
        Period duration = Period.between(today, birthday);
        System.out.println(duration.getYears());  // 0
        System.out.println(duration.getMonths()); // 7
        System.out.println(duration.getDays());   // 24
    }

    private static void isLeap() {
        LocalDate year = LocalDate.now();
        System.out.println("this year is leap year? " + year.isLeapYear());
    }

    private static void yearMonth() {
        YearMonth yearMonth = YearMonth.now();
        System.out.println(yearMonth.getYear() + " " + yearMonth.getMonthValue() + " " + yearMonth.lengthOfMonth());
        YearMonth expiry = YearMonth.of(2017, 6);
        System.out.println(expiry);
    }

    private static void zone() {
        ZoneId america = ZoneId.of("America/New_York");
        LocalDateTime dateTime = LocalDateTime.now();
        ZonedDateTime americaDate = ZonedDateTime.of(dateTime, america);
        System.out.println(dateTime);
        System.out.println(americaDate);
    }

    private static void dateCompare() {
        LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.plus(1, ChronoUnit.DAYS);
        LocalDate yesterday = today.minus(1, ChronoUnit.DAYS);
        System.out.println("tomorrow comes after today : " + tomorrow.isAfter(today));
        System.out.println("yesterday is day before today : " + yesterday.isBefore(today));
    }

    private static void clock() {
        Clock clock = Clock.systemUTC();
        Clock clock1 = Clock.systemDefaultZone();
        System.out.println(clock);
        System.out.println(clock1);

        LocalDate date = LocalDate.of(2015, 4, 8);
        LocalDate date1 = LocalDate.now(clock);
        System.out.println(date1);
        System.out.println(date.isBefore(date1));
    }

    private static void dateAdd() {
        LocalDate today = LocalDate.now();
        LocalDate nextWeek = today.plusWeeks(1);
        LocalDate nextTwoWeek = today.plus(2, ChronoUnit.WEEKS);
        System.out.println(today);
        System.out.println(nextWeek);
        System.out.println(nextTwoWeek);

        LocalDate lastYear = today.minusYears(1);
        LocalDate lastYear2 = lastYear.plusDays(1);
        System.out.println(lastYear);
        System.out.println(lastYear2);
    }

    private static void localTime() {
        LocalTime now = LocalTime.now();
        System.out.println(now);

        LocalTime newTime = now.plusHours(2);
        System.out.println("two hours late " + newTime);
    }

    private static void monthDay() {
        LocalDate today = LocalDate.now();
        LocalDate dateOfBirth = LocalDate.of(1991, 12, 03);
        MonthDay birthday = MonthDay.of(dateOfBirth.getMonthValue(), dateOfBirth.getDayOfMonth());
        MonthDay currentDay = MonthDay.from(today);

        System.out.println(birthday);
        System.out.println(currentDay);
        System.out.println(currentDay.equals(birthday));

        LocalDate nextBirthday = LocalDate.of(2015, 12, 03);
        MonthDay next = MonthDay.from(nextBirthday);
        System.out.println(next.equals(birthday));
    }

    private static void dateEqual() {
        LocalDate today = LocalDate.now();
        LocalDate date = LocalDate.of(2015, 4, 9);
        System.out.println(date);
        System.out.println(today.equals(date));
    }

    private static void m1() {
        LocalDate today = LocalDate.now();
        System.out.println("today : " + today);
        int year = today.getYear();
        int month = today.getMonthValue();
        int date = today.getDayOfMonth();
        System.out.println("year " + year + " , month " + month + " , date " + date);
        Month m = today.getMonth();
        DayOfWeek ofWeek = today.getDayOfWeek();
        int ofYear = today.getDayOfYear();
        System.out.println(m);
        System.out.println(ofWeek);
        System.out.println(ofYear);
    }


}
