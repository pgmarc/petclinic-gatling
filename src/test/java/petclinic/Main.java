package petclinic;

import java.util.Random;

public class Main {

    public static void main(String[] args) {

        DataGenerator dg = new DataGenerator(1000, new Random(42));
        dg.generateFiles("petclinic-data");
        dg.generateVisitTestCases("visits-use-case", "visits-use-case");
        dg.generateConsulationData("consultation-use-case", "consultation-use-case");

    }

}
