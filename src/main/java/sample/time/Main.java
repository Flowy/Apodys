package sample.time;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.ChronoUnit;

public class Main {

    public static void main(String[] args) {
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM);

        System.out.println("Zimny na letny:");
        ZonedDateTime test1 = ZonedDateTime.of(2015, 3, 29, 1, 0, 0, 0, ZoneId.systemDefault());
        System.out.println(test1.format(formatter));
        System.out.println(test1.plus(3L, ChronoUnit.HOURS).plus(Period.ofDays(0)).format(formatter));

        System.out.println();

        System.out.println("Letny na zimny:");
        ZonedDateTime test2 = ZonedDateTime.of(2015, 10, 25, 1, 0, 0, 0, ZoneId.systemDefault());
        System.out.println(test2.format(formatter));
        System.out.println(test2.plus(3L, ChronoUnit.HOURS).format(formatter));

        System.out.println();

        System.out.println("Normalny:");
        ZonedDateTime test3 = ZonedDateTime.of(2015, 11, 20, 1, 0, 0, 0, ZoneId.systemDefault());
        System.out.println(test3.format(formatter));
        System.out.println(test3.plus(3L, ChronoUnit.HOURS).format(formatter));

        System.out.println(ZoneId.getAvailableZoneIds());
    }
}
