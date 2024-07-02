package packages.Services;

import packages.Objects.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import static packages.Services.MainService.*;

public class ServiceProgramare {

    private static ServiceProgramare instance;
    private Scanner scanner = new Scanner(System.in);

    private ServiceProgramare() {}

    public static ServiceProgramare getInstance() {
        if (instance == null) {
            synchronized (ServiceProgramare.class) {
                if (instance == null) {
                    instance = new ServiceProgramare();
                }
            }
        }
        return instance;
    }

    public List<Programare> getProgramari(DatabaseManager db, int idPacient) {
        String queryGetProgramari = "SELECT * FROM Programare WHERE id_pacient = ?";
        List<Programare> programari = new ArrayList<>();

        try (PreparedStatement stmt = db.getConnection().prepareStatement(queryGetProgramari)) {
            stmt.setInt(1, idPacient);
            ResultSet rs = stmt.executeQuery(); {
                while (rs.next()) {
                    int id_medic = rs.getInt("id_medic");
                    String zi = rs.getString("zi");
                    String ora = rs.getString("ora");
                    programari.add(new Programare(zi, ora, id_medic, idPacient));
                }
                return programari;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Programare getProgramare(DatabaseManager db, int idProgramare) {
        String queryGetProgramare = "SELECT * FROM Programare WHERE id = ?";
        try (PreparedStatement stmt = db.getConnection().prepareStatement(queryGetProgramare)) {
            stmt.setInt(1, idProgramare);
            ResultSet rs = stmt.executeQuery(); {
                if (rs.next()) {
                    int idMedic = rs.getInt("id_medic");
                    int idPacient = rs.getInt("id_pacient");
                    String zi = rs.getString("zi");
                    String ora = rs.getString("ora");
                    return new Programare(zi, ora, idMedic, idPacient);
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void editProgramare(DatabaseManager db, Programare programare) {
        String queryUpdateProgramare = "UPDATE Programare SET ora = ? WHERE id = ?";

        try (PreparedStatement stmt = db.getConnection().prepareStatement(queryUpdateProgramare)) {
            stmt.setString(1, programare.getOra());
            stmt.setInt(2, programare.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void deleteProgramare(DatabaseManager db, int idProgramare) {
        String queryDeleteProgramare = "DELETE FROM Programare WHERE id = ?";
        try (PreparedStatement stmt = db.getConnection().prepareStatement(queryDeleteProgramare)) {
            stmt.setInt(1, idProgramare);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void insertProgramare(DatabaseManager db, Programare programare) {
        String queryInsertProgramare = "INSERT INTO Programare(zi, ora, id_medic, id_pacient) VALUES(?, ?, ?, ?)";
        try (PreparedStatement stmt = db.getConnection().prepareStatement(queryInsertProgramare)) {
            stmt.setString(1, programare.getZi());
            stmt.setString(2, programare.getOra());
            stmt.setInt(3, programare.getIdMedic());
            stmt.setInt(4, programare.getIdPacient());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void insertRowMedicPacient(DatabaseManager db, Programare programare) {
        String queryInsertMedicPacient = "INSERT INTO MedicPacient(id_medic, id_pacient) VALUES(?, ?)";
        try (PreparedStatement stmt = db.getConnection().prepareStatement(queryInsertMedicPacient)) {
            stmt.setInt(1, programare.getIdMedic());
            stmt.setInt(2, programare.getIdPacient());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public Boolean getRowMedicPacient(DatabaseManager db, int idPacient) {
        String queryGetPacient = "SELECT * FROM MedicPacient WHERE id_pacient = ?";
        try (PreparedStatement stmt = db.getConnection().prepareStatement(queryGetPacient)) {
            stmt.setInt(1, idPacient);
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
    public void adaugaProgramare(DatabaseManager db) {
        List<Clinica> clinici = serviceClinica.getClinici(db);

        if(clinici.isEmpty()) {
            System.out.println("Nicio clinica inregistrata in sistem!\n");
            return;
        }
        System.out.println("\n     Clinici disponibile: ");
        for (Clinica clinica : clinici) {
            System.out.println(clinica.getId() + ". " + clinica.getDenumire());
        }
        System.out.print("\nAlegeti ID-ul clinicii pentru programarea dumneavostra: ");
        int idClinica = scanner.nextInt();
        scanner.nextLine();

        for(Clinica clinica : clinici) {
            if(Objects.equals(idClinica, clinica.getId())) {
                List<Medic> mediciClinica = serviceMedic.getMediciClinica(db, idClinica);
                if(Objects.equals(mediciClinica.size(), 0)) {
                    System.out.println("Clinica nu are niciun medic inregistrat");
                    return;
                }
                System.out.print("Alegeti ziua programarii [Luni - Vineri]: ");
                String ziProgramare = scanner.nextLine();
                System.out.println("\n     - Lista medici Clinica " + clinica.getDenumire() + " -");

                for(Medic medic : mediciClinica) {
                    System.out.println(medic.toString());
                }
                System.out.print("\nID-ul medicului dorit: ");
                int idMedic = scanner.nextInt();
                scanner.nextLine();

                for(Medic medic : mediciClinica) {
                    if(Objects.equals(medic.getId(), idMedic)) {
                        Map<String, Boolean> programMedic = serviceMedic.getProgramMedic(db, idMedic, ziProgramare);
                        System.out.println("\n    - Program medic" + " pentru ziua de " + ziProgramare + " -");

                        for (Map.Entry<String, Boolean> entry : programMedic.entrySet()) {
                            String ora = entry.getKey();
                            Boolean disponibilitate = entry.getValue();

                            System.out.println(ora + ": " + (disponibilitate ? "Disponibil" : "Indisponibil"));
                        }
                        System.out.print("\nAlegeti o ora [hh:00] interval 9:00 - 16:00: ");
                        String oraProgramare = scanner.nextLine();
                        while(!verificaOra(oraProgramare)) {
                            System.out.print("Ora introdusa nu este valida, incercati din nou: ");
                            oraProgramare = scanner.nextLine();
                        }
                        System.out.print("CNP: ");
                        String cnpPacient = scanner.nextLine();
                        while (!verificaCnp(cnpPacient)) {
                            System.out.print("Format CNP invalid, incercati din nou: ");
                            cnpPacient = scanner.nextLine();
                        }
                        if(servicePacient.getPacient(db, cnpPacient) != null) {
                            Pacient pacient = servicePacient.getPacient(db, cnpPacient);
                            Programare programare = new Programare(ziProgramare, oraProgramare, idMedic, pacient.getId());
                            insertProgramare(db, programare);

                            System.out.println("V-am identificat in sistemul nostru!");
                            serviceMedic.updateProgramMedic(db, programare);
                            if (!getRowMedicPacient(db, pacient.getId())) {
                                insertRowMedicPacient(db, programare);
                            }
                            System.out.println("Programare realizata cu succes!\n");
                        } else {
                            System.out.print("Numele dumneavoastra de familie: ");
                            String numePacient = scanner.nextLine();
                            System.out.print("Prenumele dumneavoastra: ");
                            String prenumePacient = scanner.nextLine();
                            System.out.print("Varsta dumneavoastra: ");
                            int varstaPacient = scanner.nextInt();
                            scanner.nextLine();

                            if (varstaPacient > 17) {
                                System.out.print("Sunteti asigurat CNAS ? [da / nu]: ");
                                String raspuns = scanner.nextLine();
                                Boolean asigurat = true;
                                if (Objects.equals(raspuns, "nu")) {
                                    System.out.println("Va informam ca pentru persoanele neasigurate CNAS sunt percepute tarife mai mari!");
                                    asigurat = false;
                                }
                                PacientMajor pacientMajor = new PacientMajor(numePacient, prenumePacient, varstaPacient, cnpPacient, asigurat);
                                servicePersoana.insertPersoana(db, pacientMajor);
                                int idPacient = servicePersoana.getIdPersoana(db, cnpPacient);
                                Programare programare = new Programare(ziProgramare, oraProgramare, idMedic, idPacient);
                                pacientMajor.setId(idPacient);
                                servicePacient.insertPacient(db, pacientMajor);
                                servicePacient.insertPacientMajor(db, pacientMajor);
                                serviceMedic.updateProgramMedic(db, programare);
                                insertProgramare(db, programare);
                                insertRowMedicPacient(db, programare);
                                System.out.println("\nProgramare inregistrata cu succes!\nID-ul dumneavoastra: " + pacientMajor.getId());
                                return;
                            } else {
                                System.out.print("Este nevoie de un insotitor major pentru a efectua operatiunea, continuati? [da / nu]: ");
                                String raspuns = scanner.nextLine();
                                if (Objects.equals(raspuns, "da")) {
                                    System.out.print("Nume insotitor: ");
                                    String numeInsotitor = scanner.nextLine();
                                    System.out.print("Prenume insotitor: ");
                                    String prenumeInsotitor = scanner.nextLine();
                                    System.out.print("CNP insotitor: ");
                                    String cnpInsotitor = scanner.nextLine();
                                    while(Objects.equals(cnpPacient, cnpInsotitor)) {
                                        System.out.println("CNP-ul trebuie sa fie diferit de cel al pacientului, incercati din nou: ");
                                        cnpInsotitor = scanner.nextLine();
                                    }
                                    while (!verificaCnp(cnpInsotitor)) {
                                        System.out.println("Format CNP invalid, incercati din nou: ");
                                        cnpInsotitor = scanner.nextLine();
                                    }
                                    System.out.print("Varsta insotitor: ");
                                    int varstaInsotitor = scanner.nextInt();
                                    scanner.nextLine();

                                    if (varstaInsotitor < 18) {
                                        System.out.println("Programarea nu poate fi efectuata in absenta unui insotitor major!");
                                        return;
                                    } else {

                                        System.out.print("Semnatura: ");
                                        String semnatura = scanner.nextLine();
                                        while(Objects.equals(semnatura, "")) {
                                            System.out.print("Semnatura obligatorie: ");
                                            semnatura = scanner.nextLine();
                                        }
                                        PacientMinor pacientMinor = new PacientMinor(numePacient, prenumePacient, varstaPacient, cnpPacient);
                                        servicePersoana.insertPersoana(db, pacientMinor);
                                        int idPacient = servicePersoana.getIdPersoana(db, pacientMinor.getCNP());
                                        pacientMinor.setId(idPacient);
                                        servicePacient.insertPacient(db, pacientMinor);
                                        servicePacient.insertPacientMinor(db, pacientMinor);

                                        Insotitor insotitor = new Insotitor(idPacient, numeInsotitor, prenumeInsotitor, varstaInsotitor, cnpInsotitor, semnatura);
                                        servicePersoana.insertPersoana(db, insotitor);
                                        int idInsotitor = servicePersoana.getIdPersoana(db, insotitor.getCNP());
                                        insotitor.setId(idInsotitor);
                                        serviceInsotitor.insertInsotitor(db, insotitor);

                                        Programare programare = new Programare(ziProgramare, oraProgramare, idMedic, idPacient);
                                        serviceMedic.updateProgramMedic(db, programare);
                                        insertProgramare(db, programare);
                                        insertRowMedicPacient(db, programare);
                                        System.out.println("\nProgramare inregistrata cu succes!\nID-ul dumneavoastra: " + pacientMinor.getId());
                                        return;
                                    }
                                } else {
                                    return;
                                }
                            }
                        }
                    }
                }

                return;
            }
        }
    }
    public void veziProgramari(DatabaseManager db) {
        System.out.print("\nIntroduceti ID-ul dumneavoastra: ");
        int idPacient = scanner.nextInt();

        while(servicePacient.checkIdPacient(db, idPacient) == "") {
            System.out.print("ID incorect, incercati din nou: ");
            idPacient = scanner.nextInt();
        }
        scanner.nextLine();
        List<Programare> programari = getProgramari(db, idPacient);
        for (Programare p : programari) {
            Medic medic = serviceMedic.getMedic(db, p.getIdMedic());
            Clinica clinicaMedic = serviceClinica.getClinicaMedic(db, medic.getIdClinica());

            System.out.println(p.toString());
            System.out.println("Clinica: " + clinicaMedic.getDenumire() +
                    "\nAdresa: " + clinicaMedic.getAdresa() +
                    "\nTelefon: " + clinicaMedic.getNumarTelefon());
            System.out.println("Medic: " + medic.getNume() + " " + medic.getPrenume() + "\n");
        }
    }
    public void anuleazaProgramare(DatabaseManager db) {
        System.out.print("\nIntroduceti ID-ul programarii: ");
        int idProgramare = scanner.nextInt();
        scanner.nextLine();
        Programare programare = getProgramare(db, idProgramare);
        if (programare == null) {
            System.out.print("Nu exista nicio programarea cu ID-ul specificat!\n");
            return;
        }
        serviceMedic.elibereazaProgramMedic(db, programare);
        deleteProgramare(db, idProgramare);
        System.out.println("\nProgramare anulata cu succes!\n");
    }
    public void updateProgramare(DatabaseManager db) {
        System.out.print("\nIntroduceti ID-ul programarii: ");
        int idProgramare = scanner.nextInt();
        scanner.nextLine();
        Programare programare = getProgramare(db, idProgramare);
        serviceMedic.elibereazaProgramMedic(db, programare);
        Map<String, Boolean> program = serviceMedic.getProgramMedic(db, programare.getIdMedic(), programare.getZi());
        for (Map.Entry<String, Boolean> entry : program.entrySet()) {
            String ora = entry.getKey();
            Boolean disponibilitate = entry.getValue();

            System.out.println(ora + ": " + (disponibilitate ? "Disponibil" : "Indisponibil"));
        }
        System.out.print("\nAlegeti o ora [hh:00] interval 9:00 - 16:00: ");
        String oraProgramare = scanner.nextLine();
        while(!verificaOra(oraProgramare)) {
            System.out.print("Ora introdusa nu este valida, incercati din nou: ");
            oraProgramare = scanner.nextLine();
        }
        programare.setOra(oraProgramare);
        programare.setId(idProgramare);
        editProgramare(db, programare);
        serviceMedic.updateProgramMedic(db, programare);
        System.out.println("\nProgramare modificata cu succes!");
    }
}
