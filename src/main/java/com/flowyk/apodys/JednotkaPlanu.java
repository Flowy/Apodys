package com.flowyk.apodys;

import java.time.ZonedDateTime;

public interface JednotkaPlanu {
    TypJednotkyPlanu typ();
    ZonedDateTime zaciatok();
    ZonedDateTime koniec();
    Zamestnanec vykonavatel();
}
