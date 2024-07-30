package petclinic;

import java.util.Random;

public class Main {

    public static void main(String[] args) {

        DataGenerator dg = new DataGenerator(1000, new Random(42));
        dg.generateFiles("petclinic-data");

    }

}
