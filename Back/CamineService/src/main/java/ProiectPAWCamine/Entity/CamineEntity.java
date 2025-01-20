package ProiectPAWCamine.Entity;


import jakarta.persistence.*;

@Entity
@Table(name = "camine")
public class CamineEntity  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,unique = true)
    private String nume;
    @Column(nullable = false)
    private String locatie;
    @Column(nullable = false)
    private int etaje;
    @Column(nullable = false)
    private int camere;
    @Column(nullable = false)
    private int locuri;
    @Column(nullable = false)
    private String listaCamereEtaj;
    @Column(nullable = false)
    private String documentCamin; // Path sau link
    @Column(nullable = false)
    private String caleFolderOglinzi; // Path către folderul de oglinzi
    private String descriere;
    public CamineEntity(){}
    public CamineEntity(String nume, String locatie, int etaje, int camere, int locuri, String listaCamereEtaj, String documentCamin, String caleFolderOglinzi, String descriere) {
        this.nume = nume;
        this.locatie = locatie;
        this.etaje = etaje;
        this.camere = camere;
        this.locuri = locuri;
        this.listaCamereEtaj = listaCamereEtaj;
        this.documentCamin = documentCamin;
        this.caleFolderOglinzi = caleFolderOglinzi;
        this.descriere = descriere;
    }

    public int getLocuri() {
        return locuri;
    }

    public void setLocuri(int locuri) {
        this.locuri = locuri;
    }
    public Long getId() {
        return id;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getLocatie() {
        return locatie;
    }

    public void setLocatie(String locatie) {
        this.locatie = locatie;
    }

    public int getEtaje() {
        return etaje;
    }

    public void setEtaje(int etaje) {
        this.etaje = etaje;
    }

    public int getCamere() {
        return camere;
    }

    public void setCamere(int camere) {
        this.camere = camere;
    }

    public String getListaCamereEtaj() {
        return listaCamereEtaj;
    }

    public void setListaCamereEtaj(String listaCamereEtaj) {
        this.listaCamereEtaj = listaCamereEtaj;
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
