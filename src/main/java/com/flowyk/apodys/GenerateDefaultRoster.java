package com.flowyk.apodys;

import com.flowyk.apodys.bussiness.controller.Context;
import com.flowyk.apodys.bussiness.controller.Export;
import com.flowyk.apodys.bussiness.entity.PredlohaSmeny;
import com.flowyk.apodys.bussiness.entity.PredlohaSmenyImpl;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.time.Duration;
import java.time.LocalTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

public class GenerateDefaultRoster {
    public static void main(String[] args) throws URISyntaxException, IOException {
        Context context = new Context(new Export());
        context.getShiftTemplates().addAll(generateTemplates());
        File file = File.createTempFile("default_roster", ".xml");

        context.saveTo(file);

        System.out.println("File saved into: " + file.getAbsolutePath());

    }

    private static List<PredlohaSmenyImpl> generateTemplates() {
        List<PredlohaSmenyImpl> result = new ArrayList<>();
        result.add(new PredlohaSmenyImpl("R2P", LocalTime.of(6, 0), LocalTime.of(18, 0), Duration.ofHours(12L)));
        result.add(new PredlohaSmenyImpl("P1C", LocalTime.of(9, 0), LocalTime.of(21, 0), Duration.ofHours(12L)));
        result.add(new PredlohaSmenyImpl("O7,5", LocalTime.of(14, 0), LocalTime.of(22, 0), Duration.ofHours(8L)));
        result.add(new PredlohaSmenyImpl("N2P", LocalTime.of(18, 0), LocalTime.of(6, 0), Period.ofDays(1), Duration.ofHours(12L)));
        return result;
    }
}
