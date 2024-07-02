package packages.Objects;

public class ProgramMedic {
    private int idProgramMedic;
    private int idMedic;
    private String zi;
    private String ora;
    private Boolean disponibilitate;

    public ProgramMedic(int idMedic, String zi, String ora, Boolean disponibilitate) {
        this.idMedic = idMedic;
        this.zi = zi;
        this.ora = ora;
        this.disponibilitate = disponibilitate;
    }

    public int getIdProgramMedic() {
        return idProgramMedic;
    }

    public int getIdMedic() {
        return idMedic;
    }

    public String getZi() {
        return zi;
    }

    public String getOra() {
        return ora;
    }

    public Boolean getDisponibilitate() {
        return disponibilitate;
    }

}
