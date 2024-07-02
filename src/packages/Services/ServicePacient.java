package packages.Services;

import packages.Objects.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import static packages.Services.MainService.*;

public class ServicePacient {

    private static ServicePacient instance;
    private Scanner scanner = new Scanner(System.in);

    private ServicePacient() {}

    public static ServicePacient getInstance() {
        if (instance == null) {
            synchronized (ServicePacient.class) {
                if (instance == null) {
                    instance = new ServicePacient();
                }
            }
        }
        return instance;
    }

    public String checkIdPacient(DatabaseManager db, int idPacient) {
        String queryGetPacient = "SELECT cnp FROM Pacient WHERE id_pacient = ?";
        try (PreparedStatement stmt = db.getConnection().prepareStatement(queryGetPacient)) {
            stmt.setInt(1, idPacient);
            ResultSet rs = stmt.executeQuery(); {
                if (rs.next()) {
                    return rs.getString("cnp");
                } else {
                    return "";
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void modificaPacient(DatabaseManager db, Pacient pacient) {
        String queryEditPacient = "UPDATE Pacient SET nume = ?, prenume = ? WHERE id_pacient = ?";

        try (PreparedStatement stmt = db.getConnection().prepareStatement(queryEditPacient)) {
            stmt.setString(1, pacient.getNume());
            stmt.setString(2, pacient.getPrenume());
            stmt.setInt(3, pacient.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void modificaPacientMinor(DatabaseManager db, Pacient pacient) {
        String queryEditPacient = "UPDATE PacientMinor SET nume = ?, prenume = ? WHERE id_pacient_minor = ?";

        try (PreparedStatement stmt = db.getConnection().prepareStatement(queryEditPacient)) {
            stmt.setString(1, pacient.getNume());
            stmt.setString(2, pacient.getPrenume());
            stmt.setInt(3, pacient.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void modificaPacientMajor(DatabaseManager db, Pacient pacient) {
        String queryEditPacient = "UPDATE PacientMajor SET nume = ?, prenume = ? WHERE id_pacient_major = ?";

        try (PreparedStatement stmt = db.getConnection().prepareStatement(queryEditPacient)) {
            stmt.setString(1, pacient.getNume());
            stmt.setString(2, pacient.getPrenume());
            stmt.setInt(3, pacient.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void modificaPersoana(DatabaseManager db, Pacient pacient) {
        String queryEditPacient = "UPDATE Persoana SET nume = ?, prenume = ? WHERE id = ?";

        try (PreparedStatement stmt = db.getConnection().prepareStatement(queryEditPacient)) {
            stmt.setString(1, pacient.getNume());
            stmt.setString(2, pacient.getPrenume());
            stmt.setInt(3, pacient.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void insertPacient(DatabaseManager db, Persoana pacient) {
        String queryInsertPacient = "INSERT INTO Pacient(id_pacient, nume, prenume, varsta, cnp) VALUES(?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = db.getConnection().prepareStatement(queryInsertPacient)) {
            stmt.setInt(1, pacient.getId());
            stmt.setString(2, pacient.getNume());
            stmt.setString(3, pacient.getPrenume());
            stmt.setInt(4, pacient.getVarsta());
            stmt.setString(5, pacient.getCNP());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public int getNumarPacienti(DatabaseManager db, int idMedic) {
        String queryGetNumarPacienti = "SELECT * FROM MedicPacient WHERE id_medic = ?";
        int numarPacienti = 0;
        try (PreparedStatement stmt = db.getConnection().prepareStatement(queryGetNumarPacienti)) {
            stmt.setInt(1, idMedic);
            ResultSet rs = stmt.executeQuery(); {
                while (rs.next()) {
                    numarPacienti ++;
                }
                return numarPacienti;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public Pacient getPacient(DatabaseManager db, String cnp) {
        String querySelectPacient = "SELECT * FROM Pacient WHERE cnp = ?";
        try (PreparedStatement stmt = db.getConnection().prepareStatement(querySelectPacient)) {
            stmt.setString(1, cnp);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int idPacient = rs.getInt("id_pacient");
                    String nume = rs.getString("nume");
                    String prenume = rs.getString("prenume");
                    int varsta = rs.getInt("varsta");
                    return new Pacient(idPacient, nume, prenume, varsta, cnp);
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void insertPacientMajor(DatabaseManager db, PacientMajor pacientmajor) {
        String queryInsertPacientMajor = "INSERT INTO PacientMajor(id_pacient_major, nume, prenume, varsta, cnp, asigurat) VALUES(?,?,?,?,?,?)";
        try (PreparedStatement stmt = db.getConnection().prepareStatement(queryInsertPacientMajor)) {
            stmt.setInt(1, pacientmajor.getId());
            stmt.setString(2, pacientmajor.getNume());
            stmt.setString(3, pacientmajor.getPrenume());
            stmt.setInt(4, pacientmajor.getVarsta());
            stmt.setString(5, pacientmajor.getCNP());
            stmt.setBoolean(6, pacientmajor.getAsiguratCNAS());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void stergePacient(DatabaseManager db, int idPacient) {
        String queryDeletePacient = "DELETE FROM Pacient WHERE id_pacient = ?";

        try (PreparedStatement stmt = db.getConnection().prepareStatement(queryDeletePacient)) {
            stmt.setInt(1, idPacient);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void insertPacientMinor(DatabaseManager db, PacientMinor pacientMinor) {
        String queryInsertPacientMinor = "INSERT INTO PacientMinor(id_pacient_minor, nume, prenume, varsta, cnp) VALUES(?,?,?,?,?)";
        try (PreparedStatement stmt = db.getConnection().prepareStatement(queryInsertPacientMinor)) {
            stmt.setInt(1, pacientMinor.getId());
            stmt.setString(2, pacientMinor.getNume());
            stmt.setString(3, pacientMinor.getPrenume());
            stmt.setInt(4, pacientMinor.getVarsta());
            stmt.setString(5, pacientMinor.getCNP());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void stergePacientMajor(DatabaseManager db, int idPacientMajor) {
        String queryDeletePacientMajor = "DELETE FROM PacientMajor WHERE id_pacient_major = ?";

        try (PreparedStatement stmt = db.getConnection().prepareStatement(queryDeletePacientMajor)) {
            stmt.setInt(1, idPacientMajor);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void stergePacientMinor(DatabaseManager db, int idPacientMinor) {
        String queryDeletePacientMinor = "DELETE FROM PacientMinor WHERE id_pacient_minor = ?";

        try (PreparedStatement stmt = db.getConnection().prepareStatement(queryDeletePacientMinor)) {
            stmt.setInt(1, idPacientMinor);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void stergePacientMedic(DatabaseManager db, int idPacient) {
        String queryDeletePacient = "DELETE FROM MedicPacient WHERE id_pacient = ?";

        try (PreparedStatement stmt = db.getConnection().prepareStatement(queryDeletePacient)) {
            stmt.setInt(1, idPacient);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void stergeProgramariPacient(DatabaseManager db, int idPacient) {
        String queryDeleteProgramari = "DELETE FROM Programare WHERE id_pacient = ?";

        try (PreparedStatement stmt = db.getConnection().prepareStatement(queryDeleteProgramari)) {
            stmt.setInt(1, idPacient);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void infoPacient(DatabaseManager db) {
        System.out.print("\nIntroduceti ID-ul dumneavoastra: ");
        int idPacient = scanner.nextInt();
        while (checkIdPacient(db, idPacient) == "") {
            System.out.print("\nID incorect, incercati din nou: ");
            idPacient = scanner.nextInt();
        }
        List<Programare> programariPacient = serviceProgramare.getProgramari(db, idPacient);
        String cnpPacient = checkIdPacient(db, idPacient);
        scanner.nextLine();
        Pacient pacient = getPacient(db, cnpPacient);
        System.out.print(pacient.toString());
        System.out.println("Numar programari: " + programariPacient.size() + "\n");
    }
    public void editeazaPacient(DatabaseManager db) {
        System.out.print("\nIntroduceti ID-ul dumneavoastra: ");

        int idPacient = scanner.nextInt();

        while (checkIdPacient(db, idPacient) == null) {
            System.out.print("ID incorect, incercati din nou: ");
            idPacient = scanner.nextInt();
        }
        String cnpPacient = checkIdPacient(db, idPacient);
        Pacient pacient = getPacient(db, cnpPacient);
        pacient.setId(idPacient);

        scanner.nextLine();
        System.out.print("\nDoriti sa modificati numele? [da / nu]: ");
        String raspuns = scanner.nextLine();
        if (Objects.equals(raspuns, "da")) {
            System.out.print("\nIntroduceti noul nume: ");
            String nume = scanner.nextLine();
            pacient.setNume(nume);
            modificaPacient(db, pacient);
            modificaPersoana(db, pacient);
            if (pacient.getVarsta() < 18) {
                modificaPacientMinor(db, pacient);
            } else {
                modificaPacientMajor(db, pacient);
            }
        }
        System.out.print("\nDoriti sa modificati prenumele? [da / nu]: ");
        raspuns = scanner.nextLine();
        if (Objects.equals(raspuns, "da")) {
            System.out.print("\nIntroduceti noul prenume: ");
            String prenume = scanner.nextLine();
            pacient.setPrenume(prenume);
            modificaPacient(db, pacient);
            modificaPersoana(db, pacient);
            if (pacient.getVarsta() < 18) {
                modificaPacientMinor(db, pacient);
            } else {
                modificaPacientMajor(db, pacient);
            }
        }
        System.out.println("\nModificari salvate cu succes!");
    }
    public void deletePacient(DatabaseManager db) {
        System.out.print("\nIntroduceti ID-ul dumneavoastra: ");
        int idPacient = scanner.nextInt();
        while (Objects.equals(checkIdPacient(db, idPacient), "")) {
            System.out.print("ID incorect, incercati din nou: ");
            idPacient = scanner.nextInt();
        }
        String cnpPacient = checkIdPacient(db, idPacient);
        Pacient pacient = getPacient(db, cnpPacient);
        List<Programare> programariPacient = serviceProgramare.getProgramari(db, idPacient);
        for (Programare programare : programariPacient) {
            serviceMedic.elibereazaProgramMedic(db, programare);
        }
        pacient.setId(idPacient);
        scanner.nextLine();
        stergeProgramariPacient(db, idPacient);
        stergePacientMedic(db, idPacient);
        stergePacient(db, idPacient);
        servicePersoana.stergePersoana(db, idPacient);
        if (pacient.getVarsta() < 18) {
            stergePacientMinor(db, idPacient);
        } else {
            stergePacientMajor(db, idPacient);
        }
        System.out.println("\nPacientul cu ID-ul " + idPacient + " a fost sters din sistem!");
    }

}
