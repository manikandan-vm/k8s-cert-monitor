#!/bin/bash

echo "Cleaning minikube and starting a fresh cluster ..."
minikube delete && minikube start --kubernetes-version=v1.20.0 --memory=6g --bootstrapper=kubeadm --extra-config=kubelet.authentication-token-webhook=true --extra-config=kubelet.authorization-mode=Webhook --extra-config=scheduler.bind-address=0.0.0.0 --extra-config=controller-manager.bind-address=0.0.0.0

echo "Disabling metrics add on ..."
minikube addons disable metrics-server

echo "Cleaning up kube-prometheus directory if it exists"
rm -rf kube-prometheus/
echo "Checking out kube-prometheus project"
git clone git@github.com:prometheus-operator/kube-prometheus.git

# Create the namespace and CRDs, and then wait for them to be available before creating the remaining resources
kubectl apply --server-side -f kube-prometheus/manifests/setup
until kubectl get servicemonitors --all-namespaces ; do date; sleep 1; echo ""; done
kubectl apply -f kube-prometheus/manifests/
