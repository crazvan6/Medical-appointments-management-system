package packages.Objects;

public class Programare {
    private int id;
    private String zi;
    private String ora;
    private int idMedic;
    private int idPacient;

    public Programare(String zi, String ora, int idMedic, int idPacient) {
        this.zi = zi;
        this.ora = ora;
        this.idMedic = idMedic;
        this.idPacient = idPacient;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getZi() {
        return zi;
    }

    public void setZi(String zi) {
        this.zi = zi;
    }

    public String getOra() {
        return ora;
    }

    public void setOra(String ora) {
        this.ora = ora;
    }

    public int getIdMedic() {
        return idMedic;
    }

    public void setIdMedic(int idMedic) {
        this.idMedic = idMedic;
    }

    public int getIdPacient() {
        return idPacient;
    }

    public void setIdPacient(int idPacient) {
        this.idPacient = idPacient;
    }

    @Override
    public String toString() {
        return "\nZi: " + zi +
                "\nOra: " + ora;
    }
}
