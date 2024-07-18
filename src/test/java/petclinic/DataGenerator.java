package petclinic;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

import com.github.javafaker.Faker;

import de.siegmar.fastcsv.writer.CsvWriter;

public class DataGenerator {

    private final static String CSV_BASE_PATH = "src/test/resources";

    private final static String FILE_NAME = "users.sql";

    private final static int USER_ID_OFFSET = 20;

    private final static int OWNER_USERNAME_OFFSET = 11;

    private final static String OWNER_PASSWORD = "$2a$10$DaS6KIEfF5CRTFrxIoGc7emY3BpZZ0.fVjwA3NiJ.BjpGNmocaS3e";

    private final Integer users;

    private final Faker faker;

    public DataGenerator(Integer users) {
        this.users = users;
        this.faker = new Faker(new Random(42));
    }

    private String getPathName() {
        return CSV_BASE_PATH + "/" + FILE_NAME;
    }

    public boolean checkFileExists(String path) {
        return new File(getPathName()).exists();
    }

    public void generateUsersSQLFile() {

        File file = new File(getPathName());

        try {
            if (file.createNewFile()) {
                System.out.println("Se ha creado el fichero " + getPathName());
            } else {
                System.out.println("El archivo " + getPathName() + " ya existe, se sobreescribirán los registros");
            }
        } catch (IOException err) {
            System.err.println(err);
        }

        int OWNER_AUTHORITY = 3;

        try (CsvWriter csv = CsvWriter.builder().build(new FileWriter(file))) {
            csv.writeRecord("INSERT INTO `appusers` (id, username, password, authority) VALUES");
            for (int i = 0; i < this.users; i++) {
                int userId = USER_ID_OFFSET + i;
                String username = "owner" + userId;

                csv.writeRecord("(" + userId, "'" + username + "'", "'" + OWNER_PASSWORD + "'", OWNER_AUTHORITY + ")",
                        "");
            }

            csv.writeRecord(
                    "INSERT INTO owners (id, user_id, clinic, first_name, last_name, address, city, telephone) VALUES");
            for (int i = 0; i < this.users; i++) {
                Integer CLINIC_PRICING = 3;
                Integer ownerId = OWNER_USERNAME_OFFSET + i;
                Integer userId = USER_ID_OFFSET + i;
                String firstName = "'" + faker.name().firstName().replace("'", "''") + "'";
                String lastName = "'" + faker.name().lastName().replace("'", "''") + "'";
                String address = "'" + faker.address().cityPrefix() + "'";
                String city = "'" + faker.address().cityName().replace("'", "''") + "'";
                String telephone = "'" + "111111111" + "'";

                csv.writeRecord("(" + ownerId, userId.toString(), CLINIC_PRICING.toString(), firstName, lastName,
                        address, city, telephone + ")", "");

            }
        } catch (IOException err) {
            System.err.println(err);
        }

    }

}
