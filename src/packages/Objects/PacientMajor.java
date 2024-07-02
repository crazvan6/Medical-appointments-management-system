package packages.Objects;

public class PacientMajor extends Pacient {
    private Boolean asiguratCNAS;
    //private int id;

    public PacientMajor(int id, String nume, String prenume, int varsta, String CNP, Boolean asiguratCNAS) {
        super(id, nume, prenume, varsta, CNP);
        this.asiguratCNAS = asiguratCNAS;
    }
    public PacientMajor(String nume, String prenume, int varsta, String CNP, Boolean asiguratCNAS) {
        super(nume, prenume, varsta, CNP);
        this.asiguratCNAS = asiguratCNAS;
    }
    public Boolean getAsiguratCNAS() {
        return asiguratCNAS;
    }

    public void setAsiguratCNAS(Boolean asiguratCNAS) {
        this.asiguratCNAS = asiguratCNAS;
    }
}
