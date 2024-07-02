package packages.Objects;

public class Medic extends Persoana {
    private int idClinica;

    public Medic(int idClinica, String nume, String prenume, int varsta, String CNP) {
        super(nume, prenume, varsta, CNP);
        this.idClinica = idClinica;
    }
    public Medic(int idMedic, int idClinica, String nume, String prenume, int varsta, String CNP) {
        super(nume, prenume, varsta, CNP);
        this.idClinica = idClinica;
        this.setId(idMedic);
    }
    public int getIdClinica() {
        return idClinica;
    }

    public void setIdClinica(int idClinica) {
        this.idClinica = idClinica;
    }

    @Override
    public String toString() {
        return "\nID: " + getId() +
                "\nID Clinica: " + idClinica +
                "\nNume: " + getNume() +
                "\nPrenume: " + getPrenume();
    }
}
