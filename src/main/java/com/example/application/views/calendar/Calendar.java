package com.example.application.views.calendar;

import com.example.application.views.MainLayout;
import com.example.application.views.scanner.assignment;
import com.example.application.views.scanner.file;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.vaadin.stefan.fullcalendar.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.Locale;


@PageTitle("Calendar")
@Route(value = "calendar", layout = MainLayout.class)
public class Calendar extends VerticalLayout {

    private final FullCalendar calendar;

    private assignment[] listOfAssignments = new assignment[100];

    public Calendar() {

        for (int i = 0; i < 100; i++) {
            listOfAssignments[i] = new assignment();
        }
        H2 monthDisplayed = new H2();
        listOfAssignments = file.getListOfAssignments();
        int numOfAssignments = file.getNumOfAssignments();
        calendar = FullCalendarBuilder.create().withEntryLimit(3).build();

        if(numOfAssignments == 0){
            add(new H2("Error: Assignments have not been loaded from file"));
        }else {
            calendar.setLocale(Locale.ENGLISH);
            calendar.setFirstDay(DayOfWeek.SUNDAY);
            setFlexGrow(2, calendar);
            setHorizontalComponentAlignment(Alignment.STRETCH, calendar);
            setSizeFull();

            for (int i = 0; i < numOfAssignments; i++) {
                Entry entry1 = new Entry();
                entry1.setTitle(listOfAssignments[i].getDescription());
                entry1.setColor("#ff3333");

                entry1.setStart(LocalDate.of(2023, listOfAssignments[i].getMonthAsInt(),
                        listOfAssignments[i].getDayAsInt()).atTime(10, 0));
                entry1.setEnd(entry1.getStart().plusHours(2));
                entry1.isAllDay();

                calendar.getEntryProvider().asInMemory().addEntries(entry1);
            }

            calendar.gotoDate(LocalDate.of(2023, listOfAssignments[0].getMonthAsInt(), listOfAssignments[0].getDayAsInt()));

            Button nextMonth = new Button("Next Month");
            nextMonth.addClickListener(event -> {
                calendar.next();
            });

            Button backMonth = new Button("Back Month");
            backMonth.addClickListener(event -> {
                calendar.previous();
            });

            calendar.setEditable(false);
            calendar.setEntryDurationEditable(false);
            calendar.setEntryStartEditable(false);
            calendar.allowDatesRenderEventOnOptionChange(false);

            HorizontalLayout buttons = new HorizontalLayout();

            buttons.setPadding(true);
            buttons.setDefaultVerticalComponentAlignment(Alignment.END);
            //buttons.setAlignSelf(Alignment.CENTER);
            buttons.setJustifyContentMode(JustifyContentMode.EVENLY);
            //buttons.setDefaultVerticalComponentAlignment(Alignment.CENTER);
            buttons.getStyle().set("text-align", "center");

            calendar.setWeekNumbersVisible(false);
            calendar.setTimeslotsSelectable(true);

            HorizontalLayout views = new HorizontalLayout();

            Button month = new Button("Month View");
            month.addClickListener(event -> {
                calendar.changeView(CalendarViewImpl.DAY_GRID_MONTH);
            });

            Button list = new Button("List View");
            list.addClickListener(event -> {
                calendar.changeView(CalendarViewImpl.LIST_YEAR);
            });

            Button week = new Button("Week");
            week.addClickListener(event -> {
                calendar.changeView(CalendarViewImpl.LIST_WEEK);
            });

            views.add(month, week, list);


            buttons.add(backMonth);
            buttons.add(nextMonth);
            calendar.addDatesRenderedListener(event -> {
                LocalDate date = event.getIntervalStart();
                Month currMonth = date.getMonth();
                monthDisplayed.removeAll();
                monthDisplayed.add(currMonth.toString());
                buttons.remove(monthDisplayed, backMonth, nextMonth);
                buttons.add(backMonth, monthDisplayed, nextMonth);
                buttons.setJustifyContentMode(JustifyContentMode.CENTER);
                //buttons.setDefaultHorizontalComponentAlignment(Alignment.CENTER);
                buttons.getStyle().set("text-align", "center");
            });

            add(views);

            add(buttons);
            add(calendar);
        }

    }

}
