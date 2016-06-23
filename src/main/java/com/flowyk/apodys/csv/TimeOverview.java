package com.flowyk.apodys.csv;

import com.flowyk.apodys.bussiness.entity.EmployeeShifts;
import com.flowyk.apodys.bussiness.entity.Shift;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import javax.inject.Singleton;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;

@Singleton
public class TimeOverview {

    public void export(File file, List<EmployeeShifts> shifts, LocalDate startDate, LocalDate endDate) {
        CSVFormat format = CSVFormat.EXCEL
                .withDelimiter('\t')
                .withHeader(generateHeaders(shifts, startDate, endDate));

        try (FileWriter fileWriter = new FileWriter(file);
             BufferedWriter writer = new BufferedWriter(fileWriter);
             CSVPrinter printer = new CSVPrinter(writer, format)) {
            for (EmployeeShifts shift : shifts) {
                printer.printRecord((Object[]) generateRecord(shift, startDate, endDate));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Object[] generateRecord(EmployeeShifts employeeShifts, LocalDate startDate, LocalDate endDate) {
        int days = calculateDiff(startDate, endDate);
        String[] result = new String[days + 1];

        result[0] = employeeShifts.getEmployee().getName();

        for (int i = 0; i < days; i++) {
            Shift current = employeeShifts.getShift(startDate.plusDays(i));
            if (current != null) {
                result[i+1] = current.getPredloha().getNazov();
            }
        }
        return result;
    }

    private String[] generateHeaders(List<EmployeeShifts> shifts, LocalDate startDate, LocalDate endDate) {
        int days = calculateDiff(startDate, endDate);

        String[] result = new String[days + 1];
        for (int i = 0; i < days; i++) {
            LocalDate currentDate = startDate.plusDays(i);
            result[i+1] = currentDate.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM));
        }
        return result;
    }

    private int calculateDiff(LocalDate startDate, LocalDate endDate) {
        Period dif = Period.between(startDate, endDate);
        return dif.getDays();
    }
}
