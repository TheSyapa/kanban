## Kanban Application

This is a simple implementation of a Kanban Board, a tool that helps visualize and manage work. Originally it was first created in Toyota automotive, but nowadays it's widely used in software development.

A Kanban Board is usually made of 3 columns - *TODO*, *InProgres*s & *Done*. In each column there are Post-it notes that represents card and their status.

As already stated this project is an implementation of such board and made of 3 separate Docker containers that holds:

- PostgreSQL database
- Java backend (Spring Boot)
- Angular frontend

The entry point for a user is a website which is available under the address: **http://localhost:4200/**

![Kanban](https://github.com/wkrzywiec/project-board/blob/master/assets/project.gif)

More information about this project you can found in blog post: https://medium.com/@wkrzywiec/how-to-run-database-backend-and-frontend-in-a-single-click-with-docker-compose-4bcda66f6de

---

### Prerequisites

In order to run this application you need to install two tools: **Docker** & **Docker Compose**.

Instructions how to install **Docker** on [Ubuntu](https://docs.docker.com/install/linux/docker-ce/ubuntu/), [Windows](https://docs.docker.com/docker-for-windows/install/), [Mac](https://docs.docker.com/docker-for-mac/install/).

**Docker Compose** is already included in installation packs for *Windows* and *Mac*, so only Ubuntu users needs to follow [this instructions](https://docs.docker.com/compose/install/).


### How to run it?

The entire application can be run with a single command on a terminal:

```
$ docker-compose up -d
```

If you want to stop it, use the following command:

```
$ docker-compose down
```

---

#### project-postgres (Database)

PostgreSQL database contains only single schema with two tables - project
and card table.

After running the app it can be accessible using these connectors:

- Host: *localhost*
- Database: *kanban*
- User: *kanban*
- Password: *kanban*


Like other parts of application Postgres database is containerized and
the definition of its Docker container can be found in
*docker-compose.yml* file.

```yml
kanban-postgres:
    image: "postgres:9.6-alpine"
    container_name: kanban-postgres
    volumes:
      - kanban-data:/var/lib/postgresql/data
    ports:
      - 5432:5432
    environment:
      - POSTGRES_DB:kanban
      - POSTGRES_USER:kanban
      - POSTGRES_PASSWORD:kanban
```

#### kanban-app (REST API)

This is a Spring Boot (Java) based application that connects with a
database that and expose the REST endpoints that can be consumed by
frontend. It supports multiple HTTP REST methods like GET, POST, PUT and
DELETE for two resources - project & card.

Full list of available REST endpoints could be found in Swagger UI,
which could be called using link: **http://localhost:8080/api/swagger-ui.html**


![swagger-ui](https://github.com/wkrzywiec/project-board/blob/master/assets/swagger.png)


This app is also put in Docker container and its definition can be found
in a file *kanban-app/Dockerfile*. 



#### kanban-ui (Frontend)

This is a real endpoint for a user where they can manipulate their
projects and cards. It consumes the REST API endpoints provided by
*kanban-app*.

It can be entered using link: **http://localhost:4200/**

Cloned this kannan board three tier application 

#### Then given the following command to build as image 

```
docker-compose build 
docker-compose up
```
Once image is built , push to docker hub 
Create three repo in docker hub for three different image
````
docker login 
````
the above command for getting the hub registry 

push to docker hub 

Create kubernetes cluster 
````
minikube start 
````
Cluster created 

Then deploy the yml files 
````
kubectl apply -f filename.yml 

Create service for it 

kubectl apply -f filename.yml
````
In that service give 

type:loadbalancer 

Autoscaling by giving 5 replicasets and loadbalancing by service yml file 
````
minikube service servicename --url 
````
for checking that url and whether its connected s
````
kubectl get deployments 
kubectl get pods 
````
Follow the below link for alert message like slack (kubewatch) in kubernetes 

https://betterprogramming.pub/monitor-your-kubernetes-resources-with-kubewatch-d40ecf420f28
