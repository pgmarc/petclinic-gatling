package petclinic;

public final class PerfMetric {

    private final Long timestamp;

    private final Double cpuUsage;

    public PerfMetric(Long timestamp, Double cpuUsage) {
        this.timestamp = timestamp;
        this.cpuUsage = cpuUsage;

    }

    public Long getTimestamp() {
        return this.timestamp;
    }

    public Double getCpuUsage() {
        return this.cpuUsage;
    }

    @Override
    public String toString() {
        return "[" + "timestamp=" + this.timestamp + ", " + "cpuUsage=" + this.cpuUsage + "]";
    }

}
