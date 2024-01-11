package com.example.application.views.creator;

import com.example.application.views.MainLayout;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.component.textfield.TextField;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import java.io.*;


class assignment {
    String date = "";
    String desc = "";
};


@PageTitle("Creator")
@Route(value = "creator", layout = MainLayout.class)
public class CreatorView extends VerticalLayout {

    private final File uploadFolder = new File("syllabusPractice.pdf");
    private String professor, subject, schedule, location;
    Paragraph errorMsg = new Paragraph("ERROR: One or more of the fields is empty");
    int i = 0;
    static assignment[] listOfAssignments = new assignment[100];

    private InputStream getStream(File file) {
        FileInputStream stream = null;
        try {
            stream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return stream;
    }

    private void showDownloadLink(){
        StreamResource streamResource = new StreamResource("syllabusPractice.pdf", () -> getStream(uploadFolder));
        Anchor link = new Anchor(streamResource, String.format("%s (%d KB)", "syllabusPractice.pdf",
                (int) 5000 / 1024));
        link.getElement().setAttribute("download", true);
        add(link);
    }


    private void makeFile() throws IOException{
        remove(errorMsg);

        if(professor.equals("") || subject.equals("") || schedule.equals("") || location.equals("")){
            add(errorMsg);
        }else {
            PDDocument document = new PDDocument();

            PDPage page = new PDPage();

            document.addPage(page);

            PDPageContentStream contentStream = new PDPageContentStream(document, page);

            contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);

            float x = 50;
            float y = 750;
            float spacing = 15;

            contentStream.beginText();
            contentStream.newLineAtOffset(x, y);
            contentStream.showText(professor);
            contentStream.endText();

            y = y - spacing;

            contentStream.beginText();
            contentStream.newLineAtOffset(x, y);
            contentStream.showText(subject);
            contentStream.endText();

            y = y - spacing;

            contentStream.beginText();
            contentStream.newLineAtOffset(x, y);
            contentStream.showText(schedule);
            contentStream.endText();

            y = y - spacing;

            contentStream.beginText();
            contentStream.newLineAtOffset(x, y);
            contentStream.showText(location);
            contentStream.endText();

            y = y - spacing * 2;

            contentStream.beginText();
            contentStream.newLineAtOffset(x, y);
            contentStream.showText("Schedule");
            contentStream.endText();

            for (int k = 0; k < i; k++) {
                y = y - spacing;
                contentStream.beginText();
                contentStream.newLineAtOffset(x, y);
                contentStream.showText(listOfAssignments[k].date + " " + listOfAssignments[k].desc);
                contentStream.endText();
            }


            contentStream.close();
            document.save(new File("syllabusPractice.pdf"));

            document.close();

            showDownloadLink();
        }
    }

    public CreatorView() throws IOException {
        for (int i = 0; i < 100; i++) {
            listOfAssignments[i] = new assignment();
        }

        TextField profName = new TextField();
        profName.setLabel("Professor's Name:");
        profName.setPlaceholder("John Doe");

        TextField className = new TextField();
        className.setLabel("Class Name:");
        className.setPlaceholder("ENG 2311");

        TextField classTime = new TextField();
        classTime.setLabel("Schedule");
        classTime.setPlaceholder("MWF 2:00 - 3:00)");

        TextField classLocatn = new TextField();
        classLocatn.setLabel("Where is your class held?");
        classLocatn.setPlaceholder("Science Building");

        Button addAssigment = new Button("Add Assignment");

        Dialog dialog = new Dialog();
        dialog.setHeaderTitle("New Assignment");
        VerticalLayout dialogLayout = new VerticalLayout();
        TextField assign = new TextField();
        assign.setLabel("Assignment: ");
        assign.setPlaceholder("Test");
        TextField dueDate = new TextField();
        dueDate.setLabel("Due Date: ");
        dueDate.setPlaceholder("January 1");
        dialogLayout.add(assign);
        dialogLayout.add(dueDate);
        dialog.add(dialogLayout);

        Button addToSyll = new Button("Add to Syllabus");
        addToSyll.addClickListener(event -> {
            System.out.println("Adding");
            listOfAssignments[i].date = dueDate.getValue();
            listOfAssignments[i].desc = assign.getValue();
            i++;
            dialog.close();
        });
        Button cancelButton = new Button("Cancel", e -> dialog.close());
        dialog.getFooter().add(cancelButton);
        dialog.getFooter().add(addToSyll);
        addAssigment.addClickListener( event -> {
            dialog.open();

        });

        Button generate = new Button("Generate Syllabus");

        generate.addClickListener(buttonClickEvent -> {
            professor = profName.getValue();
            subject = className.getValue();
            schedule = classTime.getValue();
            location = classLocatn.getValue();
            try {
                makeFile();
            }catch (IOException e){
                System.out.println("ERROR IN MAKE FILE");
            }

        });
        add(profName);
        add(className);
        add(classTime);
        add(classLocatn);
        add(addAssigment);
        add(generate);

    }

}
