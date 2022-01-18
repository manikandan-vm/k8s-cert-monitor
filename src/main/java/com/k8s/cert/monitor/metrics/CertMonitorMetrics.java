package com.k8s.cert.monitor.metrics;

import com.k8s.cert.monitor.service.CertMonitorService;
import io.fabric8.certmanager.api.model.v1.Certificate;
import io.fabric8.kubernetes.api.model.Duration;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.Metrics;
import io.micrometer.core.instrument.Tags;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class CertMonitorMetrics {

    private static final Logger logger = Logger.getLogger(CertMonitorMetrics.class.getSimpleName());

    @Autowired
    private CertMonitorService certMonitorService;

    public void publishCertMetrics(List<Certificate> certificateList) {

        for (Certificate certificate : certificateList) {
            Instant expiryTime = Instant.parse(certificate.getStatus().getNotAfter());
            Long certExpiry = ChronoUnit.SECONDS.between(Instant.now(), expiryTime);

            Tags tags = Tags.of("cert_name", certificate.getMetadata().getName(), "cert_expiry_in_seconds", certExpiry.toString(), "cert_namespace", certificate.getMetadata().getNamespace());
            Counter.builder("cert_details")
                    .tags(tags)
                    .register(Metrics.globalRegistry).increment();
        }
    }


    @Scheduled(fixedRate = 60000)
    public void certMetricsTrigger() {
        List<Certificate> certificateList = certMonitorService.listAllCertsInAnyNamespace();
        try {
            logger.info("Scheduled scrapping for cert expiry details across namespaces.");
            System.out.println("mani");
            publishCertMetrics(certificateList);
        } catch (Exception e) {
            logger.log(Level.SEVERE, String.format("Error while publishing cert metrics."), e);
        }
    }
}
