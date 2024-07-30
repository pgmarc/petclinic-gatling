# Dependencias

A parte de gatling en el pom hay 3 dependencias más:

- De JSON a Java con [Jackson](https://github.com/FasterXML/jackson)
- Escribir CSVs con [FastCSV](https://github.com/osiegmar/FastCSV)
- Generar datos aleatorios [JavaFaker](https://github.com/DiUS/java-fake)

# Sobre maven

Utilice el binario de maven del repositorio es decir `mvnw` o `mvnw.cmd`

Para especificar argumentos de líneas de comandos en maven utiliza como
prefijo `-D`.

## Simulaciones de monitorización individual

¿Por qué hacer un script de grabación de métricas?

El primer programa que hice para grabar métricas estaba desarrollado en python
y cada vez que quería grabar métricas tenía que ejecutar el script manualmente.
Esto tiene unas cuantas implicaciones:

- Tengo que saber cuando empezar a grabar
- Tengo que saber cuando terminar de grabar
- Requiere que la persona esté atenta al ejecutar
  las simulación

Con esto en mente, desarrollé el mismo script en java utilizando la funcionalidad de
Gatling que permite ejecutar código java antes de empezar la simulación y después
de terminar la simulación, los hooks `before` y `after`. De esta manera antes
de que empiece la simulación ejecuto un hilo en paralelo mientras se ejecutan
las simulaciónes y grabo las métricas, una vez terminada la simulación
cierro el hilo.

Para recolectar métricas del consumo de cpu, memoria, etc, se ha desarrollado
en el contexto del proyecto un script que toma "fotos" o "snapshots" de instantes
de tiempo de la aplicación de `petclinic`, `Recorder.java`. Los datos se guardan
en la carpeta raiz del repositorio `metrics`.

Estas fotos se toman llamando a una API `/api/v1/metrics` desarrollada exclusivamente
para la monitorización de `petclinic`. El repositorio se encuentra en este
[enlace](https://github.com/pgmarc/petclinic-experimentation).

El constructor acepta los siguientes parámetros:

```java
public Recorder(String apiUrl, String filename, Integer id, Integer recordRateMilis);
```

La url de la API desplegada del repositorio anterior
El nombre del fichero que se va a guardar las métricas capturadas
El número del experimento realizado
La duración en milisegundos en el que va a tomar una foto aproximadamente

¿Si quiero hacer una simulación de Gatling como puedo utilizar el Recorder?

Si nos fijamos en el código fuente de la simulación `PetsFeatureSimulation`:

```java
package petclinic.basic;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;
import petclinic.Recorder;

public class PetsFeatureSimulation extends Simulation {

    private static final String URL = System.getProperty("url", "http://localhost:8080");

    private static final Integer simulationId = Integer.getInteger("id", 1);

    private static final String ownerType = System.getProperty("type", "basic").toLowerCase();

    HttpProtocolBuilder httpProtocol = http.baseUrl(URL).disableCaching();

    // La clase Recorder implementa la interfaz Runnable
    Recorder recorder = new Recorder(URL + "/api/v1/metrics", this.getClass().getName() + "-" + ownerType.toLowerCase(),
            simulationId, 100);

    // Crea un hilo por separado para grabar las métricas
    Thread hilo = new Thread(recorder);

    /* Código de tu simulación
     * .....
     * .....
    */

    // Este trozo de código se ejecuta antes de ejecutar
    // la simulación por lo que puede arrancar el hilo en este momento
    @Override
    public void before() {
        recorder.printHeader();
        hilo.start();

    }

    // Este trozo de código se ejecuta después de ejecutar
    // la simulación por lo que puede parar el hilo en este momento
    @Override
    public void after() {
        recorder.stopRecording();
        recorder.printFooter();
    }

    {
        setUp(basicOwnerChecksPets.injectOpen(atOnceUsers(1)).protocols(httpProtocol));
    }

}
```

### Basic

#### PetsConcurrentSimulation

Depende del archivo `basic/owners-con.csv`

Argumentos:

- url: URL en el que está ubicado petclinic
- users: Número de usuarios concurrentes

Linux / MacOS:

```bash
./mvnw gatling:test -Dgatling.simulationClass=petclinic.basic.PetsConcurrentSimulation -Durl=http://192.168.0.21 -Dusers=100
```

Windows:

```bash
mvnw.cmd gatling:test -Dgatling.simulationClass=petclinic.basic.PetsConcurrentSimulation -Durl=http://192.168.0.21 -Dusers=100
```

#### PetsRamSimulation

Depende del archivo `basic/owners-ramp.csv`

Argumentos:

- users: Número de usuarios concurrentes
- duration: Número de segundos que va a durar la rampa
- url: URL en el que está ubicado petclinic

Linux / MacOS:

```bash
./mvnw gatling:test -Dgatling.simulationClass=petclinic.basic.PetsRampSimulation -Durl=http://192.168.0.21 -Dusers=100
```

Windows:

```bash
mvnw.cmd gatling:test -Dgatling.simulationClass=petclinic.basic.PetsRampSimulation -Durl=http://192.168.0.21 -Dusers=100
```

#### PetsFeatureSimulation

Argumentos:

- url: URL en el que está ubicado petclinic
- type [basic | gold | platinum]: plan de precio del propietario de mascota
- id: Número de identificación de la simulación. Este parámetro sirve por
  si quiere ejecutar múltiples veces este script y generar distintas métricas.

Linux / MacOS:

```bash
./mvnw gatling:test -Dgatling.simulationClass=petclinic.basic.PetsFeatureSimulation -Durl=http://192.168.0.21 -Did=1
```

On Windows:

```bash
mvnw.cmd gatling:test -Dgatling.simulationClass=petclinic.basic.PetsFeatureSimulation -Durl=http://192.168.0.21 -Did=1
```

### Gold

#### CalendarFeatureSimulation

Argumentos:

- url: URL en el que está ubicado petclinic
- type [gold | platinum]: plan de precio del propietario de mascota
- id: Número de identificación de la simulación. Este parámetro sirve por
  si quiere ejecutar múltiples veces este script y generar distintas métricas.

Linux / MacOS:

```bash
./mvnw gatling:test -Dgatling.simulationClass=petclinic.gold.CalendarFeatureSimulation -Durl=http://192.168.0.21 -Dtype=gold -Did=1
```

On Windows:

```bash
mvnw.cmd gatling:test -Dgatling.simulationClass=petclinic.gold.CalendarFeatureSimulation -Durl=http://192.168.0.21 -Dtype=gold -Did=1
```

#### PetsConcurrentSimulation

Depende del archivo `gold/owners-con.csv`

Argumentos:

- url: URL en el que está ubicado petclinic
- users: Número de usuarios concurrentes

Linux / MacOS:

```bash
./mvnw gatling:test -Dgatling.simulationClass=petclinic.gold.PetsConcurrentSimulation -Durl=http://192.168.0.21 -Dusers=100
```

Windows:

```bash
mvnw.cmd gatling:test -Dgatling.simulationClass=petclinic.gold.PetsConcurrentSimulation -Durl=http://192.168.0.21 -Dusers=100
```

#### PetsRampSimulation

Depende del archivo `gold/owners-ramp.csv`

Argumentos:

- url: URL en el que está ubicado petclinic
- users: Número de usuarios concurrentes
- duration: Número de segundos que dura la rampa

Linux / MacOS:

```bash
./mvnw gatling:test -Dgatling.simulationClass=petclinic.gold.PetsRampSimulation -Durl=http://192.168.0.21 -Dusers=100
```

Windows:

```bash
mvnw.cmd gatling:test -Dgatling.simulationClass=petclinic.gold.PetsRampSimulation -Durl=http://192.168.0.21 -Dusers=100
```

### Platinum

#### ConsultationsFeatureSimulation

Argumentos:

- url: URL en el que está ubicado petclinic
- id: Número de identificación de la simulación. Este parámetro sirve por
  si quiere ejecutar múltiples veces este script y generar distintas métricas.

On Linux / MacOS:

```bash
./mvnw gatling:test -Dgatling.simulationClass=petclinic.platinum.ConsultationsFeatureSimulation -Durl=192.168.0.21 -Did=1
```

Windows:

```bash
mvnw.cmd gatling:test -Dgatling.simulationClass=petclinic.platinum.ConsultationsFeatureSimulation -Durl=192.168.0.21 -Did=1
```

#### PetsConcurrentSimulation

Depende del archivo `platinum/owners-con.csv`

Argumentos:

- url: URL en el que está ubicado petclinic
- id: Número de identificación de la simulación. Este parámetro sirve por
  si quiere ejecutar múltiples veces este script y generar distintas métricas.

Linux / MacOS:

```bash
./mvnw gatling:test -Dgatling.simulationClass=petclinic.platinum.PetsConcurrentSimulation -Durl=http://192.168.0.21 -Dusers=100
```

On Windows:

```bash
mvnw.cmd gatling:test -Dgatling.simulationClass=petclinic.platinum.PetsConcurrentSimulation -Durl=http://192.168.0.21 -Dusers=100

```

#### PetsRampSimulation

Depende del archivo `platinum/owners-ramp.csv`

- url: URL en el que está ubicado petclinic
- users: Número de usuarios concurrentes
- duration: Número de segundos que dura la rampa

Linux / MacOS:

```bash
./mvnw gatling:test -Dgatling.simulationClass=petclinic.platinum.PetsRampSimulation -Durl=http://192.168.0.21 -Dusers=100 -Dduration=30
```

Windows:

```bash
mvnw.cmd gatling:test -Dgatling.simulationClass=petclinic.platinum.PetsRampSimulation -Durl=http://192.168.0.21 -Dusers=100 -Dduration=30
```

### Pricing

#### PetsConcurrentSimulation

Depende de 3 archivos:

- pricing/basic-owners-con-final.csv
- pricing/gold-owners-con-final.csv
- pricing/platinum-owners-con-final.csv

Argumentos:

- url: URL en el que está ubicado petclinic
- basic: Número de usuarios de tipo BASIC concurrentes
- gold: Número de usuarios de tipo GOLD concurrentes
- platinum: Número de usuarios de tipo PLATINUM concurrentes

On Linux / MacOS:

```bash
./mvnw gatling:test -Dgatling.simulationClass=petclinic.pricing.PetsConcurrentSimulation -Durl=http://192.168.0.21 -Dbasic=10000 -Dgold=5000 -Dplatinum=1000
```

On Windows:

```bash
mvnw.cmd gatling:test -Dgatling.simulationClass=petclinic.pricing.PetsConcurrentSimulation -Durl=http://192.168.0.21 -Dbasic=10000 -Dgold=5000 -Dplatinum=1000
```

#### PetsRampSimulation

Depende de 3 archivos:

- pricing/basic-owners-ramp-final.csv
- pricing/gold-owners-ramp-final.csv
- pricing/platinum-owners-ramp-final.csv

Argumentos:

- url: URL en el que está ubicado petclinic
- basic: Número de usuarios de tipo BASIC concurrentes
- gold: Número de usuarios de tipo GOLD concurrentes
- platinum: Número de usuarios de tipo PLATINUM concurrentes
- basicDur: Duración en segundos de la rampa de los usuarios tipo BASIC
- goldDur: Duración en segundos de la rampa de los usuarios tipo GOLD
- platinumDur: Duración en segundos de la rampa de los usuarios tipo PLATINUM

On Linux / MacOS:

```bash
./mvnw gatling:test -Dgatling.simulationClass=petclinic.pricing.PetsRampSimulation -Durl=http://192.168.0.21 -Dbasic=10000 -DbasicDur=30 -Dgold=5000 -DgoldDur=30 -Dplatinum=1000 -DplatinumDur=30
```

On Windows:

```bash
mvnw.cmd gatling:test -Dgatling.simulationClass=petclinic.pricing.PetsRampSimulation -Dbasic=10000 -DbasicDur=30 -Dgold=5000 -DgoldDur=30 -Dplatinum=1000 -DplatinumDur=30
```

## Simulación de monitorización colectiva

### TLDR

Para ejecutar la simulación de monitorización colectiva mediante línea de
comandos necesita pasar como argumento la url que quiere probar. Por defecto
el parámetro `url` es opcional y por defecto tiene el
valor de `http://localhost:8080`.

Con el argumento `gatling.simulationClass` puede indicar que simulación
ejecutar, en este caso especificamos la ruta de paquetes de java hasta llegar a
la simulación de `RandomUsers.java`, `petclinic.pricing.RandomUsers`.

Linux:

```bash
./mvnw gatling:test -Dgatling.simulationClass=petclinic.pricing.RandomUsers -Durl=http://192.168.0.21
```

Windows:

```bash
mvnw.cmd gatling:test -Dgatling.simulationClass=petclinic.pricing.RandomUsers -Durl=http://192.168.0.21
```

### Detalles

El script de gatling que se utiliza para hacer la monitorización colectiva
es `RandomUsers.java`. Utiliza el archivo `random.csv` para cargar usuarios
virtuales.

El archivo tienes tres columnas:

- El nombre de usuario, `username`
- El plan de precios del usuario `pricing`
- El número de veces que va a consultar la API `times`

Sin entrar en mucho nivel de detalle laa simulacion hace lo siguiente:

- Hace login en petclinic
- Dependiendo del plan de precio que tenga el propietario de mascotas
  (owner) llama:
  - Si es de tipo `BASIC` consulta a las mascotas que tiene
  - Si es de tipo `GOLD` consulta a las visitas de sus mascotas
  - Si es de tipo `PLATINUM` consulta las consultas que ha hecho a la
    clínica el propietario de mascotas
- Estas llamadas las repite el usuario X veces dependinedo del número
  que se haya puesto en el archivo `random.csv` en la columna times

# Generación de datos de prueba

Para generar datos de prueba para la fase de monitorización colectiva se ha
desarrollado un script `DataGenerator.java` que genera dos archivos en
la ruta `src/test/resources/pricing`:

- Una archivo `random.csv` que sirve de
  [feeder](https://docs.gatling.io/reference/script/core/session/feeders/) para el script RandomUsers.java
- Un archivo `petclinic-data.sql` que contiene datos ficticios de `petclinic`
  para popular la BBDD previamente antes de ejecutar las simulación colectiva.

## VSCode

Para ejecutar el generador haga click derecho en el archivo `Main.java` > `Run Java`.

El constructor `DataGenerator` acepta dos parámetros:

- users [Integer]: Número de usuarios a generar
- random [Random]: Objecto de la clase `java.util.random` en el
  que puede establecer una semilla

Siguiendo el patrón de petclinic de los usuarios de petclinic se han llamado
a los propietarios de mascotas `ownerXXX` donde las `XXX` es un número.

## Consideraciones previas

La implementación del generador de datos está **ALTAMENTE ACOPLADO** a los datos
de prueba de `petclinic` de
[PSG2](https://github.com/gii-is-psg2/react-petclinic/blob/main/src/main/resources/data.sql)
por lo que **NO SE RECOMIENDA** alterar el archivo `data.sql` (id, owner_id, etc...).

Antes de usar el archivo `data.sql` para poblar la BBDD **TIENE QUE LIMPIAR**
el archivo. Por ejemplo:

Tiene que eliminar las dobles comillas `"` de todos los comandos SQL que
encuentre en el archivo:

Esto no es SQL válido

```txt
"INSERT INTO `appusers` (id, username, password, authority) VALUES"
(20,'owner20','$2a$10$DaS6KIEfF5CRTFrxIoGc7emY3BpZZ0.fVjwA3NiJ.BjpGNmocaS3e',3),
(21,'owner21','$2a$10$DaS6KIEfF5CRTFrxIoGc7emY3BpZZ0.fVjwA3NiJ.BjpGNmocaS3e',3),
...
```

Esto es SQL válido

```sql
INSERT INTO `appusers` (id, username, password, authority) VALUES
(20,'owner20','$2a$10$DaS6KIEfF5CRTFrxIoGc7emY3BpZZ0.fVjwA3NiJ.BjpGNmocaS3e',3),
(21,'owner21','$2a$10$DaS6KIEfF5CRTFrxIoGc7emY3BpZZ0.fVjwA3NiJ.BjpGNmocaS3e',3),
-- Más datos ...
```

- Los comandos SQL terminan con un `;`. Al generar los datos la tabla
  `consultations` no tiene `;` añádela al final y quite la coma sobrante
  si hay.

```sql
INSERT INTO `consultations` (id, owner_id, pet_id, is_clinic_comment, title, status, creation_date) VALUES
(12,17,20,0,'Lorem ipsum','PENDING','2023-01-24 12:46:52'), -- Falta ;
```

```sql
INSERT INTO `consultations` (id, owner_id, pet_id, is_clinic_comment, title, status, creation_date) VALUES
(12,17,20,0,'Lorem ipsum','PENDING','2023-01-24 12:46:52'); -- Esto está bien
```

¿Por qué has hecho un script y no has utilizado alguna IA para generar datos?

- Se pierde tiempo en entrenar a una IA para que genere datos ficticios
- Tienes que describir la lógica y las restricciones de petclinic a la IA
- Es más mantenible desarrollar un script que genere datos ficticios
- Para facilitar la reprducción de los experimentos se ha establecido una
  semilla
- El generador de datos utiliza la librería [Faker](https://github.com/DiUS/java-faker)
  que genera datos dependiendo del contexto en el que estés (ciudades, direcciones
  nombres, apellidos...)
