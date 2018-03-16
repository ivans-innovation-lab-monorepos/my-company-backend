# my-company-backend

This backend application is a mono-repo version of the lab: http://ivans-innovation-lab.github.io/

Repo is structured in two folders

- my-company-apps (deployable applications)
- my-company-libs (libraries that are used by applications)

## Drawbacks

- Scaling development is difficult (Multiple teams on the same code base)
- No separate deployment of applications (All applications are deployed on every change in the git repo at the same time)
- Securing the repo is hard (It is difficult to grant granular access rights to folders/modules/apps)

## Benefits

- Release management is simple (Libraries are always in SNAPSHOT. Every application depends on the latest library SNAPSHOT. I would keep applications in SNAPSHOT as well, but you can choose to introduce release versions on this level)
- Continuous Integration is improved (The code is integrated and tested more frequently because everything is build and tested on every push to one repository, so we know how change on a library level will affect some of the applications early in the process)
- No need for additional tools (In mult-repo scenario you need to coordinate many repos which is very hard. We tend to use ChatOps platforms like Atomist and Slack to coordinate our work. In mono-repo scenario we can rely on Github only

## Running instructions

### Clone it

```bash
$ git clone https://github.com/ivans-innovation-lab-monorepos/my-company-backend.git
```

### Run it

```bash
$ cd my-company-backend
$ mvn clean install
$ mvn spring-boot:run
```
