package packages.Objects;

public class Insotitor extends Persoana {
    private String semnatura;
    private int idPacientMinor;
    public Insotitor(int idPacientMinor, String nume, String prenume, int varsta, String CNP, String semnatura) {
        super(nume, prenume, varsta, CNP);
        this.semnatura = semnatura;
        this.idPacientMinor = idPacientMinor;
    }

    public int getIdPacientMinor() {
        return idPacientMinor;
    }

    public String getSemnatura() {
        return semnatura;
    }

    public void setSemnatura(String semnatura) {
        this.semnatura = semnatura;
    }
}
