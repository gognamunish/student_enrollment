### Travis CI 

[![Build Status](https://travis-ci.com/gognamunish/student_enrollment.svg?branch=master)](https://travis-ci.com/gognamunish/student_enrollment)

### About
Student Enrollment project is a basic Spring boot application that follows micro service architecture and exposes REST API endpoints supporting current business use cases.

### Prerequisites

- Maven
- Java 8
- Docker

### Design Features/Considerations
- Travis CI configured for builds, every commit starts a new build. 
- Maven multi module project and separates REST from DAO layer (can be easily separated into individual repos).
- Since there is only one Micro service at the moment so no Gateway endpoint added for sake of performance (YAGNI) 
- Spring Data JPA ready (default is H2)
- Cucumber BDD adoption for basic use cases (Show case only).
- Database design for the moment defines just one Entity, later we might want to normalize enrollment into - Student, Class & Enrollment entities.
- No Cache is used at the moment so in Future some distributed and highly fault tolerant Cache like Hazelcast can be used

### TODO
- Helm chart / Kubernetes Setup
- AWS Deployment / Setup
- MySQL spring profile
- Application has potential for all good "Spring Cloud" stuff like Config Server, ZooKeeper Discovery, Zuul, Ribbon, Hystrix etc.

## Running Application as Docker Image on Local Machine
```shell script
curl -o deploy.sh 'https://raw.githubusercontent.com/gognamunish/student_enrollment/master/scripts/deploy.sh'
chmod +x deploy.sh
./deploy.sh
```


## Swagger API Documentation

http://localhost:8080/swagger-ui.html


