package packages.Objects;

public class Pacient extends Persoana {
    //private ArrayList<Programare> programari;
    //private Set<Medic> medici;
    //private int COD_CLIENT;
    public Pacient() {};


    public Pacient(int id, String nume, String prenume, int varsta, String CNP) {
        super(id, nume, prenume, varsta, CNP);
    }
    public Pacient(String nume, String prenume, int varsta, String CNP) {
        super(nume, prenume, varsta, CNP);
    }
    @Override
    public String toString() {
        return "\nNume: " + getNume() +
                "\nPrenume: " + getPrenume() +
                "\nVarsta: " + getVarsta() +
                "\nCNP: " + getCNP() + "\n";
    }

}
