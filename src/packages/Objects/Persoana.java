package packages.Objects;

public abstract class Persoana {
    private int id;

    private String nume;
    private String prenume;
    private int varsta;
    private String CNP;

    public Persoana() {};
    public Persoana(String nume, String prenume, int varsta, String CNP) {
        this.nume = nume;
        this.prenume = prenume;
        this.varsta = varsta;
        this.CNP = CNP;
    }
    public Persoana(int id, String nume, String prenume, int varsta, String CNP) {
        this.nume = nume;
        this.prenume = prenume;
        this.varsta = varsta;
        this.CNP = CNP;
        this.id = id;
    }
    public int getId() {
        return this.id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getPrenume() {
        return prenume;
    }

    public void setPrenume(String prenume) {
        this.prenume = prenume;
    }

    public int getVarsta() {
        return varsta;
    }

    public void setVarsta(int varsta) {
        this.varsta = varsta;
    }

    public String getCNP() {
        return CNP;
    }

    public void setCNP(String CNP) {
        this.CNP = CNP;
    }
}
