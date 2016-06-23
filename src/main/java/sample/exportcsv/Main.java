package sample.exportcsv;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        CSVFormat csvFileFormat = CSVFormat.EXCEL.withDelimiter('\t');

        ;

        File file = null;
        try {
            file = File.createTempFile("csvexample", ".csv");
        } catch (IOException e) {
            e.printStackTrace();
        }
        try(BufferedWriter fileWriter = new BufferedWriter(new FileWriter(file));
            CSVPrinter csvFilePrinter = new CSVPrinter(fileWriter, csvFileFormat)
        ) {
            csvFilePrinter.printRecord(Arrays.asList("Zamestnanec", "1.1.2016", "2.1.2016"));
            csvFilePrinter.printRecord(Arrays.asList("Smurfs", "RRR", "TTT"));
            csvFilePrinter.printRecord(Arrays.asList("Simpsons", "AAA", "BBB"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("File: " + file.getAbsolutePath());
    }
}
