package com.example.application.views.myClasses;

import com.example.application.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("MyClassView")
@Route(value = "myClasses", layout = MainLayout.class)
public class myClassView extends VerticalLayout {
    public myClassView(){
        Paragraph test = new Paragraph("Testiong");
        H2 header = new H2("H2 Header");
        add(test);
        add(header);
    }
}
