package packages.Services;

import packages.Objects.Clinica;
import packages.Objects.Medic;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import static packages.Services.MainService.serviceMedic;
import static packages.Services.MainService.verificaNumarTelefon;

public class ServiceClinica {

    private static ServiceClinica instance;
    private Scanner scanner = new Scanner(System.in);

    private ServiceClinica() {}

    public static ServiceClinica getInstance() {
        if (instance == null) {
            synchronized (ServiceClinica.class) {
                if (instance == null) {
                    instance = new ServiceClinica();
                }
            }
        }
        return instance;
    }

    public List<Clinica> getClinici(DatabaseManager db) {
        List<Clinica> clinici = new ArrayList<>();

        try {
            String querySelectClinici = "SELECT * FROM Clinica";
            Statement statement = db.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(querySelectClinici);
            while (resultSet.next()) {
                int id = resultSet.getInt("id_clinica");
                String denumire = resultSet.getString("denumire");
                String adresa = resultSet.getString("adresa");
                String numarTelefon = resultSet.getString("numarTelefon");
                clinici.add(new Clinica(id, adresa, numarTelefon, denumire));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return clinici;
    }

    public Clinica getClinicaMedic(DatabaseManager db, int idClinica) {
        String queryGetClinica = "SELECT * FROM Clinica WHERE id_clinica = ?";
        try (PreparedStatement stmt = db.getConnection().prepareStatement(queryGetClinica)) {
            stmt.setInt(1, idClinica);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String denumire = rs.getString("denumire");
                String adresa = rs.getString("adresa");
                String telefon = rs.getString("numarTelefon");
                return new Clinica(adresa, telefon, denumire);
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void adaugaClinica(DatabaseManager db) {
        System.out.print("\nIntroduceti denumirea clinicii:  ");
        String denumire = scanner.nextLine();
        System.out.print("Introduceti adresa clinicii " + denumire + ": ");
        String adresa = scanner.nextLine();
        System.out.print("Introduceti numarul de telefon al clinicii " + denumire + ": ");
        String numarTelefon = scanner.nextLine();

        while(verificaNumarTelefon(numarTelefon)) {
            System.out.print("Numar de telefon invalid, introdu alt numar: ");
            numarTelefon = scanner.nextLine();
        }
        String queryInsertClinica = "INSERT INTO Clinica (adresa, numarTelefon, denumire) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = db.getConnection().prepareStatement(queryInsertClinica)) {
            stmt.setString(1, adresa);
            stmt.setString(2, numarTelefon);
            stmt.setString(3, denumire);
            stmt.executeUpdate();
            System.out.println("Clinica " + denumire + " a fost adaugata in sistem!\n");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("A aparut o eroare la adaugarea clinicii in baza de date.");
        }
    }
    public void stergeClinica(DatabaseManager db, int idClinica) {
        String queryDeleteClinica = "DELETE FROM Clinica WHERE id_clinica = ?";

        try (PreparedStatement stmt = db.getConnection().prepareStatement(queryDeleteClinica)) {
            stmt.setInt(1, idClinica);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void editeazaClinica(DatabaseManager db) {
        List<Clinica> clinici = getClinici(db);
        for (Clinica c : clinici) {
            System.out.println(c.toString());
        }

        System.out.print("\nIntroduceti ID-ul clinicii: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        Clinica clinicaSelectata = null;
        for (Clinica clinica : clinici) {
            if (Objects.equals(clinica.getId(), id)) {
                clinicaSelectata = clinica;
                break;
            }
        }

        if (clinicaSelectata == null) {
            System.out.println("Clinica nu a fost gasita.");
            return;
        }

        boolean modificat = false;

        System.out.print("Doriti sa modificati numele? [da / nu]: ");
        String raspuns = scanner.nextLine();
        if (Objects.equals(raspuns, "da")) {
            System.out.print("Introduceti noul nume al clinicii: ");
            String denumireNoua = scanner.nextLine();
            clinicaSelectata.setDenumire(denumireNoua);
            modificat = true;
        }

        System.out.print("\nDoriti sa modificati numarul de telefon? [da / nu]: ");
        raspuns = scanner.nextLine();
        if (Objects.equals(raspuns, "da")) {
            System.out.print("Introduceti noul numar de telefon: ");
            String numarTelefon = scanner.nextLine();
            while (verificaNumarTelefon(numarTelefon)) {
                System.out.print("Numar de telefon invalid, introdu alt numar: ");
                numarTelefon = scanner.nextLine();
            }
            clinicaSelectata.setNumarTelefon(numarTelefon);
            modificat = true;
        }

        System.out.print("\nDoriti sa modificati adresa? [da / nu]: ");
        raspuns = scanner.nextLine();
        if (Objects.equals(raspuns, "da")) {
            System.out.print("Introduceti noua adresa: ");
            String adresa = scanner.nextLine();
            clinicaSelectata.setAdresa(adresa);
            modificat = true;
        }

        if (modificat) {
            String queryUpdateClinica = "UPDATE Clinica SET denumire = ?, numarTelefon = ?, adresa = ? WHERE id_clinica = ?";
            try (PreparedStatement stmt = db.getConnection().prepareStatement(queryUpdateClinica)) {
                stmt.setString(1, clinicaSelectata.getDenumire());
                stmt.setString(2, clinicaSelectata.getNumarTelefon());
                stmt.setString(3, clinicaSelectata.getAdresa());
                stmt.setInt(4, clinicaSelectata.getId());
                stmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("A aparut o eroare la actualizarea clinicii in baza de date.");
            }
            System.out.println("Modificare salvata cu succes!\n");
        } else {
            System.out.println("Nu au fost facute modificari.");
        }
    }

    public void infoClinica(DatabaseManager db) {
        List<Clinica> clinici = getClinici(db);

        if(clinici.isEmpty()) {
            System.out.println("Nicio clinica inregistrata in sistem!\n");
            return;
        }
        System.out.println("\n     Clinici disponibile: ");
        for (Clinica clinica : clinici) {
            System.out.print(clinica.toString());
            List<Medic> mediciClinica = serviceMedic.getMediciClinica(db, clinica.getId());
            System.out.println("Medici angajati: " + mediciClinica.size());
        }
    }
    public void deleteClinica(DatabaseManager db) {
        List<Clinica> clinici = getClinici(db);
        System.out.println("\n    Clinici disponibile:\n ");
        for (Clinica clinica : clinici) {
            System.out.println(clinica.getId() + ". " + clinica.getDenumire());
        }
        System.out.print("\nIntroduceti ID-ul clinicii: ");
        int idClinica = scanner.nextInt();
        scanner.nextLine();

        List<Medic> mediciClinica = serviceMedic.getMediciClinica(db, idClinica);
        for (Medic medic : mediciClinica) {
            serviceMedic.stergeMedicPacient(db, medic.getId());
            serviceMedic.stergeProgramMedic(db, medic.getId());
            serviceMedic.stergeProgramari(db, medic.getId());
            serviceMedic.stergeMedic(db, medic.getId());
        }
        stergeClinica(db, idClinica);
        System.out.println("\nClinica a fost eliminata cu succes din sistem!");
    }
}
