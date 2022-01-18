# k8s-cert-monitor
An utility app to monitor and alert certs managed by k8s cert-manager 

## Requirements

 * [Helm](https://helm.sh/) - This project was developed and tested on helm 3
 * [Docker](https://www.docker.com/products/docker-desktop) - This project was tested on docker mac
 * [Kubernetes | Minikube](https://minikube.sigs.k8s.io/docs/start/)

For building and running the application you will need, 
  - [JDK 11](https://openjdk.java.net/projects/jdk/11/)
  - [Maven 3](https://maven.apache.org)

## Quickstart - Running the application using minikube
There are few scripts under `scripts` folder that can be used in following order to set up prometheus, grafana, alert-maanger, cert-manager and the k8s-cert-monitor

1. Set up monitoring frameworks in the `monitoring` namespace
```shell
git clone git@github.com:manikandan-vm/k8s-cert-monitor.git
cd k8s-cert-monitor

sh scripts/prometheus-stack-install.sh
```
2. Set up cert-manager in the `cert-manager` namespace
```shell
sh scripts/cert-manager-install.sh
```
3. Finally set up the k8s-cert-monitor app along with the prometheus rules
```shell
sh scripts/k8s-cert-monitor-install.sh
```
This will open up the swagger doc from where api(s) can be accessed. 

## Running the application locally

There are several ways to run a Spring Boot application on your local machine. One way is to execute the `main` method in the `com.k8s.cert.monitor.CertMonitorApplication` class from your IDE.

Alternatively you can use the [Spring Boot Maven plugin](https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins-maven-plugin.html) like so:

```shell
mvn spring-boot:run
```
Once you run locally, you can access the open api documentation by accessing [localhost-swagger](http://localhost:8080/swagger-ui/index.html)

