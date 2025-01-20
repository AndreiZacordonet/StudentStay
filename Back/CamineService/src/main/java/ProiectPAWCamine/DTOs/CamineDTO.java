package ProiectPAWCamine.DTOs;

public class CamineDTO {
    private String nume;
    private String locatie;
    private Integer etaje;
    private Integer camere;
    private Integer locuri;
    private String listaCamereEtaj;
    private String documentCamin;
    private String caleFolderOglinzi;
    private String descriere;

    public String getListaCamereEtaj() {
        return listaCamereEtaj;
    }

    public void setListaCamereEtaj(String listaCamereEtaj) {
        this.listaCamereEtaj = listaCamereEtaj;
    }

    public String getLocatie() {
        return locatie;
    }

    public void setLocatie(String locatie) {
        this.locatie = locatie;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public Integer getEtaje() {
        return etaje;
    }

    public void setEtaje(Integer etaje) {
        this.etaje = etaje;
    }

    public Integer getCamere() {
        return camere;
    }

    public void setCamere(Integer camere) {
        this.camere = camere;
    }

    public Integer getLocuri() {
        return locuri;
    }

    public void setLocuri(Integer locuri) {
        this.locuri = locuri;
    }

    public String getDocumentCamin() {
        return documentCamin;
    }

    public void setDocumentCamin(String documentCamin) {
        this.documentCamin = documentCamin;
    }

    public String getCaleFolderOglinzi() {
        return caleFolderOglinzi;
    }

    public void setCaleFolderOglinzi(String caleFolderOglinzi) {
        this.caleFolderOglinzi = caleFolderOglinzi;
    }

    public String getDescriere() {
        return descriere;
    }

    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }
}
