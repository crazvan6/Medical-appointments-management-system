package packages.Services;

import packages.Objects.Insotitor;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ServiceInsotitor {

    private static ServiceInsotitor instance;

    private ServiceInsotitor() {}

    public static ServiceInsotitor getInstance() {
        if (instance == null) {
            synchronized (ServiceInsotitor.class) {
                if (instance == null) {
                    instance = new ServiceInsotitor();
                }
            }
        }
        return instance;
    }

    public void insertInsotitor(DatabaseManager db, Insotitor insotitor) {
        String queryInsertInsotitor = "INSERT INTO Insotitor(id_insotitor, id_pacient_minor, nume, prenume, varsta, cnp, semnatura) VALUES(?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = db.getConnection().prepareStatement(queryInsertInsotitor)) {
            stmt.setInt(1, insotitor.getId());
            stmt.setInt(2, insotitor.getIdPacientMinor());
            stmt.setString(3, insotitor.getNume());
            stmt.setString(4, insotitor.getPrenume());
            stmt.setInt(5, insotitor.getVarsta());
            stmt.setString(6, insotitor.getCNP());
            stmt.setString(7, insotitor.getSemnatura());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
