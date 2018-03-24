# [projects](http://ivans-innovation-lab-monorepos.github.io/projects)/my-company-backend [![CircleCI](https://circleci.com/gh/ivans-innovation-lab-monorepos/my-company-backend.svg?style=svg)](https://circleci.com/gh/ivans-innovation-lab-monorepos/my-company-backend)

This **backend** application/s is a mono-repo version of the [lab (multi-repo version)](http://ivans-innovation-lab.github.io/), and represents its predecessor. It exposes a REST API to be consumed by [internal employees or partners](http://idugalic.pro/2017-12-26-API-Strategy/).

![Monorepo](https://github.com/ivans-innovation-lab-monorepos/my-company-backend/raw/master/monorepo.png)


Repository is structured in two folders:

- my-company-apps (deployable applications that exposes REST API)
   - monolith application or
   - microservices applications
- my-company-libs (libraries/components that are used by applications)

Libraries are implemented in a separate maven projects and packaged independently into 'jar' archives. This promotes code sharing, reuse and it will enable us to apply microservices architecture style as an alternative to monolith style easier.

![My Company dependencies](https://github.com/ivans-innovation-lab-monorepos/my-company-backend/raw/master/my-company-dependencies.png)

At this stage we have **[one deployment pipeline](https://circleci.com/gh/ivans-innovation-lab-monorepos/workflows/my-company-backend)** for all applications and libraries together.


## Benefits

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

## Drawbacks

- **Scaling development is difficult** (Multiple teams on the same code base)
- **No separate deployment of applications** (All applications are deployed on every change, regadless if the change was on the library or application level)
- **An upgrade to a lib requires a change to all implementors (can't roll out different versions side by side)**
- **Takes work to try and limit access to parts of the code base**


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
#### Run monolith

```bash
$ cd my-company-backend/my-company-apps/my-company-monolith
$ mvn spring-boot:run
```

#### Run microservices

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
