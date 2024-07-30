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

## Pricing

Description of petclinic pricing

### Basic Users

1. GET api/v1/pets?userId=#{authToken}

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

## Simulation details

Why simulations are not run with a seed

Gatling DSL does not provide any kind of method to set a seed in the simulations so every time
the simulation is run we get random results.

```sql
SELECT appusers.id, owners.user_id, username, first_name, clinic FROM appusers JOIN owners WHERE appusers.id=owners.user_id;
```

```sql
SELECT plan, COUNT(*) FROM appusers JOIN owners JOIN clinics
WHERE appusers.id=owners.user_id AND owners.clinic=clinics.id GROUP BY clinic;
```
