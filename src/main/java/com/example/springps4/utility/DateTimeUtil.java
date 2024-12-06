package com.example.springps4.utility;

import java.time.LocalDate;
import java.sql.Date;


public class DateTimeUtil {

    public static LocalDate sqlDateToLocalDate(java.sql.Date sqlDate){
        return sqlDate.toLocalDate();
    }

    public static java.sql.Date localDateToSqlDate(LocalDate localDate){
        return java.sql.Date.valueOf(localDate);
    }


}
