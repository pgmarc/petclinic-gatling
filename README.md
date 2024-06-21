# Petclinic Gatling testing

Before running any simulation, make sure that csv files are generated. This
is mandatory as gatling feeders will require them. If a file is absent you may
run the script `genUsers.py` to regenerate them.

## Run a test locally

On Linux / MacOS:

```console
./mvnw gatling:test
```

On Windows:

```console
mvnw.cmd gatling:test
```

## Run a single test locally

On Linux / MacOS:

```console
./mvnw gatling:test -Dgatling.simulationClass=petclinic.basic.PetsFeatureRampUsersSimulation -DrampUsers=200 -DrampDuration=30
./mvnw gatling:test -Dgatling.simulationClass=petclinic.basic.PetsConcurrentSimulation -users=50
./mvnw gatling:test -Dgatling.simulationClass=petclinic.basic.PetsOneUserActive
```

On Windows:

```console
mvnw.cmd gatling:test -Dgatling.simulationClass=petclinic.basic.PetsRampSimulation -Dusers=200 -Dduration=30
mvnw.cmd gatling:test -Dgatling.simulationClass=petclinic.basic.PetsConcurrentSimulation -users=50
mvnw.cmd gatling:test -Dgatling.simulationClass=petclinic.basic.PetsOneUserActive
```

After executing `mvn gatling:test`, results are in the folder
`target/gatling/<simulationname>-<timestamp>/index.html`

```text
.
├── src/
│   └── test/
│       ├── java/
│       │   └── petclinic/
│       │       ├── basic/
│       │       │   ├── PetsConcurrentSimulation.java
│       │       │   └── PetsRampSimulation.java
│       │       ├── gold/
│       │       │   ├── PetsConcurrentSimulation.java
│       │       │   └── PetsRampSimulation.java
│       │       ├── platinum/
│       │       │   ├── PetsConcurrentSimulation.java
│       │       │   └── PetsRampSimulation.java
│       │       └── pricing/
│       │           ├── PetsConcurrentSimulation.java
│       │           └── PetsRampSimulation.java
│       └── resources/
│           └── ...
└── target/
    └── gatling/
        └── <simulationname>-<timestamp>/
            └── index.html
```
