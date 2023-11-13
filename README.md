# java_Oauth
Template for Java Springboot using Oauth

## Oauth Flows
![Oath2 Flows](/static/images/flows.png)
## How to use
- Login to Azure and navigate to Entra ID (formerly Azure AD)
- Click on App Registrations
- New Registration
- Name it and select Register
- Naviate to manifests and set the following. This forces v2 tokens
```console
"accessTokenAcceptedVersion": 2,
```
- Navigate to Certificates and Secrets then to Client Secrets
- Create New, name it, and selet 6 months TTL
- Take note of Tenant ID, Client ID, And Secet
- Export the previously noted configurations and install and run
```console
export AZ_CLIENT_ID=REDACTED
export AZ_CLIENT_SECRET=REDACTED
export AZ_TENANT=REDACTED
cd client
mvn clean install -Dmaven.test.skip=true
mvn spring-boot:run -Dmaven.test.skip=true
```
- In another terminal export the tenant again, then install and run
```console
export AZ_TENANT=REDACTED
cd resource
mvn clean install -Dmaven.test.skip=true
mvn spring-boot:run -Dmaven.test.skip=true
```

- Open a new terminal and show you can hit localhost:8080/fail to prove you need auth
```console
curl localhost:8080/fail
Failed to make non-authenticated request
```
- Hit localhost:8080/success to prove it works
```console
curl localhost:8080/success
This is a protected endpoint. You're authenticated!
```
