# [projects](http://ivans-innovation-lab-monorepos.github.io/projects)/my-company-backend [![CircleCI](https://circleci.com/gh/ivans-innovation-lab-monorepos/my-company-backend.svg?style=svg)](https://circleci.com/gh/ivans-innovation-lab-monorepos/my-company-backend)

This **backend** application/s is a mono-repo version of the [lab](http://ivans-innovation-lab-monorepos.github.io), and represents a predecessor of a [multi-repo version](http://ivans-innovation-lab.github.io). It exposes a REST API to be consumed by [internal employees or partners](http://idugalic.pro/2017-12-26-API-Strategy).

![Monorepo](https://github.com/ivans-innovation-lab-monorepos/my-company-backend/raw/master/monorepo.png)


Repository is structured in two folders:

- my-company-apps (deployable applications that exposes REST API)
   - monolith application or
   - microservices applications
- my-company-libs (libraries/components that are used by applications)

Libraries are implemented in a separate maven projects and packaged independently into 'jar' archives. This promotes code sharing, reuse and it will enable us to apply microservices architecture style as an alternative to a monolith style.

![My Company dependencies](https://github.com/ivans-innovation-lab-monorepos/my-company-backend/raw/master/my-company-dependencies.png)

At this stage we have **[one deployment pipeline](https://circleci.com/gh/ivans-innovation-lab-monorepos/workflows/my-company-backend)** for all projects (apps & libs). In addition, all projects in the repository share the same dependencies. Hence, there are no version conflicts b/c everyone has to use the same/ the latest (snapshot) version. And you don't need to deal with a private maven repository when you just want to use your own libraries.


## Benefits of monorepo

- **Promotes code sharing and reuse**
- **Easier dependency management and unified versioning**
  - Everything at that current commit works together
  - Libraries are always in SNAPSHOT
  - Every application depends on the latest library SNAPSHOT
  - Release versions on the application/s level only (Applications are exposing REST API that will be consumed by external partner applications, and you want to communicate changes)
- **Easier continuous integration**
  - The code is integrated and tested more frequently because 'everything' is build and tested on each commit to the repository, so we learn fast how the change on a library level will affect applications 
- **No need for additional tools**
  - In mult-repo scenario you need to coordinate many repos which is very hard. We tend to use ChatOps platforms like Atomist and Slack to coordinate our work. 
  - In mono-repo scenario we can rely on the Github only. Maven repo tools like Nexus are not necessary, we are not publishing our libraries (always in SNAPSHOT).

## Drawbacks of monorepo

- **Scaling development is difficult** (Multiple teams on the same code base)
- **No separate deployment of applications** (All applications are deployed on every change, regadless if the change was on the library or application level)
- **An upgrade to a lib requires a change to all implementors (can't roll out different versions side by side)**
- **Takes work to try and limit access to parts of the code base**

## Architecture overview

Domain Driven Design is applied through Event Sourcing and CQRS. How Event Sourcing enables deployment flexibility - the application can be deployed as a monolithic or microservices.

### Monolithic

[The application](https://github.com/ivans-innovation-lab-monorepos/my-company-backend/tree/master/my-company-apps/my-company-monolith) is literally split into a *command-side (domain)* component and a *query-side (materialized view)* component (this is CQRS in its most literal form).

Communication between the two components is `event-driven` and the application uses simple event store (Database in this case - JPA) as a means of passing the events between components of a monolithic applicaton.

- The **command-side (domain)** processes commands. Commands are actions which change state in some way. The execution of these commands results in `Events` being generated which are persisted by Axon, and propagated out to other components. In event-sourcing, events are the sole records in the system. They are used by the system to describe and re-build domain aggregates on demand, one event at a time.
- The **query-side (materialized view)** is an event-listener and processor. It listens for the `Events` and processes them in whatever way makes the most sense. In this application, the query-side just builds and maintains a *materialised view* which tracks the state of the individual agregates (Project, Blog, Team ...).

### Microservices

Modular approach is enabling us to use microservices pattern and deploy components from monolithic application as independent applications/containers. The domain is split into a command-side microservice application/container and a query-side microservice application/container.

Communication between the two microservices is event-driven and we use RabbitMQ messaging as a means of passing the events between processes (VM's).

- The [command-side](https://github.com/ivans-innovation-lab-monorepos/my-company-backend/tree/master/my-company-apps/my-company-microservices/my-company-blog-domain-microservice) processes commands. Commands are actions which change state in some way. The execution of these commands results in Events being generated which are persisted by Axon and propagated out to other VM's (as many VM's as you like) via RabbitMQ messaging. In event-sourcing, events are the sole records in the system. They are used by the system to describe and re-build aggregates on demand, one event at a time.

- The [query-side](https://github.com/ivans-innovation-lab-monorepos/my-company-backend/tree/master/my-company-apps/my-company-microservices/my-company-blog-materialized-view-microservice) is an event-listener and processor. It listens for the Events and processes them in whatever way makes the most sense. In this application, the query-side just builds and maintains a materialised view which tracks the state of the individual agregates (Product, Blog, Team, ...). The query-side can be replicated many times for scalability and the messages held by the RabbitMQ queues are durable, so they can be temporarily stored on behalf of the event-listener if it goes down.

The command-side and the query-side containers both have REST API's which can be used to access their capabilities.


## Running instructions

### Clone it

```bash
$ git clone https://github.com/ivans-innovation-lab-monorepos/my-company-backend.git
```

### Run it

```bash
$ cd my-company-backend
$ mvn clean install
```
#### run monolith

```bash
$ cd my-company-backend/my-company-apps/my-company-monolith
$ mvn spring-boot:run
```

#### or run microservices

```bash
$ cd my-company-backend/my-company-apps/my-company-microservices/my-company-configuration-backingservice
$ mvn spring-boot:run

$ cd my-company-backend/my-company-apps/my-company-microservices/my-company-registry-backingservice
$ mvn spring-boot:run

$ cd my-company-backend/my-company-apps/my-company-microservices/my-company-blog-domain-microservice
$ mvn spring-boot:run

$ cd my-company-backend/my-company-apps/my-company-microservices/my-company-blog-materialized-view-microservice
$ mvn spring-boot:run

$ cd my-company-backend/my-company-apps/my-company-microservices/my-company-project-domain-microservice
$ mvn spring-boot:run

$ cd my-company-backend/my-company-apps/my-company-microservices/my-company-project-materialized-view-microservice
$ mvn spring-boot:run

$ cd my-company-backend/my-company-apps/my-company-microservices/my-company-api-gateway-backingservice
$ mvn spring-boot:run

$ cd my-company-backend/my-company-apps/my-company-microservices/my-company-adminserver-backingservice
$ mvn spring-boot:run

```

  ---
Created by [Ivan Dugalic][idugalic]@[lab][lab].
Need Help?  [Join our Slack team][slack].

[idugalic]: http://idugalic.pro
[lab]: http://lab.idugalic.pro
[slack]: https://communityinviter.com/apps/idugalic/idugalic
