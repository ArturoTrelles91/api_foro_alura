# ForoHub API — README

Proyecto de API REST construido con **Java 21** y **Spring Boot** para el reto de Alura. 
Incluye autenticación JWT, persistencia con JPA/Hibernate sobre **MySQL**, y migraciones con **Flyway**.

> Probado con Insomnia (capturas incluidas).

## Stack

- Java 21 (JDK 21)
- Spring Boot 4.x (snapshot de la app)
- Spring Security + JWT (lib `com.auth0:java-jwt`)
- Spring Data JPA / Hibernate 7.1
- MySQL 8.x
- Flyway para migraciones
- Maven

## Requisitos

- JDK 21
- Maven 3.9+
- MySQL 8 (crear la BD `forohub`)
- Variables/propiedades de conexión:
  - `spring.datasource.url=jdbc:mysql://localhost:3306/forohub?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true`
  - `spring.datasource.username=<usuario>`
  - `spring.datasource.password=<password>`

> Flyway ejecuta las migraciones al iniciar la app. Si cambias versiones/archivos, respeta el formato `V__`.


## Ejecutar

```bash
# desde el root del proyecto
mvn spring-boot:run
# o desde el IDE (ApiApplication.main)
```

Si todo va bien verás algo como *"Tomcat started on port 8080"*.

---

## Autenticación

Login vía **POST `/auth/login`** con JSON:

```json
{
  "username": "arturo",
  "password": "123456"
}
```

Respuesta (ejemplo):

```json
{
  "token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9...",
  "type": "Bearer"
}
```

En Insomnia/REST client usa el **Auth → Bearer Token** y pega el token, o bien añade el header:

```
Authorization: Bearer <TOKEN>
```

**Notas**

- El string que empieza con `$2a$10$...` es **BCrypt** del password en BD, **no** es un JWT.
- La caducidad del JWT es configurable en la app; revisa la propiedad de expiración en `application.properties|yml` o el `JwtService` para el valor efectivo.

Capturas (Insomnia):

![Login ok](docs/images/login-success.png)
![Auth con Bearer](docs/images/bearer-auth-tab.png)

---

## Endpoints principales

### 1) Listar tópicos
`GET /topics` (requiere token)

Ejemplo cURL:
```bash
curl -H "Authorization: Bearer $TOKEN" http://localhost:8080/topics
```

### 2) Crear tópico
`POST /topics` (requiere token)

Body JSON esperado:
```json
{
  "title": "Prueba Hola Mundo",
  "message": "Hola Mundo",
  "status": "OPEN",
  "author": "arturo",
  "course": "Spring Boot"
}
```

Respuesta 201 (ejemplo):
```json
{
  "id": 3,
  "title": "Prueba Hola Mundo",
  "message": "Hola Mundo",
  "creationDate": "2025-08-16T20:11:15.132749",
  "status": "OPEN",
  "author": "arturo",
  "course": "Spring Boot"
}
```

cURL:
```bash
curl -X POST http://localhost:8080/topics   -H "Authorization: Bearer $TOKEN"   -H "Content-Type: application/json"   -d '{"title":"Prueba Hola Mundo","message":"Hola Mundo","status":"OPEN","author":"arturo","course":"Spring Boot"}'
```

![POST tópico 201](docs/images/post-topic-success.png)

### 3) Actualizar tópico
`PUT /topics/<built-in function id>` (requiere token)

```bash
curl -X PUT http://localhost:8080/topics/3   -H "Authorization: Bearer $TOKEN"   -H "Content-Type: application/json"   -d '{"title":"Nuevo título","message":"Nuevo mensaje"}'
```

### 4) Borrar tópico
`DELETE /topics/<built-in function id>` (requiere token)

```bash
curl -X DELETE http://localhost:8080/topics/3   -H "Authorization: Bearer $TOKEN"
```

---

## Migraciones (Flyway)

- Al iniciar, Flyway valida y aplica versiones (`V1__...`, `V2__...`, etc.).
- Si ves mensajes como **"migrations were detected but did not follow the filename convention"**, renombra los archivos al patrón correcto.
- Si aparece **"Validate failed: detected failed migration to version X"**, corrige el script, **repara** el historial y vuelve a levantar:

```bash
# Opción 1: usar "flyway repair" (si usas CLI)
# Opción 2 (solo DEV): limpiar la BD y reiniciar
#   spring.flyway.clean-disabled=false  (DEV)
#   flyway:clean && levantar de nuevo
```

> El warning “MySQL 8.4 is newer than this version of Flyway…” es informativo; actualiza Flyway si deseas quitarlo.

---

## Errores comunes y cómo evitarlos

- **400 JSON parse error**: JSON mal formado (comillas de más, falta coma). Valida el cuerpo antes de enviar.
- **401/403**: falta el header `Authorization: Bearer <TOKEN>` o el token caducó.
- **BCrypt vs JWT**: `$2a$...` es el hash del **password** en BD, no lo uses como token.
- **No levanta el contexto por JPA**: suele ser un fallo previo de Flyway; revisa primero migraciones.

---

## Scripts de prueba rápidos

```bash
# 1) Login
TOKEN=$(curl -s -X POST http://localhost:8080/auth/login   -H "Content-Type: application/json"   -d '{"username":"arturo","password":"123456"}' | jq -r '.token')

# 2) Crear un tópico
curl -X POST http://localhost:8080/topics   -H "Authorization: Bearer $TOKEN"   -H "Content-Type: application/json"   -d '{"title":"Prueba Hola Mundo","message":"Hola Mundo","status":"OPEN","author":"arturo","course":"Spring Boot"}'
```

---

## Estructura (alto nivel)
- `domain/` entidades y repositorios (JPA)
- `infra/security/` filtros JWT y `DatabaseUserDetailsService`
- `controllers/` endpoints REST
- `db/migration/` scripts Flyway `V__`

---

## Licencia
Uso educativo / reto Alura.
