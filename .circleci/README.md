```
cf api https://api.run.pivotal.io
cf auth EMAIL PASSWORD

# Space Staging
###################
cf target -o idugalic -s StageMonorepo
## Monolithic
cf create-service cleardb spark mysql
## Microservices
cf create-user-provided-service my-company-configuration-backingservice -p '{"uri":"https://stagemonorepo-my-company-configuration-backingservice.cfapps.io"}'
cf create-user-provided-service my-company-registry-backingservice -p '{"uri":"https://stagemonorepo-my-company-registry-backingservice.cfapps.io"}'
cf create-service cloudamqp lemur rabbit

# Space Production
#####################
cf target -o idugalic -s ProdMonorepo
## Monolithic
cf create-service cleardb spark mysql
## Microservices
cf create-user-provided-service my-company-configuration-backingservice -p '{"uri":"https://prodmonorepo-my-company-configuration-backingservice.cfapps.io"}'
cf create-user-provided-service my-company-registry-backingservice -p '{"uri":"https://prodmonorepo-my-company-registry-backingservice.cfapps.io"}'
cf create-service cloudamqp lemur rabbit

```