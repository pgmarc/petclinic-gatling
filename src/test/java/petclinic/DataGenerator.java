package petclinic;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.github.javafaker.Faker;

import de.siegmar.fastcsv.writer.CsvWriter;

public class DataGenerator {

    private final static String CSV_BASE_PATH = "src/test/resources";

    private final static int USER_ID_OFFSET = 20;

    private final static int PET_ID_OFFSET = 14;

    private final static int OWNER_USERNAME_OFFSET = 11;

    private final static int VISIT_ID_OFFSET = 10;

    private final static int CONSULTATION_ID_OFFSET = 6;

    private final static String OWNER_PASSWORD = "$2a$10$DaS6KIEfF5CRTFrxIoGc7emY3BpZZ0.fVjwA3NiJ.BjpGNmocaS3e";

    private final static int OWNER_AUTHORITY_ID = 3;

    private final static Map<Integer, String> CLINICS_PRICING = new LinkedHashMap<>();

    private final static Map<Integer, List<Integer>> CLINICS_VETS = new LinkedHashMap<>();

    private final static List<String> CONSULTATION_STATUS = List.of("PENDING", "ANSWERED", "CLOSED");

    private static final int PLATINUM = 1;
    private static final int GOLD = 2;
    private static final int BASIC = 3;

    static {
        CLINICS_PRICING.put(PLATINUM, "platinum");
        CLINICS_PRICING.put(GOLD, "gold");
        CLINICS_PRICING.put(BASIC, "basic");

        CLINICS_VETS.put(PLATINUM, List.of(1, 2));
        CLINICS_VETS.put(GOLD, List.of(3, 4));
        CLINICS_VETS.put(BASIC, List.of(5, 6));
    }

    private final Integer users;

    private final Faker faker;

    private final Random random;

    private final List<Integer> clinicIds;

    public DataGenerator(Integer users, Random random) {
        this.users = users;
        this.random = random;
        this.faker = new Faker(random);
        this.clinicIds = new LinkedList<Integer>();
    }

    private void generateGatlingCSV(String fileName) {

        String path = CSV_BASE_PATH + "/pricing/" + fileName + ".csv";
        File file = new File(path);

        try {
            if (file.createNewFile()) {
                System.out.println("Se ha creado el fichero " + path);
            } else {
                System.out.println(
                        "El archivo " + path + " ya existe, se sobreescribirán los registros");
            }
        } catch (IOException err) {
            err.printStackTrace();
        }

        try (CsvWriter csv = CsvWriter.builder().build(new FileWriter(file))) {
            csv.writeRecord("username", "pricing", "times");
            for (int i = 0; i < this.users; i++) {
                String pricing = CLINICS_PRICING.get(this.clinicIds.get(i));
                int ownerUsernameId = OWNER_USERNAME_OFFSET + i;
                csv.writeRecord("owner" + ownerUsernameId, pricing, Integer.toString(1));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void generateAppUsersSQL(CsvWriter csv) {
        csv.writeRecord("INSERT INTO `appusers` (id, username, password, authority) VALUES");
        for (int i = 0; i < this.users; i++) {
            int userId = USER_ID_OFFSET + i;
            String username = "owner" + userId;

            if (i != this.users - 1) {

                csv.writeRecord("(" + userId, "'" + username + "'", "'" + OWNER_PASSWORD + "'",
                        OWNER_AUTHORITY_ID + ")", "");
            } else {
                csv.writeRecord("(" + userId, "'" + username + "'", "'" + OWNER_PASSWORD + "'",
                        OWNER_AUTHORITY_ID + ");");
            }

        }
    }

    private void generateOwnersSQL(CsvWriter csv) {
        csv.writeRecord(
                "INSERT INTO `owners` (id, user_id, clinic, first_name, last_name, address, city, telephone) VALUES");
        for (int i = 0; i < this.users; i++) {
            Integer CLINIC_PRICING = this.random.nextInt(3) + 1;
            this.clinicIds.add(CLINIC_PRICING);
            Integer ownerId = OWNER_USERNAME_OFFSET + i;
            Integer userId = USER_ID_OFFSET + i;
            String firstName = "'" + faker.name().firstName().replace("'", "''") + "'";
            String lastName = "'" + faker.name().lastName().replace("'", "''") + "'";
            String address = "'" + faker.address().cityPrefix() + "'";
            String city = "'" + faker.address().cityName().replace("'", "''") + "'";
            String telephone = "'" + "111111111" + "'";

            if (i != this.users - 1) {
                csv.writeRecord("(" + ownerId, userId.toString(), CLINIC_PRICING.toString(), firstName, lastName,
                        address, city, telephone + ")", "");
            } else {
                csv.writeRecord("(" + ownerId, userId.toString(), CLINIC_PRICING.toString(), firstName, lastName,
                        address, city, telephone + ");");
            }
        }
    }

    private void generatePetsSQL(CsvWriter csv) {
        csv.writeRecord("INSERT INTO `pets` (id, name, birth_date, type_id, owner_id) VALUES");
        for (int i = 0; i < this.users; i++) {
            Integer petId = PET_ID_OFFSET + i;
            String petName = "'" + this.faker.dog().name().replace("'", "''") + "'";

            SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
            Date birthDate = this.faker.date().birthday(3, 6);
            String formatedDate = "'" + dt.format(birthDate) + "'";

            Integer petType = this.random.nextInt(7) + 1;

            if (petType <= 0 || petType > 7) {
                throw new IllegalArgumentException("Pet type id out of range");
            }

            Integer ownerId = OWNER_USERNAME_OFFSET + i;
            if (i != this.users - 1) {
                csv.writeRecord("(" + petId, petName, formatedDate,
                        petType.toString(), ownerId.toString() + ")", "");
            } else {
                csv.writeRecord("(" + (PET_ID_OFFSET + i), petName, formatedDate,
                        petType.toString(), ownerId.toString() + ");");
            }
        }
    }

    private void generateVisitsSQL(CsvWriter csv) {
        csv.writeRecord("INSERT INTO `visits` (id, pet_id, vet_id, visit_date_time, description) VALUES");
        for (int i = 0; i < this.users; i++) {
            Integer visitId = VISIT_ID_OFFSET + i;
            Integer petId = PET_ID_OFFSET + i;

            Integer chosenIndex = this.random.nextInt(2);
            Integer vetId = CLINICS_VETS.get(this.clinicIds.get(i)).get(chosenIndex);

            // 2022-01-01 00:00:00
            Date start = new Date(1640995200000L);
            Date end = new Date();
            SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Date birthDate = this.faker.date().between(start, end);
            String formatedDate = "'" + dt.format(birthDate) + "'";

            String description = "'rabbies shot'";

            if (i != this.users - 1) {
                csv.writeRecord("(" + visitId, petId.toString(), vetId.toString(), formatedDate,
                        description + ")", "");
            } else {
                csv.writeRecord("(" + visitId, petId.toString(), vetId.toString(), formatedDate,
                        description + ");");
            }
        }
    }

    private void generateConsultationsSQL(CsvWriter csv) {
        csv.writeRecord(
                "INSERT INTO `consultations` (id, owner_id, pet_id, is_clinic_comment, title, status, creation_date) VALUES");
        for (int i = 0; i < this.users; i++) {

            if (this.clinicIds.get(i) != PLATINUM) {
                continue;
            }

            Integer consultationId = CONSULTATION_ID_OFFSET + i;
            Integer ownerId = OWNER_USERNAME_OFFSET + i;
            Integer petId = PET_ID_OFFSET + i;

            Integer isClinicComment = 0;
            String title = "'Lorem ipsum'";
            String status = "'" + CONSULTATION_STATUS.get(this.random.nextInt(3)) + "'";

            // 2022-01-01 00:00:00
            Date start = new Date(1640995200000L);
            Date end = new Date();
            SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Date creationDate = this.faker.date().between(start, end);
            String formatedDate = "'" + dt.format(creationDate) + "'";

            if (i != this.users - 1) {
                csv.writeRecord("(" + consultationId, ownerId.toString(), petId.toString(), isClinicComment.toString(),
                        title, status, formatedDate + ")", "");
            } else {
                csv.writeRecord("(" + consultationId, ownerId.toString(), petId.toString(), isClinicComment.toString(),
                        title, status, formatedDate + ");");
            }
        }
    }

    public void generateFiles(String fileName) {

        String path = CSV_BASE_PATH + "/pricing/" + fileName + ".sql";

        File file = new File(path);

        try {
            if (file.createNewFile()) {
                System.out.println("Se ha creado el fichero " + path);
            } else {
                System.out.println("El archivo " + path + " ya existe, se sobreescribirán los registros");
            }
        } catch (IOException err) {
            System.err.println(err);
        }

        try (CsvWriter csv = CsvWriter.builder().build(new FileWriter(file))) {

            generateAppUsersSQL(csv);
            generateOwnersSQL(csv);
            generatePetsSQL(csv);
            generateVisitsSQL(csv);
            generateConsultationsSQL(csv);

        } catch (IOException err) {
            System.err.println(err);
        }

        generateGatlingCSV("random");

    }

}
