package packages.Services;

import packages.Objects.Clinica;
import packages.Objects.Medic;
import packages.Objects.Programare;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import static packages.Services.MainService.*;

public class ServiceMedic {
    private static ServiceMedic instance;
    private Scanner scanner = new Scanner(System.in);

    private ServiceMedic() {}

    public static ServiceMedic getInstance() {
        if (instance == null) {
            synchronized (ServiceMedic.class) {
                if (instance == null) {
                    instance = new ServiceMedic();
                }
            }
        }
        return instance;
    }
    public List<Medic> getMedici(DatabaseManager db) {
        List<Medic> medici = new ArrayList<>();

        try {
            String querySelectMedici = "SELECT * FROM Medic";
            PreparedStatement statement = db.getConnection().prepareStatement(querySelectMedici);
            ResultSet resultSet = statement.executeQuery(); {
                while (resultSet.next()) {
                    int idMedic = resultSet.getInt("id_medic");
                    int idClinica = resultSet.getInt("id_clinica");
                    String nume = resultSet.getString("nume");
                    String prenume = resultSet.getString("prenume");
                    int varsta = resultSet.getInt("varsta");
                    String cnp = resultSet.getString("cnp");
                    medici.add(new Medic(idMedic, idClinica, nume, prenume, varsta, cnp));
                }
                return medici;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void stergeMedic(DatabaseManager db, int idMedic) {
        String queryDeleteMedic = "DELETE FROM Medic WHERE id_medic = ?";

        try (PreparedStatement stmt = db.getConnection().prepareStatement(queryDeleteMedic)) {
            stmt.setInt(1, idMedic);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void stergeMedicPacient(DatabaseManager db, int idMedic) {
        String queryDeleteMedicPacient = "DELETE FROM MedicPacient WHERE id_medic = ?";

        try(PreparedStatement stmt = db.getConnection().prepareStatement(queryDeleteMedicPacient)) {
            stmt.setInt(1, idMedic);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void stergeProgramMedic(DatabaseManager db, int idMedic) {
        String queryDeleteProgramMedic = "DELETE FROM ProgramMedic WHERE id_medic = ?";

        try(PreparedStatement stmt = db.getConnection().prepareStatement(queryDeleteProgramMedic)) {
            stmt.setInt(1, idMedic);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void stergeProgramari(DatabaseManager db, int idMedic) {
        String queryDeleteProgramari = "DELETE FROM Programare WHERE id_medic = ?";

        try (PreparedStatement stmt = db.getConnection().prepareStatement(queryDeleteProgramari)) {
            stmt.setInt(1, idMedic);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public Medic getMedic(DatabaseManager db, int idMedic) {
        String queryGetMedic = "SELECT * FROM Medic WHERE id_medic = ?";
        try (PreparedStatement stmt = db.getConnection().prepareStatement(queryGetMedic)) {
            stmt.setInt(1, idMedic);
            ResultSet rs = stmt.executeQuery(); {
                if (rs.next()) {
                    int idClinica = rs.getInt("id_clinica");
                    String nume = rs.getString("nume");
                    String prenume = rs.getString("prenume");
                    return new Medic(idClinica, nume, prenume, 0, "");
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public List<Medic> getMediciClinica(DatabaseManager db, int idClinica) {
        List<Medic> medici = new ArrayList<>();

        try {
            String querySelectMedici = "SELECT * FROM Medic WHERE id_clinica = ?";
            PreparedStatement statement = db.getConnection().prepareStatement(querySelectMedici);
            statement.setInt(1, idClinica);
            ResultSet resultSet = statement.executeQuery(); {
                while (resultSet.next()) {
                    int idMedic = resultSet.getInt("id_medic");
                    String nume = resultSet.getString("nume");
                    String prenume = resultSet.getString("prenume");
                    int varsta = resultSet.getInt("varsta");
                    String cnp = resultSet.getString("cnp");
                    medici.add(new Medic(idMedic, idClinica, nume, prenume, varsta, cnp));
                }
                return medici;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public Map<String, Boolean> getProgramMedic(DatabaseManager db, int idMedic, String ziProgramare) {
        Map<String, Boolean> program = new HashMap<>();
        try {
            String querySelectProgramMedic = "SELECT * FROM ProgramMedic WHERE id_medic = ?";
            PreparedStatement stmt = db.getConnection().prepareStatement(querySelectProgramMedic);
            stmt.setInt(1, idMedic);
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                String zi = resultSet.getString("zi");
                String ora = resultSet.getString("ora");
                Boolean disponibilitate = resultSet.getBoolean("disponibilitate");
                if (Objects.equals(zi, ziProgramare)) {
                    program.put(ora, disponibilitate);
                }
            }
            return program;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public Boolean verificaNumePrenumeMedic(DatabaseManager db, String nume, String prenume) {
        String querySelectMedic = "SELECT id_medic FROM Medic WHERE nume = ? AND prenume = ?";
        try (PreparedStatement stmt = db.getConnection().prepareStatement(querySelectMedic)) {
            stmt.setString(1, nume);
            stmt.setString(2, prenume);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return true;
                } else {
                    return false;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void insertMedic(DatabaseManager db, Medic medic) {
        String queryInsertMedic = "INSERT INTO Medic(id_medic, id_clinica, nume, prenume, varsta, CNP) VALUES(?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = db.getConnection().prepareStatement(queryInsertMedic)) {
            stmt.setInt(1, medic.getId());
            stmt.setInt(2, medic.getIdClinica());
            stmt.setString(3, medic.getNume());
            stmt.setString(4, medic.getPrenume());
            stmt.setInt(5, medic.getVarsta());
            stmt.setString(6, medic.getCNP());
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void insertProgramMedic(DatabaseManager db, Medic medic) {
        List<String> zile = new ArrayList<>();
        zile.add("Luni");
        zile.add("Marti");
        zile.add("Miercuri");
        zile.add("Joi");
        zile.add("Vineri");

        List<String> ore = new ArrayList<>();
        ore.add("9:00");
        ore.add("10:00");
        ore.add("11:00");
        ore.add("12:00");
        ore.add("13:00");
        ore.add("14:00");
        ore.add("15:00");
        ore.add("16:00");

        String queryInsertProgramMedic = "INSERT INTO ProgramMedic(id_medic, zi, ora, disponibilitate) VALUES(?, ?, ?, ?)";
        for (String zi : zile) {
            for (String ora : ore) {
                try (PreparedStatement stmt = db.getConnection().prepareStatement(queryInsertProgramMedic)) {
                    stmt.setInt(1, medic.getId());
                    stmt.setString(2, zi);
                    stmt.setString(3, ora);
                    stmt.setBoolean(4, true);
                    stmt.executeUpdate();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
    public void updateProgramMedic(DatabaseManager db, Programare programare) {
        String queryUpdateProgramMedic = "UPDATE ProgramMedic SET disponibilitate = ? WHERE id_medic = ? AND zi = ? AND ora = ?";
        try (PreparedStatement stmt = db.getConnection().prepareStatement(queryUpdateProgramMedic)) {
            stmt.setBoolean(1, false);
            stmt.setInt(2, programare.getIdMedic());
            stmt.setString(3, programare.getZi());
            stmt.setString(4, programare.getOra());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void elibereazaProgramMedic(DatabaseManager db, Programare programare) {
        String queryUpdateProgramMedic = "UPDATE ProgramMedic SET disponibilitate = ? WHERE id_medic = ? AND zi = ? AND ora = ?";
        try (PreparedStatement stmt = db.getConnection().prepareStatement(queryUpdateProgramMedic)) {
            stmt.setBoolean(1, true);
            stmt.setInt(2, programare.getIdMedic());
            stmt.setString(3, programare.getZi());
            stmt.setString(4, programare.getOra());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private void actualizeazaMedic(DatabaseManager db, Medic medic) {
        String updateQuery = "UPDATE Medic SET nume = ?, prenume = ?, id_clinica = ? WHERE id_medic = ?";
        try (PreparedStatement stmt = db.getConnection().prepareStatement(updateQuery)) {
            stmt.setString(1, medic.getNume());
            stmt.setString(2, medic.getPrenume());
            stmt.setInt(3, medic.getIdClinica());
            stmt.setInt(4, medic.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void adaugaMedic(DatabaseManager db) {
        List<Clinica> clinici = serviceClinica.getClinici(db);
        if(clinici.isEmpty()) {
            System.out.println("Nicio clinica inregistrata in sistem!\n");
            return;
        }
        System.out.println("\n     Clinici disponibile: ");
        for (Clinica c : clinici) {
            System.out.println(c.toString());
        }
        System.out.print("\nIntroduceti ID-ul clinicii unde vreti sa adaugati medicul: ");
        int idClinica = scanner.nextInt();
        scanner.nextLine();

        for(Clinica clinica : clinici) {
            if (Objects.equals(clinica.getId(), idClinica)) {
                System.out.print("Introduceti numele medicului: ");
                String numeMedic = scanner.nextLine();

                System.out.print("Introduceti prenumele medicului: ");
                String prenumeMedic = scanner.nextLine();

                while (verificaNumePrenumeMedic(db, numeMedic, prenumeMedic)) {
                    System.out.println("Numele si prenumele introduse coincid cu ale altui medic, te rog incearca din nou!");
                    System.out.print("Introduceti numele medicului: ");
                    numeMedic = scanner.nextLine();

                    System.out.print("Introduceti prenumele medicului: ");
                    prenumeMedic = scanner.nextLine();
                }

                System.out.print("Introduceti varsta medicului: ");
                int varstaMedic = scanner.nextInt();
                while (!verificaVarsta(varstaMedic, "medic")) {
                    System.out.print("Varsta minima 18 ani, introdu o noua varsta: ");
                    varstaMedic = scanner.nextInt();
                }
                scanner.nextLine();
                System.out.print("Introduceti CNP-ul medicului: ");
                String cnpMedic = scanner.nextLine();
                while (!verificaCnp(cnpMedic)) {
                    System.out.print("Format CNP invalid, incearca din nou: ");
                    cnpMedic = scanner.nextLine();
                }
                Medic medic = new Medic(idClinica, numeMedic, prenumeMedic, varstaMedic, cnpMedic);
                servicePersoana.insertPersoana(db, medic);
                int idMedic = servicePersoana.getIdPersoana(db, cnpMedic);
                medic.setId(idMedic);
                insertMedic(db, medic);
                insertProgramMedic(db, medic);
                System.out.println("Datele medicului au fost adaugate cu succes in sistem!\n");
                return;
            }
        }
    }
    public void editeazaInfoMedic(DatabaseManager db) {
        List<Medic> medici = getMedici(db);
        System.out.println("\n     Medici inregistrati in sistem");
        for (Medic medic : medici) {
            System.out.println(medic);
        }
        System.out.print("\nIntroduceti ID-ul medicului: ");
        int idMedic = scanner.nextInt();
        scanner.nextLine();
        Medic medicCautat = null;

        for (Medic medic : medici) {
            if (medic.getId() == idMedic) {
                medicCautat = medic;
                break;
            }
        }

        if (medicCautat == null) {
            System.out.println("Nu a fost găsit niciun medic cu ID-ul specificat.");
            return;
        }

        System.out.print("Doriti sa modificati numele medicului? [da / nu]: ");
        String raspuns = scanner.nextLine();
        String numeNou, prenumeNou;
        if (Objects.equals(raspuns, "da")) {
            System.out.print("Introduceti noul nume al medicului: ");
            numeNou = scanner.nextLine();
            while (verificaNumePrenumeMedic(db, numeNou, medicCautat.getPrenume())) {
                System.out.print("Exista deja un medic inregistrat, introdu un nume diferit: ");
                numeNou = scanner.nextLine();
            }
            medicCautat.setNume(numeNou);
            actualizeazaMedic(db, medicCautat);
            System.out.println("Nume modificat cu succes!");
        }

        System.out.print("\nDoriti sa modificati prenumele medicului? [da / nu]: ");
        raspuns = scanner.nextLine();
        if (Objects.equals(raspuns, "da")) {
            System.out.print("Introduceti noul prenume al medicului: ");
            prenumeNou = scanner.nextLine();
            while (verificaNumePrenumeMedic(db, medicCautat.getNume(), prenumeNou)) {
                System.out.print("Exista deja un medic inregistrat, inrodu alt prenume: ");
                prenumeNou = scanner.nextLine();
            }
            medicCautat.setPrenume(prenumeNou);
            actualizeazaMedic(db, medicCautat);
            System.out.println("Prenume modificat cu succes!");
        }

        System.out.print("\nDoriti sa modificati clinica medicului? [da / nu]: ");
        raspuns = scanner.nextLine();
        if (Objects.equals(raspuns, "da")) {
            List<Clinica> clinici = serviceClinica.getClinici(db);
            System.out.println("\n     Clinici disponibile:");
            for (Clinica clinica : clinici) {
                System.out.println(clinica);
            }
            System.out.print("\nIntroduceti ID-ul noii clinici: ");
            int idClinicaNoua = scanner.nextInt();
            scanner.nextLine();

            boolean clinicaGasita = false;
            for (Clinica clinica : clinici) {
                if (clinica.getId() == idClinicaNoua) {
                    clinicaGasita = true;
                    break;
                }
            }

            if (!clinicaGasita) {
                System.out.println("Clinica cu ID-ul specificat nu a fost găsită.");
            } else {
                medicCautat.setIdClinica(idClinicaNoua);
                actualizeazaMedic(db, medicCautat);
                System.out.println("Clinica modificată cu succes!");
            }
        }
    }
    public void infoMedic(DatabaseManager db) {
        List<Medic> medici = getMedici(db);
        if (medici.size() == 0) {
            System.out.println("\nNu exista niciun medic in sistem!");
            return;
        }
        System.out.println("\n     Medici inregistrati: \n");
        for (Medic medic : medici) {
            System.out.println(medic.getId() + ". " + medic.getNume() + " " + medic.getPrenume());
        }
        System.out.print("\nAlege ID-ul unui medic:");
        int idMedic = scanner.nextInt();
        scanner.nextLine();

        Medic medic = getMedic(db, idMedic);
        medic.setId(idMedic);
        System.out.println(medic.toString());
        int numarPacienti = servicePacient.getNumarPacienti(db, idMedic);
        System.out.println("Numar pacienti: " + numarPacienti + "\n");
    }

    public void deleteMedic(DatabaseManager db) {
        List<Medic> medici = getMedici(db);
        for (Medic medic : medici) {
            System.out.println(medic.getId() + ". " + medic.getNume() + " " + medic.getPrenume());
        }
        System.out.print("\nID-ul medicului pe care vreti sa il stergeti din sistem: ");
        int idMedic = scanner.nextInt();
        scanner.nextLine();
        stergeMedicPacient(db, idMedic);
        stergeProgramMedic(db, idMedic);
        stergeProgramari(db, idMedic);
        stergeMedic(db, idMedic);
        servicePersoana.stergePersoana(db, idMedic);
        System.out.println("\nMedicul a fost sters din sistem!");
    }

}
