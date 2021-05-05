package io.wedeploy.example;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class DataEntries {
    private String device;
    private LocalDateTime timestamp;
    private BigDecimal temp;
    private BigDecimal humidity;

    public DataEntries() {
    }

    public DataEntries(String device, BigDecimal temp, BigDecimal humidity) {
        this.device =device;
        this.temp = temp;
        this.humidity = humidity;
        this.timestamp = LocalDateTime.now();
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public BigDecimal getTemp() {
        return temp;
    }

    public void setTemp(BigDecimal temp) {
        this.temp = temp;
    }

    public BigDecimal getHumidity() {
        return humidity;
    }

    public void setHumidity(BigDecimal humidity) {
        this.humidity = humidity;
    }

    @Override
    public String toString() {
        return "{" +
                "device:" + device  +
                ", timestamp:" + timestamp +
                ", temp:" + temp +
                ", humidity:" + humidity +
                '}';
    }
}
