# microservices-security
spring cloud: microservices-security

1. browser microservice has role of frontend, it is make register/login publish wiki requests to zuul
2. zuul generate zuul token and send it to register microservice
3. register validate zuul token and do registration
4. login: auth microservice also receive zuul token validate and after succesfull login  service generate auth token and return back.
5. with auth and zuul tokens wiki service insert wiki json to mongodb.
6. wiki service get user email from auth token and call auth service and validate the user.
7. eureka service is password protected and all microservices for connect eureka use username/password.
8. Each microservice has doublicated token provider services, an all credentials in yml files.
9. zipkin store all traces from microservices in mysql.


./run.sh

# zipkin
http://localhost:9411/zipkin/

# eureka
http://localhost:8761/

# zuul

http://localhost:8079/gateway/micro/auth/api/v2/tokens/generate/test/token

# auth

http://localhost:5001/swagger-ui.html

# browser

http://localhost:5002/swagger-ui.html

# register

http://localhost:5003/swagger-ui.html

# phpmyadmin

http://localhost:9191 (root:root)
