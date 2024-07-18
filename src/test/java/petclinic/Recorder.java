package petclinic;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;
import java.util.stream.Stream;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.siegmar.fastcsv.writer.CsvWriter;

public class Recorder implements Runnable {

    private static final String CSV_BASE_PATH = "metrics";

    private boolean stopRecording = false;
    private final Integer id;
    private final Integer recordRateMilis;
    private final String filename;
    private final String apiUrl;
    private final HttpClient client;
    private final List<PerfMetric> metrics;
    private final ObjectMapper objectMapper;

    public Recorder(String apiUrl, String filename, Integer id, Integer recordRateMilis) {
        this.id = id;
        this.recordRateMilis = recordRateMilis;
        this.filename = filename;
        this.apiUrl = apiUrl;
        this.client = HttpClient.newHttpClient();
        this.metrics = new LinkedList<>();
        this.objectMapper = new ObjectMapper();

    }

    private String getFullName() {
        return this.filename + "-" + id + ".csv";
    }

    public synchronized void stopRecording() {
        this.stopRecording = true;

    }

    private synchronized boolean keepExectuing() {
        return this.stopRecording == false;
    }

    public void printHeader() {
        System.out.println("Empezando grabación de las métricas");
    }

    public void printFooter() {
        System.out.println("Se ha terminado de grabar las métricas");
    }

    @Override
    public void run() {
        while (keepExectuing()) {
            try {
                Optional<PerfMetric> metric = fetchMetricAPI();
                if (metric.isPresent()) {
                    metrics.add(metric.get());
                }
                Thread.sleep(this.recordRateMilis);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        dumpMetricsToCSV(this.metrics);
    }

    private void dumpMetricsToCSV(List<PerfMetric> metrics) {

        File file = new File(CSV_BASE_PATH + "/" + this.getFullName());

        try {
            if (file.createNewFile()) {
                System.out.println("Se ha creado el fichero " + this.getFullName());
            } else {
                System.out.println("El archivo " + this.getFullName() + " ya existe, se sobreescribirán las métricas");
            }
        } catch (IOException err) {
            System.err.println(err);
        }

        System.out.println("Guardando métricas en la ruta " + CSV_BASE_PATH + "/" + this.getFullName());

        try (CsvWriter csv = CsvWriter.builder().build(new FileWriter(file))) {
            csv.writeRecord("timestamp", "cpu_usage");
            for (PerfMetric metric : metrics) {
                csv.writeRecord(metric.getTimestamp().toString(), metric.getCpuUsage().toString());
            }
        } catch (IOException err) {
            System.err.println(err);
        }

    }

    private Optional<PerfMetric> fetchMetricAPI() throws InterruptedException {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(this.apiUrl)).GET().build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            JsonNode node = objectMapper.readTree(response.body());
            PerfMetric metric = new PerfMetric(node.get("timestamp").asLong(), node.get("cpuUsage").asDouble());
            return Optional.of(metric);

        } catch (IOException err) {
            err.printStackTrace();
            return Optional.empty();
        }

    }

    public static Iterator<Map<String, Object>> generateUsers(Integer users, String scenarioName) {
        return Stream.generate((Supplier<Map<String, Object>>) () -> {
            UUID uuid = UUID.randomUUID();
            return Map.of("username", uuid, "petName", uuid + "-" + scenarioName);
        }).iterator();
    }

}
