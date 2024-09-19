import packages.Services.CSVLogger;
import packages.Services.DatabaseManager;
import packages.Services.MainService;

import java.util.Objects;
import java.util.Scanner;

import static packages.Services.MainService.serviceClinica;
import static packages.Services.MainService.serviceMedic;
import static packages.Services.MainService.servicePacient;
import static packages.Services.MainService.serviceProgramare;

public class Main {

    public static void main(String[] args) {

        DatabaseManager dbManager = DatabaseManager.getInstance();

        MainService mainService = MainService.getInstance();

        CSVLogger logger = CSVLogger.getInstance();

        Scanner scanner = new Scanner(System.in);

        mainService.setComenzi();

        System.out.println("Bine ati venit!");

        System.out.println("Comenzile disponibile ale aplicatiei sunt:\n");

        mainService.afiseazaComenzi();

        System.out.print("\nIntroduceti o comanda: ");

        String comanda = scanner.nextLine();

        while (!comanda.equals("exit")) {
            switch (comanda) {
                case "adauga-clinica": {
                    serviceClinica.adaugaClinica(dbManager);
                    logger.logAction("adauga-clinica");
                    break;
                }

                case "edit-clinica": {
                    serviceClinica.editeazaClinica(dbManager);
                    logger.logAction("edit-clinica");
                    break;
                }
                case "delete-clinica": {
                    serviceClinica.deleteClinica(dbManager);
                    logger.logAction("delete-clinica");
                    break;
                }
                case "add-medic": {
                    serviceMedic.adaugaMedic(dbManager);
                    logger.logAction("add-medic");
                    break;
                }

                case "edit-medic": {
                    serviceMedic.editeazaInfoMedic(dbManager);
                    logger.logAction("edit-medic");
                    break;
                }

                case "info-clinica": {
                    serviceClinica.infoClinica(dbManager);
                    logger.logAction("info-clinica");
                    break;
                }

                case "add-programare": {
                    serviceProgramare.adaugaProgramare(dbManager);
                    logger.logAction("add-programare");
                    break;
                }
                case "vezi-programari": {
                    serviceProgramare.veziProgramari(dbManager);
                    logger.logAction("vezi-programari");
                    break;
                }

                case "info-medic": {
                    serviceMedic.infoMedic(dbManager);
                    logger.logAction("info-medic");
                    break;
                }
                case "delete-medic": {
                    serviceMedic.deleteMedic(dbManager);
                    logger.logAction("delete-medic");
                    break;
                }
                case "info-pacient": {
                    servicePacient.infoPacient(dbManager);
                    logger.logAction("info-pacient");
                    break;
                }
                case "edit-pacient": {
                    servicePacient.editeazaPacient(dbManager);
                    logger.logAction("edit-pacient");
                    break;
                }
                case "delete-pacient": {
                    servicePacient.deletePacient(dbManager);
                    logger.logAction("delete-pacient");
                    break;
                }
                case "update-programare": {
                    serviceProgramare.updateProgramare(dbManager);
                    logger.logAction("update-programare");
                    break;
                }
                case "delete-programare": {
                    serviceProgramare.anuleazaProgramare(dbManager);
                    logger.logAction("delete-programare");
                    break;
                }

                case "exit": {
                    System.out.println("Aplicatia a fost oprita!");
                    logger.logAction("exit");
                    break;
                }
            }
            System.out.print("\nIntroduceti o comanda noua: ");
            comanda = scanner.nextLine();
        }
        System.out.println("Aplicatia a fost oprita!");
        dbManager.closeConnection();
    }
}
