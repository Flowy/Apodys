package com.flowyk.apodys.bussiness.entity;

import com.flowyk.apodys.PredlohaSmeny;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class XmlDataWrapper {

    @XmlElement(required = true)
    private PlanSmien planSmien;

    @XmlElementWrapper(name = "smeny", required = true)
    @XmlElement(name = "smena")
    private List<PredlohaSmeny> predlohy;

    /**
     * JAXB constructor
     */
    public XmlDataWrapper() {
    }

    public XmlDataWrapper(PlanSmien planSmien, List<PredlohaSmeny> smeny) {
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
