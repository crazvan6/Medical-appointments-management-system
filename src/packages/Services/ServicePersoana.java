package packages.Services;

import packages.Objects.Persoana;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class ServicePersoana {
    private static ServicePersoana instance;
    private Scanner scanner = new Scanner(System.in);

    private ServicePersoana() {}

    public static ServicePersoana getInstance() {
        if (instance == null) {
            synchronized (ServicePersoana.class) {
                if (instance == null) {
                    instance = new ServicePersoana();
                }
            }
        }
        return instance;
    }

    public int getIdPersoana(DatabaseManager db, String cnp) {
        String queryGetidPersoana = "SELECT id FROM Persoana WHERE cnp = ?";
        try (PreparedStatement stmt = db.getConnection().prepareStatement(queryGetidPersoana)) {
            stmt.setString(1, cnp);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id");
                } else {
                    return 0;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void insertPersoana(DatabaseManager db, Persoana persoana) {
        String queryInsertPersoana = "INSERT INTO Persoana(nume, prenume, varsta, cnp) VALUES(?, ?, ?, ?)";
        try (PreparedStatement stmt = db.getConnection().prepareStatement(queryInsertPersoana)) {
            stmt.setString(1, persoana.getNume());
            stmt.setString(2, persoana.getPrenume());
            stmt.setInt(3, persoana.getVarsta());
            stmt.setString(4, persoana.getCNP());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void stergePersoana(DatabaseManager db, int idPersoana) {
        String queryDeletePersoana = "DELETE FROM Persoana WHERE id = ?";

        try (PreparedStatement stmt = db.getConnection().prepareStatement(queryDeletePersoana)) {
            stmt.setInt(1, idPersoana);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
