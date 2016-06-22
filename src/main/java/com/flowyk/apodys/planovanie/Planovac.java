package com.flowyk.apodys.planovanie;

import com.flowyk.apodys.bussiness.entity.EmployeeShifts;

import java.time.LocalDate;
import java.util.List;

public interface Planovac {

    void naplanuj(List<EmployeeShifts> employeeShifts, LocalDate zaciatok, LocalDate koniec);
}
