# Detalles sobre el grabador de métricas

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

# Ej 1

Funciona

```java
jsonPath("$").ofList().saveAs("type")
```

```json
"type": #{type.size()}
```

# Ej 2

No funciona

```java
jsonPath("$").count().saveAs("type")
```

```json
"type": #{type}
```

# Ej 3

No funciona

```json
"type": #{type.size()-1}
```

# Ej 4

No funciona

El count parece que ve la lista en si y ya está

```java
jsonPath("$").ofList().count().saveAs("type")
```

```json
"type": #{type.size()}
```

# Ej 5

Funciona devuelve un objeto java

```java
jsonPath("$").ofList().saveAs("type")
```

```json
"type": #{type(1)}
```

# Ej 6

Funciona devuelve un String sin codificar json

```java
jsonPath("$").ofList().saveAs("type")
```

```json
"type": #{type(1).name}
```

# Ej 7

Funciona

```json
{
  "id": null,
  "name": "#{petName}",
  "birthDate": "2024-06-16",
  "type": #{type(1).jsonStringify()},
  "owner": {}
}

```
