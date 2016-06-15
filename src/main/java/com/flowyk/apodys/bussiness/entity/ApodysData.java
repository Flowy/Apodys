package com.flowyk.apodys.bussiness.entity;

import com.flowyk.apodys.PlanSmien;
import com.flowyk.apodys.PredlohaSmeny;
import com.flowyk.apodys.Zamestnanec;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ApodysData {

    @XmlElement(required = true)
    private PlanSmien planSmien;

    @XmlElementWrapper(name = "smeny", required = true)
    @XmlElement(name = "smena")
    private List<PredlohaSmeny> predlohy;

    /**
     * JAXB constructor
     */
    public ApodysData() {
    }

    public ApodysData(PlanSmien planSmien, List<PredlohaSmeny> smeny) {
        this.planSmien = planSmien;
        this.predlohy = smeny;
    }

    public PlanSmien getPlanSmien() {
        return planSmien;
    }

    public List<PredlohaSmeny> getSmeny() {
        return predlohy;
    }
}
