package com.example.application.views.scanner;

import java.util.Formatter;
import java.io.*;
//import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import java.util.Scanner;

public class file {

    public static int numOfAssignments = 0;
    public static assignment[] listOfAssignments = new assignment[100];

    public static void processFile(InputStream is) throws IOException {

        byte[] ba1 = new byte[1024];
        int leng;
        try {
            FileOutputStream fout = new FileOutputStream("download.pdf");
            while ((leng = is.read(ba1)) != -1) {
                fout.write(ba1, 0, leng);
            }
        } catch (FileNotFoundException e) {
            System.out.println("ERROR: " + e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        File file = new File("download.pdf");
        PDDocument document = PDDocument.load(file);
        PDFTextStripper pdfTextStripper = new PDFTextStripper();
        pdfTextStripper.setStartPage(1);
        pdfTextStripper.setEndPage(5);
        String text = pdfTextStripper.getText(document);
        document.close();
        Formatter x;
        File file1 = new File("text1.txt");
        x = new Formatter("text1.txt");
        x.format(text);
        x.close();
    }

    public static void getInfoPDF() throws FileNotFoundException {
        //initializing all class assignments
        for (int i = 0; i < 100; i++) {
            listOfAssignments[i] = new assignment();
        }
        //declaring all necessary variables
        String line;

        //attempting to open the file
        File syll = new File("text1.txt");

        //checking if file is open and readable
        try {
            if (!syll.canRead()) {
                //throws exeption if file is not found
                throw new FileNotFoundException();
            }
        } catch (FileNotFoundException a) {
            System.out.println("File Not Found: " + a);
        }

        //declaring and initializing scanner, named sc
        Scanner sc = new Scanner(syll);

        //skipping everything in file up to the schedule
        while (!(line = sc.nextLine()).equals("Schedule")) {
            continue;
        }

        //getting all the date for each assigment, and storing it in respective spot in listOfAssignment array
        while (sc.hasNextLine()) {
            //System.out.println("inside sc.hasNextLine() loop");
            //sets the delimeter as the space character so that it grabs just the words
            sc.useDelimiter(" ");

            //takes the data from file, will be the month assigment is due
            line = sc.next();
            //stores it in correct index of listOfAssignment array
            listOfAssignments[numOfAssignments].month = line;

            //takes next data from the file, will be the day assignment is due
            line = sc.next();
            listOfAssignments[numOfAssignments].day = line;

            //sets the delimiter back to the newline character because we want all the data up to the newline for the description
            //sc.useDelimiter("\n");

            //gets the description of assigment from the file
            line = sc.nextLine();
            //stores description in listOfAssignment array
            listOfAssignments[numOfAssignments].description = line;

            //increments numberOfAssignments
            numOfAssignments++;
            //System.out.println("incrementing assingments");
        }

        sc.close();
    }

    public static int convertToInt(assignment ass) {
        return Integer.parseInt(ass.day);
    }

    public static void sort() {
        //sort Day-------------------------------------------------------------------
        for (int i = 0; i < numOfAssignments; i++) {
            listOfAssignments[i].dayAsInt = convertToInt(listOfAssignments[i]);
        }

        int n = numOfAssignments;

        for (int i = 0; i < n - 1; i++) {
            // Find the minimum element in unsorted array
            int min_idx = i;
            for (int j = i + 1; j < n; j++) {
                if (listOfAssignments[j].dayAsInt < listOfAssignments[min_idx].dayAsInt) {
                    min_idx = j;
                }
            }
            // Swap the found minimum element with the first
            // element
            assignment temp = listOfAssignments[min_idx];
            listOfAssignments[min_idx] = listOfAssignments[i];
            listOfAssignments[i] = temp;
        }

        //format month----------------------------------------------------------------
        for (int i = 0; i < numOfAssignments; i++) {
            if (listOfAssignments[i].month.equals("January")) {
                listOfAssignments[i].monthAsInt = 1;
            } else if (listOfAssignments[i].month.equals("February")) {
                listOfAssignments[i].monthAsInt = 2;
            } else if (listOfAssignments[i].month.equals("March")) {
                listOfAssignments[i].monthAsInt = 3;
            } else if (listOfAssignments[i].month.equals("April")) {
                listOfAssignments[i].monthAsInt = 4;
            } else if (listOfAssignments[i].month.equals("May")) {
                listOfAssignments[i].monthAsInt = 5;
            } else if (listOfAssignments[i].month.equals("June")) {
                listOfAssignments[i].monthAsInt = 6;
            } else if (listOfAssignments[i].month.equals("July")) {
                listOfAssignments[i].monthAsInt = 7;
            } else if (listOfAssignments[i].month.equals("August")) {
                listOfAssignments[i].monthAsInt = 8;
            } else if (listOfAssignments[i].month.equals("September")) {
                listOfAssignments[i].monthAsInt = 9;
            } else if (listOfAssignments[i].month.equals("October")) {
                listOfAssignments[i].monthAsInt = 10;
            } else if (listOfAssignments[i].month.equals("November")) {
                listOfAssignments[i].monthAsInt = 11;
            } else if (listOfAssignments[i].month.equals("December")) {
                listOfAssignments[i].monthAsInt = 12;
            } else {
                listOfAssignments[i].monthAsInt = -1;
            }
        }

        //sort by month---------------------------------------------------------------
        for (int i = 0; i < n; i++) {
            listOfAssignments[i].dayAsInt = convertToInt(listOfAssignments[i]);
        }
        int i, j;
        assignment temp;
        boolean swapped;
        for (i = 0; i < n - 1; i++) {
            swapped = false;
            for (j = 0; j < n - i - 1; j++) {
                if (listOfAssignments[j].monthAsInt > listOfAssignments[j + 1].monthAsInt) {

                    // Swap arr[j] and arr[j+1]
                    temp = listOfAssignments[j];
                    listOfAssignments[j] = listOfAssignments[j + 1];
                    listOfAssignments[j + 1] = temp;
                    swapped = true;
                }
            }

            // If no two elements were
            // swapped by inner loop, then break
            if (swapped == false)
                break;
        }
        //DONE------------------------------------------------------------------------
        System.out.println("File is sorted");
    }

    public static int getNumOfAssignments() {
        return numOfAssignments;
    }

    public static assignment getAssignment(int index) {
        return listOfAssignments[index];
    }

    public static assignment[] getListOfAssignments() {
        return listOfAssignments;
    }
}