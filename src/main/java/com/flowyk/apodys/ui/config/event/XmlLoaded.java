package com.flowyk.apodys.ui.config.event;

import com.flowyk.apodys.bussiness.entity.XmlExport;

public class XmlLoaded {

    private XmlExport xmlDataWrapper;

    public XmlLoaded(XmlExport xmlDataWrapper) {
        this.xmlDataWrapper = xmlDataWrapper;
    }

    public XmlExport getXmlDataWrapper() {
        return xmlDataWrapper;
    }
}
