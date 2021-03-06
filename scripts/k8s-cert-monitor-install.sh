#!/bin/bash

echo "Configuring k8s-cert-monitor using Helm..."

echo "Executing helm template to prepare for deployment..."
helm template charts/k8s-cert-monitor

helm lint charts/k8s-cert-monitor

echo "Installing k8s-cert-monitor app in the default namespace"
helm install k8s-cert-monitor charts/k8s-cert-monitor

echo "Configuring service monitors & alert rules..."
kubectl create secret generic additional-scrape-configs --from-file=scripts/prometheus-additional.yaml --dry-run -oyaml > additional-scrape-configs.yaml
kubectl apply -f scripts/additional-scrape-configs.yaml -n monitoring
kubectl apply -f scripts/k8s-cert-expiry-alert-rule.yaml

echo "Waiting for 30s seconds to let the containers initialize..."
sleep 30s
minikube service k8s-cert-monitor

