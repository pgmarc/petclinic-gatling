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

import com.fasterxml.jackson.core.JsonProcessingException;
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
            csv.writeRecord("timestamp_millis", "cpu_load", "process_cpu_load", "process_cpu_time_nanosecs",
                    "total_memory_os_bytes",
                    "free_memory_os_bytes", "jvm_total_memory_bytes", "jvm_free_memory_bytes");
            for (PerfMetric metric : metrics) {
                csv.writeRecord(metric.getTimestamp().toString(), metric.getCpuLoad().toString(),
                        metric.getProcessCpuLoad().toString(), metric.getProcessCpuTime().toString(),
                        metric.getTotalMemoryOs().toString(),
                        metric.getFreeMemoryOs().toString(), metric.getJvmTotalMemory().toString(),
                        metric.getJvmFreeMemory().toString());
            }
        } catch (IOException err) {
            System.err.println(err);
        }

    }

    private Optional<PerfMetric> fetchMetricAPI() throws InterruptedException {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(this.apiUrl)).GET().build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            return parseJson(response.body());

        } catch (IOException err) {
            err.printStackTrace();
            return Optional.empty();
        }

    }

    private Optional<PerfMetric> parseJson(String json) {
        try {
            JsonNode node = this.objectMapper.readTree(json);
            long timestamp = node.get("timestamp").asLong();
            long totalMemoryJVM = node.get("jvm").get("totalMemory").asLong();
            long freeMemoryJVM = node.get("jvm").get("freeMemory").asLong();

            double cpuLoad = node.get("osBean").get("cpuLoad").asDouble();
            double proccessCpuLoad = node.get("osBean").get("processCpuLoad").asDouble();
            long processCpuTime = node.get("osBean").get("processCpuTime").asLong();
            long totalMemoryOs = node.get("osBean").get("totalMemoryOs").asLong();
            long freeMemoryOs = node.get("osBean").get("freeMemoryOs").asLong();

            return Optional.of(new PerfMetric(timestamp, cpuLoad, proccessCpuLoad, processCpuTime, totalMemoryOs,
                    freeMemoryOs, totalMemoryJVM, freeMemoryJVM));

        } catch (JsonProcessingException e) {
            e.printStackTrace();
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
