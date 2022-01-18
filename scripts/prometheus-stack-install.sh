#!/bin/bash

echo "Cleaning minikube and starting a fresh cluster ..."
minikube delete && minikube start --kubernetes-version=v1.20.0 --memory=6g --bootstrapper=kubeadm --extra-config=kubelet.authentication-token-webhook=true --extra-config=kubelet.authorization-mode=Webhook --extra-config=scheduler.bind-address=0.0.0.0 --extra-config=controller-manager.bind-address=0.0.0.0

echo "Disabling metrics add on ..."
minikube addons disable metrics-server

# Create the namespace and CRDs, and then wait for them to be available before creating the remaining resources
kubectl apply --server-side -f manifests/setup
until kubectl get servicemonitors --all-namespaces ; do date; sleep 1; echo ""; done
kubectl apply -f manifests/

echo "Running port-forwards to access prometheus, grafana and alert manager in local..."
#Prometheus, Grafana, and Alertmanager dashboards can be accessed quickly using kubectl port-forward after running the quickstart via the commands below. Kubernetes 1.10 or later is required.
kubectl --namespace monitoring port-forward svc/prometheus-k8s 9090 &

kubectl --namespace monitoring port-forward svc/grafana 3000 &

kubectl --namespace monitoring port-forward svc/alertmanager-main 9093 &

echo "Access prometheus: http://localhost:9090"
echo "Access grafana:  http://localhost:3000"
echo "Access alert-manager: http://localhost:9093"


