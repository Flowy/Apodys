@XmlJavaTypeAdapters({
        @XmlJavaTypeAdapter(type = ZonedDateTime.class, value = ZonedDateTimeXmlAdapter.class),
        @XmlJavaTypeAdapter(type = Duration.class, value = DurationXmlAdapter.class),
        @XmlJavaTypeAdapter(type = LocalTime.class, value = LocalTimeXmlAdapter.class),
        @XmlJavaTypeAdapter(type = Period.class, value = PeriodXmlAdapter.class)
})
package com.flowyk.apodys.bussiness.entity;

import com.migesok.jaxb.adapter.javatime.DurationXmlAdapter;
import com.migesok.jaxb.adapter.javatime.LocalTimeXmlAdapter;
import com.migesok.jaxb.adapter.javatime.PeriodXmlAdapter;
import com.migesok.jaxb.adapter.javatime.ZonedDateTimeXmlAdapter;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapters;
import java.time.Duration;
import java.time.LocalTime;
import java.time.Period;
import java.time.ZonedDateTime;