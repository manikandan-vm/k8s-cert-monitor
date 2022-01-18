package com.k8s.cert.monitor.service;

import io.fabric8.certmanager.api.model.v1.Certificate;
import io.fabric8.certmanager.api.model.v1.CertificateList;
import io.fabric8.certmanager.client.CertManagerClient;
import io.fabric8.certmanager.client.DefaultCertManagerClient;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class CertMonitorService {

    private static final Logger logger = Logger.getLogger(CertMonitorService.class.getSimpleName());

    public List<Certificate> listAllCertsByNamespace(String namespace) {
        try (CertManagerClient certManagerClient = new DefaultCertManagerClient()) {
            CertificateList certificateList = certManagerClient.v1().certificates().inNamespace(namespace).list();
            if (certificateList.getItems() != null) {
                logger.info(String.format("Found %d Certificates in %s namespace.", certificateList.getItems().size(), namespace));
                return certificateList.getItems();
            } else {
                return Collections.emptyList();
            }
        } catch (Exception e) {
            logger.severe(String.format("Error while accessing certs in %s namespace.", namespace));
            return Collections.emptyList();
        }
    }
}
