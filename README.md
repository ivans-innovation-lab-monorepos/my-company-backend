# my-company-backend

This **backend** application/s is a mono-repo version of the [lab (multi-repo version)](http://ivans-innovation-lab.github.io/), and represents its predecessor. It exposes a REST API to be consumed by [internal employees or partners](http://idugalic.pro/2017-12-26-API-Strategy/).

![Monorepo](https://github.com/ivans-innovation-lab-monorepos/my-company-backend/raw/master/monorepo.png)


Repository is structured in two folders:

- my-company-apps (deployable applications that exposes REST API)
   - monolith application or
   - microservices applications
- my-company-libs (libraries/components that are used by applications)

Libraries are actually components that are implemented in a separate maven projects and packaged independently into 'jar' archives. This is giving us an opportunity to create a service (REST API applications) for every component independently and apply microservices architecture style as an alternative to monolith style.

![My Company dependencies](https://github.com/ivans-innovation-lab-monorepos/my-company-backend/raw/master/my-company-dependencies.png)

At this stage we have **[one release cycle and deployment pipeline](https://circleci.com/gh/ivans-innovation-lab-monorepos/workflows/my-company-backend)** for all applications and libraries together.


## Benefits

- **Release management is simple** (Libraries are always in SNAPSHOT. Every application depends on the latest library/component SNAPSHOT. We introduced release versions on the level of application/s because this applications are exposing REST API that will be consumed by external partner applications, and you want to communicate changes).
- **Continuous Integration is improved** (The code is integrated and tested more frequently because everything is build and tested on every push to one repository, so we know how change on a library level will affect some of the applications early in the process)
- **No need for additional tools** (In mult-repo scenario you need to coordinate many repos which is very hard. We tend to use ChatOps platforms like Atomist and Slack to coordinate our work. In mono-repo scenario we can rely on Github only. Maven repo tools like Nexus are not necessary, we are not relasing our libraries (always in SNAPSHOT).

## Drawbacks

- **Scaling development is difficult** (Multiple teams on the same code base)
- **No separate deployment of applications** (All applications are deployed on every change, regadless if the change was on the library or application level)
- **Securing the repo is hard** (It is difficult to grant granular access rights to folders/modules/apps)

## Note

In a multi-repos scenario we are not sure how the change in a library will affect the applications early in the process. This can be improved, but it requires specific integration testing and more infrastructure. This is not the case in mono-repo environment where we can practice Continuous Integration in a better way, which is a good benefit of mono-repo environment. 

Organizing multiple teams over a one repo is hard, and this is a big drawback of a mono-repo environment.

It is important to realize directions in which your organization can shift to be able to produce quality code fast. Will you go to mono-repo or multi-repo really doesn't matter, you should be able to go in both directions and support your business strategy! You have to make your architecture aware of this, and structure your applications accordingly.


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
