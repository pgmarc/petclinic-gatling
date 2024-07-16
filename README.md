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

### Basic

On Linux / MacOS:

```console
./mvnw gatling:test -Dgatling.simulationClass=petclinic.basic.PetsFeatureRampUsersSimulation -DrampUsers=100 -DrampDuration=30
./mvnw gatling:test -Dgatling.simulationClass=petclinic.basic.PetsFeatureRampUsersSimulation -DrampUsers=1000 -DrampDuration=30
./mvnw gatling:test -Dgatling.simulationClass=petclinic.basic.PetsConcurrentSimulation -Dusers=100
./mvnw gatling:test -Dgatling.simulationClass=petclinic.basic.PetsConcurrentSimulation -Dusers=1000
./mvnw gatling:test -Dgatling.simulationClass=petclinic.basic.PetsFeature -Durl=http://192.168.0.21 -Did=1
```

On Windows:

```console
mvnw.cmd gatling:test -Dgatling.simulationClass=petclinic.basic.PetsRampSimulation -Dusers=100 -Dduration=30
mvnw.cmd gatling:test -Dgatling.simulationClass=petclinic.basic.PetsRampSimulation -Dusers=1000 -Dduration=30
mvnw.cmd gatling:test -Dgatling.simulationClass=petclinic.basic.PetsConcurrentSimulation -Dusers=100
mvnw.cmd gatling:test -Dgatling.simulationClass=petclinic.basic.PetsConcurrentSimulation -Dusers=1000
mvnw.cmd gatling:test -Dgatling.simulationClass=petclinic.basic.PetsFeature -Durl=http://192.168.0.21 -Did=1
```

### Gold

On Linux / MacOS:

```console
./mvnw gatling:test -Dgatling.simulationClass=petclinic.gold.PetsFeatureRampUsersSimulation -DrampUsers=100 -DrampDuration=30
./mvnw gatling:test -Dgatling.simulationClass=petclinic.gold.PetsFeatureRampUsersSimulation -DrampUsers=1000 -DrampDuration=30
./mvnw gatling:test -Dgatling.simulationClass=petclinic.gold.PetsConcurrentSimulation -Dusers=100
./mvnw gatling:test -Dgatling.simulationClass=petclinic.gold.PetsConcurrentSimulation -Dusers=1000
./mvnw gatling:test -Dgatling.simulationClass=petclinic.gold.PetsOneUserActive
```

On Windows:

```console
mvnw.cmd gatling:test -Dgatling.simulationClass=petclinic.gold.PetsRampSimulation -Dusers=100 -Dduration=30
mvnw.cmd gatling:test -Dgatling.simulationClass=petclinic.gold.PetsRampSimulation -Dusers=1000 -Dduration=30
mvnw.cmd gatling:test -Dgatling.simulationClass=petclinic.gold.PetsConcurrentSimulation -Dusers=100
mvnw.cmd gatling:test -Dgatling.simulationClass=petclinic.gold.PetsConcurrentSimulation -Dusers=1000
mvnw.cmd gatling:test -Dgatling.simulationClass=petclinic.gold.PetsOneUserActive
```

### Platinum

On Linux / MacOS:

```console
./mvnw gatling:test -Dgatling.simulationClass=petclinic.platinum.PetsFeatureRampUsersSimulation -DrampUsers=100 -DrampDuration=30
./mvnw gatling:test -Dgatling.simulationClass=petclinic.platinum.PetsFeatureRampUsersSimulation -DrampUsers=1000 -DrampDuration=30
./mvnw gatling:test -Dgatling.simulationClass=petclinic.platinum.PetsConcurrentSimulation -Dusers=100
./mvnw gatling:test -Dgatling.simulationClass=petclinic.platinum.PetsConcurrentSimulation -Dusers=1000
./mvnw gatling:test -Dgatling.simulationClass=petclinic.platinum.PetsOneUserActive
```

On Windows:

```console
mvnw.cmd gatling:test -Dgatling.simulationClass=petclinic.platinum.PetsRampSimulation -Dusers=100 -Dduration=30
mvnw.cmd gatling:test -Dgatling.simulationClass=petclinic.platinum.PetsRampSimulation -Dusers=1000 -Dduration=30
mvnw.cmd gatling:test -Dgatling.simulationClass=petclinic.platinum.PetsConcurrentSimulation -Dusers=100
mvnw.cmd gatling:test -Dgatling.simulationClass=petclinic.platinum.PetsConcurrentSimulation -Dusers=1000
mvnw.cmd gatling:test -Dgatling.simulationClass=petclinic.platinum.PetsOneUserActive
```

### Pricing

On Linux / MacOS:

```console
./mvnw gatling:test -Dgatling.simulationClass=petclinic.pricing.PetsRampSimulation -Dbasic=10000 -DbasicDur=30 -Dgold=5000 -DgoldDur=30 -Dplatinum=1000 -DplatinumDur=30
./mvnw gatling:test -Dgatling.simulationClass=petclinic.pricing.PetsConcurrentSimulation -Dbasic=10000 -Dgold=5000 -Dplatinum=1000
```

On Windows:

````console
mvnw.cmd gatling:test -Dgatling.simulationClass=petclinic.pricing.PetsRampSimulation -Dbasic=10000 -DbasicDur=30 -Dgold=5000 -DgoldDur=30 -Dplatinum=1000 -DplatinumDur=30
mvnw.cmd gatling:test -Dgatling.simulationClass=petclinic.pricing.PetsConcurrentSimulation -Dbasic=10000 -Dgold=5000 -Dplatinum=1000

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
````

## Pricing

Description of petclinic pricing

### Basic Users

### Gold Users

Gold users can check their pets visits on a calendar at a glance.

This feature calls three endpoints:

1. GET api/v1/auth/validate?token=#{authToken}
2. GET api/v1/visits
3. GET api/v1/plan (Dead code)

### Platinum Users

Consultations

This feature calls three endpoints:

1. GET api/v1/auth/validate?token=#{authToken}
2. GET api/v1/consultations
3. GET api/v1/plan (Dead code)
