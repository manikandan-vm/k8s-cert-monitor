package com.k8s.cert.monitor.controller;

import com.k8s.cert.monitor.service.CertMonitorService;
import io.fabric8.certmanager.api.model.v1.Certificate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/v1/cert-monitor")
public class CertMonitorController {

    private static final Logger logger = Logger.getLogger(CertMonitorController.class.getSimpleName());

    @Autowired
    private CertMonitorService certMonitorService;

    @GetMapping("/certs/{namespace}/list")
    public List<Certificate> listCertificates(@PathVariable String namespace) {
        return certMonitorService.listAllCertsByNamespace(namespace);
    }

    @GetMapping("/certs/list")
    public List<Certificate> listCertificates() {
        return certMonitorService.listAllCertsInAnyNamespace();
    }

}
