package petclinic;

public class Main {

    public static void main(String[] args) {

        DataGenerator dg = new DataGenerator(1000);
        dg.generateUsersSQLFile();
    }

}
