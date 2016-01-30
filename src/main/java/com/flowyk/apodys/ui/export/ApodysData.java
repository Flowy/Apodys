package com.flowyk.apodys.ui.export;

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

    @XmlElementWrapper(name = "zamestnanci")
    @XmlElement(name = "zamestnanec", nillable = false)
    private List<Zamestnanec> zamestnanci;

    @XmlElementWrapper(name = "smeny")
    @XmlElement(name = "smena", nillable = false)
    private List<PredlohaSmeny> predlohy;

    /**
     * JAXB constructor
     */
    public ApodysData() {
    }

    public ApodysData(PlanSmien planSmien, List<Zamestnanec> zamestnanci, List<PredlohaSmeny> smeny) {
        this.planSmien = planSmien;
        this.zamestnanci = zamestnanci;
        this.predlohy = smeny;
    }

    public PlanSmien getPlanSmien() {
        return planSmien;
    }

    public List<Zamestnanec> getZamestnanci() {
        return zamestnanci;
    }

    public List<PredlohaSmeny> getSmeny() {
        return predlohy;
    }
}
