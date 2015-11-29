package com.flowyk.apodys;

import java.time.ZonedDateTime;

public interface PolozkaPlanu {
    TypPolozkyPlanu typ();
    ZonedDateTime zaciatok();
    ZonedDateTime koniec();
    Zamestnanec vykonavatel();
}
