#!/bin/bash

echo "Configuring k8s-cert-monitor using Helm..."

echo "Executing helm template to prepare for deployment..."
helm template ../charts/k8s-cert-monitor

helm lint ../charts/k8s-cert-monitor

echo "Installing k8s-cert-monitor app in the default namespace"
helm install k8s-cert-monitor ../charts/k8s-cert-monitor

echo "Waiting for 10s seconds to let the containers initialize..."
sleep 10s
minikube service k8s-cert-monitor

