package com.flowyk.apodys.export;

import com.flowyk.apodys.PlanSmien;
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

    /**
     * JAXB constructor
     */
    public ApodysData() {
    }

    public ApodysData(PlanSmien planSmien, List<Zamestnanec> zamestnanci) {
        this.planSmien = planSmien;
        this.zamestnanci = zamestnanci;
    }
}
