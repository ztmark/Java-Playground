package com.mark.java8;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Locale;

/**
 * Author: Mark
 * Date  : 16/3/26
 */
public class DateAndTime {

    public static void main(String[] args) {
//        clock();


//        System.out.println(ZoneId.getAvailableZoneIds());
        System.out.println(ZoneId.systemDefault());
        ZoneId zone1 = ZoneId.of("Asia/Shanghai");
        ZoneId zone2 = ZoneId.of("America/Los_Angeles");
        System.out.println(zone1.toString());
        System.out.println(zone2.getRules());

        LocalTime now1 = LocalTime.now(zone1);
        LocalTime now2 = LocalTime.now(zone2);

        System.out.println(now1.isBefore(now2));

        long hoursBetween = ChronoUnit.HOURS.between(now1, now2);
        long minutesBetween = ChronoUnit.MINUTES.between(now1, now2);

        System.out.println(hoursBetween);
        System.out.println(minutesBetween);

        System.out.println("=====================");

        LocalTime late = LocalTime.of(23, 59, 59);
        System.out.println(late);       // 23:59:59

        DateTimeFormatter formatter = DateTimeFormatter
                        .ofLocalizedTime(FormatStyle.SHORT)
                        .withLocale(Locale.CHINESE);

        LocalTime leetTime = LocalTime.parse("下午1:37", formatter);
        System.out.println(leetTime);

        LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.plus(1, ChronoUnit.DAYS);
        LocalDate yesterday = tomorrow.minusDays(2);
        System.out.println(today);
        System.out.println(tomorrow);
        System.out.println(yesterday);

        LocalDate aDay = LocalDate.of(2016, 2, 29);
        System.out.println(aDay);
        DayOfWeek dayOfWeek = aDay.getDayOfWeek();
        System.out.println(dayOfWeek);

        LocalDate date = LocalDate.parse("2016-01-01");
        System.out.println(date);
        date = LocalDate.parse("2016/01/02", DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        System.out.println(date);

        LocalDateTime sylvester = LocalDateTime.of(2014, Month.DECEMBER, 31, 23, 59, 59);

        DayOfWeek dWeek = sylvester.getDayOfWeek();
        System.out.println(dWeek);

        Month month = sylvester.getMonth();
        System.out.println(month);

        long minuteOfDay = sylvester.getLong(ChronoField.MINUTE_OF_DAY);
        System.out.println(minuteOfDay);

    }

    private static void clock() {
        Clock clock = Clock.systemDefaultZone();
        long millis = clock.millis();

        Instant instant = clock.instant();
        System.out.println(instant);
        Date legacyDate = Date.from(instant);
        System.out.println(legacyDate);
    }


}
