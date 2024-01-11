package com.example.application.views.scanner;

public class assignment {
    String day;
    String description;
    String month;
    Integer dayAsInt;
    int monthAsInt;

    public assignment(){
        day = "";
        description = "";
        month = "";
        dayAsInt = -1;
        monthAsInt = -1;
    }

    public String getDescription() {
        return description;
    }

    public int getMonthAsInt() {
        return monthAsInt;
    }

    public Integer getDayAsInt() {
        return dayAsInt;
    }
}
