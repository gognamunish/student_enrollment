### Travis CI 

[![Build Status](https://travis-ci.com/gognamunish/student_enrollment.svg?branch=master)](https://travis-ci.com/gognamunish/student_enrollment)

### About
Student Enrollment project is a basic Spring boot application that exposes a REST API exposing few operations supporting current business use cases.

### Prerequisites

- Maven
- Java 8
- Docker

### Design Features
- Travis CI configured for builds, every commit starts a new build. 
- Maven multi module project and separates REST from DAO layer.
- Since there is only one Micro service at the moment so no Gateway endpoint added for sake of performance (YAGNI) 
- Database can be changed to MySQL by just changing few application properties (default is H2)
- 

### TODO
- Helm chart / Kubernetes Setup
- AWS Deployment / Setup
- MySQL spring profile

## Running Application as Docker Image 
```shell script
curl -o deploy.sh https://raw.githubusercontent.com/gognamunish/student_enrollment/master/scripts/deploy.sh
chmod +x deploy.sh
./deploy.sh
```