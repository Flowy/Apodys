package com.flowyk.apodys.ui.config.event;

import com.flowyk.apodys.bussiness.entity.XmlDataWrapper;

public class XmlLoaded {

    private XmlDataWrapper xmlDataWrapper;

    public XmlLoaded(XmlDataWrapper xmlDataWrapper) {
        this.xmlDataWrapper = xmlDataWrapper;
    }

    public XmlDataWrapper getXmlDataWrapper() {
        return xmlDataWrapper;
    }
}
