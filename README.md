# Quarkus-jwt

## Getting started
1. Clone the project
```
git clone https://github.com/raphael-97/Quarkus-jwt.git
```

2. Run in the root of this project
```
mvnw compile quarkus:dev

or 

docker compose up
```


### Endpoints with role based access control (RBAC)

Roles: admin, user

#### Auth

`POST /api/auth/login` # Basic Authentication -> You get jwt cookie after successful login

`POST /api/auth/register` # All

#### User (only accessible with bearer jwt token and the correct role)

Jwt token expires in 60 minutes if not changed in [application.properties](https://github.com/raphael-97/Quarkus-jwt/blob/main/src/main/resources/application.properties)

`GET /api/users` # admin, user

`GET /api/users/{id}`# admin, user

`POST /api/users` # admin

`PUT /api/users/{id}` # admin

`DELETE /api/users/{id}` # admin


### How to use

#### Create an account (it has user role):

`curl -i -X 'POST' 'http://localhost:8080/api/auth/register' -H 'Content-Type: application/json' -d '{ "username": "john", "password": "secret"}'`

#### Login to your account:

"am9objpzZWNyZXQ=" is Base64 encoded String of username:password

`curl -i -X POST 'http://localhost:8080/api/auth/login' -H 'Authorization: Basic am9objpzZWNyZXQ='`

You get a bearer jwt token back.

#### Use User api endpoint with jwt token

`curl -i -X GET 'http://localhost:8080/api/users' -H 'Authorization: Bearer TOKENHERE'`

Keep in mind your registered user only has access to endpoints with the user role.


#### Login to an admin account:

One pre-made admin account has the username: "admin" and password: "password".
The Base64 encoded string is: YWRtaW46cGFzc3dvcmQ=

#### Get the jwt token of admin role
`curl -i -X POST 'http://localhost:8080/api/auth/login' -H 'Authorization: Basic YWRtaW46cGFzc3dvcmQ='`

#### Create new account with the admin role and insert your jwt token

`curl -i -X POST 'http://localhost:8080/api/users' -H 'Content-Type: application/json' -H 'Authorization: Bearer TOKENHERE' -d '{ "username": "Doe", "password": "secret", "roles": ["admin", "user"]}'`


