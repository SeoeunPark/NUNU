package com.nunu.NUNU;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;

class Sortlens implements Comparator<Note> {

    @Override
    public int compare(Note o1, Note o2) {
        String str = o1.getLens_end();
        String[] array = str.split("/");
        int cyear = Integer.parseInt(array[0]);
        int cmonth = Integer.parseInt(array[1]);
        int cday = Integer.parseInt(array[2]);
        LocalDate fromDate = LocalDate.now();
        LocalDate toDate = LocalDate.of(cyear, cmonth, cday);
        long subtract = ChronoUnit.DAYS.between(fromDate, toDate);
        Integer len1 = (int)subtract;

        String str2 = o2.getLens_end();
        String[] array2 = str2.split("/");
        int cyear2 = Integer.parseInt(array2[0]);
        int cmonth2 = Integer.parseInt(array2[1]);
        int cday2 = Integer.parseInt(array2[2]);
        LocalDate fromDate2 = LocalDate.now();
        LocalDate toDate2 = LocalDate.of(cyear2, cmonth2, cday2);
        long subtract2 = ChronoUnit.DAYS.between(fromDate2, toDate2);
        Integer len2 = (int)subtract2;

        return len1.compareTo(len2);
    }
}
