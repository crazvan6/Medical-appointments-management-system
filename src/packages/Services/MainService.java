package packages.Services;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainService {
    private Map<String, String> comenzi = new LinkedHashMap<String, String>();
    Scanner scanner = new Scanner(System.in);

    public static ServiceClinica serviceClinica = ServiceClinica.getInstance();
    public static ServicePersoana servicePersoana = ServicePersoana.getInstance();
    public static ServiceMedic serviceMedic = ServiceMedic.getInstance();
    public static ServicePacient servicePacient = ServicePacient.getInstance();
    public static ServiceInsotitor serviceInsotitor = ServiceInsotitor.getInstance();
    public static ServiceProgramare serviceProgramare = ServiceProgramare.getInstance();

    private static MainService instance;

    private MainService() {
        setComenzi();
    }

    public static synchronized MainService getInstance() {
        if (instance == null) {
            instance = new MainService();
        }
        return instance;
    }

    public void setComenzi() {
        comenzi.put("Adauga o clinica noua       -> ", "add-clinica");
        comenzi.put("Modifica informatii clinica -> ", "edit-clinica");
        comenzi.put("Sterge clinica              -> ", "delete-clinica");
        comenzi.put("Adauga un medic nou         -> ", "add-medic");
        comenzi.put("Modifica informatii medic   -> ", "edit-medic");
        comenzi.put("Vezi informatii clinica     -> ", "info-clinica");
        comenzi.put("Creeaza o programare noua   -> ", "add-programare");
        comenzi.put("Vezi programari pacient     -> ", "vezi-programari");
        comenzi.put("Vezi informatii medic       -> ", "info-medic");
        comenzi.put("Sterge medic               -> ", "delete-medic");
        comenzi.put("Vezi informatii pacient    -> ", "info-pacient");
        comenzi.put("Edit informatii pacient    -> ", "edit-pacient");
        comenzi.put("Sterge pacient             -> ", "delete-pacient");
        comenzi.put("Modifica programare        -> ", "update-programare");
        comenzi.put("Anuleaza programare        -> ", "delete-programare");
        comenzi.put("Opreste aplicatia          -> ", "exit");
    }

    public static boolean verificaNumarTelefon(String numarTelefon) {
        String regex = "(\\+?4)?(0|\\+40)7\\d{8}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(numarTelefon);
        return !matcher.matches();
    }

    public static boolean verificaVarsta(int varsta, String tip) {
        if (Objects.equals(tip, "medic") || Objects.equals(tip, "pacient_major")) return varsta >= 18;
        else return varsta > 0;
    }

    public static boolean verificaCnp(String CNP) {
        String regex = "\\d{13}";
        return Pattern.matches(regex, CNP);
    }

    public static boolean verificaOra(String ora) {
        String regex = "^(1[0-7]|0?[9]):00$";
        return Pattern.matches(regex, ora);
    }

    int idx = 1;
    public void afiseazaComenzi() {
        for (String key : this.comenzi.keySet()) {
            String value = this.comenzi.get(key);
            System.out.print(idx + ". " + key + value + "\n");
            idx++;
        }
    }
}
