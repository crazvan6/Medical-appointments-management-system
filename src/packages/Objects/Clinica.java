package packages.Objects;

public class Clinica {
    private int id;
    private String adresa;
    private String numarTelefon;
    private String denumire;

    public Clinica(int id, String adresa, String numarTelefon, String denumire) {
        this.id = id;
        this.adresa = adresa;
        this.denumire = denumire;
        this.numarTelefon = numarTelefon;
    }
    public Clinica(String adresa, String numarTelefon, String denumire) {
        this.adresa = adresa;
        this.denumire = denumire;
        this.numarTelefon = numarTelefon;
    }
    public String getDenumire() {
        return denumire;
    }
    public void setDenumire(String denumire) {
        this.denumire = denumire;
    }

    public int getId() {
        return id;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public String getNumarTelefon() {
        return numarTelefon;
    }

    public void setNumarTelefon(String numarTelefon) {
        this.numarTelefon = numarTelefon;
    }

    @Override
    public String toString() {
        return  "\nID: " + id +
                "\nDenumire: " + denumire +
                "\nAdresa: " + adresa +
                "\nTelefon: " + numarTelefon + "\n";
    }

}
