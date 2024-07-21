package petclinic;

public final class PerfMetric {

    private final Long timestamp;

    private final Double cpuLoad;

    private final Double processCpuLoad;

    private final Long processCpuTime;

    private final Long totalMemoryOs;

    private final Long freeMemoryOs;

    private final Long jvmTotalMemory;

    private final long jvmFreeMemory;

    public PerfMetric(Long timestamp, Double cpuLoad, double processCpuLoad, long processCpuTime, long totalMemoryOs,
            long freeMemoryOs, long jvmTotalMemory, long jvmFreeMemory) {
        this.timestamp = timestamp;
        this.cpuLoad = cpuLoad;
        this.processCpuLoad = processCpuLoad;
        this.processCpuTime = processCpuTime;
        this.totalMemoryOs = totalMemoryOs;
        this.freeMemoryOs = freeMemoryOs;
        this.jvmTotalMemory = jvmTotalMemory;
        this.jvmFreeMemory = jvmFreeMemory;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public Double getCpuLoad() {
        return cpuLoad;
    }

    public Double getProcessCpuLoad() {
        return processCpuLoad;
    }

    public Long getProcessCpuTime() {
        return processCpuTime;
    }

    public Long getTotalMemoryOs() {
        return totalMemoryOs;
    }

    public Long getFreeMemoryOs() {
        return freeMemoryOs;
    }

    public Long getJvmTotalMemory() {
        return jvmTotalMemory;
    }

    public Long getJvmFreeMemory() {
        return jvmFreeMemory;
    }

}
