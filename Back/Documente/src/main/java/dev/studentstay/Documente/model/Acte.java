package dev.studentstay.Documente.model;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Acte")
public enum Acte {
    BULETIN,
    CERTIFICAT_NASTERE,
    CERTIFICAT_DIVORT,
    CERTIFICAT_DECES,
    CERTIFICAT_HANDICAP,
    DIPLOMA_CONCURS,
    DECLARATIE_VENIT,
    DECLARATIE_TEREN_AGRICOL,
    ADEVERINTA_SALARIALA,
    ADEVERINTA_PENSIE,
    ALTE_ACTE
}
