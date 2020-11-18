# First Cup of Jakarta EE in Kubernetes

Based on the "First Cup of Java EE examples".

## Build the example

Clone this repository and build the example using:

```
mvn package
```

## Run the example

### Run the Duke's age service

In the directory `dukes-age`, run:

```
mvn payara-micro:start  
```

It should start the service at http://localhost:8080/. Test using http://localhost:8080/webapi/dukesAge.

### Run the First Cup frontend

In the directory `firstcup-war`, run:

```
mvn payara-micro:start  
```

It should start the service at http://localhost:8081/. Open the URL in a browser.

## Build and deploy Docker images

In order to build and deploy Docker images to a local MicroK8s Docker repository at http://localhost:32000, run the following in the project root:

```
mvn jib:build
```

## Generate K8s resources from maven

```
mvn k8s:resource
```

## Deploy to K8s from maven

```
mvn k8s:apply
```