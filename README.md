# k8s-cert-monitor
An utility app to monitor and alert certs managed by k8s cert-manager 

## Requirements

 * [Helm](https://helm.sh/) - This project was developed and tested on helm 3
 * [Docker](https://www.docker.com/products/docker-desktop) - This project was tested on docker mac
 * [Kubernetes | Minikube](https://minikube.sigs.k8s.io/docs/start/)

For building and running the application you will need, 
  - [JDK 11](https://openjdk.java.net/projects/jdk/11/)
  - [Maven 3](https://maven.apache.org)

## Running the application locally

There are several ways to run a Spring Boot application on your local machine. One way is to execute the `main` method in the `com.k8s.cert.monitor.CertMonitorApplication` class from your IDE.

Alternatively you can use the [Spring Boot Maven plugin](https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins-maven-plugin.html) like so:

```shell
mvn spring-boot:run
```
Once you run locally, you can access the open api documentation by accessing [localhost-swagger](http://localhost:8080/swagger-ui/index.html)