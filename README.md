# Proyecto 2: API RESTful con OAuth2/JWT, Spring Security y Programación Funcional

## Descripción

API RESTful completa que demuestra:

- **OAuth2** y **JWT** para autenticación y autorización
- **Spring Security** con configuración avanzada
- **Programación Funcional** en Java 17+ (Records, Streams, Optional, funciones puras)
- **Arquitectura RESTful** siguiendo principios REST
- **Principios SOLID** y **DRY**
- **Spring Boot 3.x** con Java 17+

## Características

- ✅ Autenticación OAuth2 con JWT
- ✅ Autorización basada en roles (RBAC)
- ✅ Programación funcional con Streams y Optional
- ✅ Validación de datos con Bean Validation
- ✅ Manejo de errores funcional
- ✅ API RESTful con HATEOAS
- ✅ Documentación con OpenAPI/Swagger

## Arquitectura

```
┌─────────────┐
│   Client    │
└──────┬──────┘
       │
       ▼
┌─────────────┐     ┌─────────────┐
│  API REST   │────▶│  Security   │
│  Controller │     │   Layer     │
└──────┬──────┘     └──────┬──────┘
       │                    │
       ▼                    ▼
┌─────────────┐     ┌─────────────┐
│  Service    │     │  JWT Token  │
│   Layer     │     │  Provider   │
└──────┬──────┘     └─────────────┘
       │
       ▼
┌─────────────┐
│ Repository  │
│   Layer     │
└──────┬──────┘
       │
       ▼
┌─────────────┐
│  Database   │
│ (PostgreSQL)│
└─────────────┘
```

## Tecnologías

- Spring Boot 3.2.0
- Spring Security 6.x
- OAuth2 Resource Server
- JWT (JSON Web Tokens)
- PostgreSQL
- Java 17+ (Records, Pattern Matching, Sealed Classes)
- Maven

## Requisitos

- Java 17+
- Maven 3.8+
- PostgreSQL 14+

## Instalación

```bash
# Clonar el repositorio
git clone <repo-url>
cd proyecto2-oauth2-jwt-functional

# Configurar base de datos
# Crear base de datos 'auth_db' en PostgreSQL

# Compilar
mvn clean install

# Ejecutar
mvn spring-boot:run
```

## Endpoints

### Autenticación

- `POST /api/auth/login` - Iniciar sesión (obtener JWT)
- `POST /api/auth/register` - Registrar nuevo usuario
- `POST /api/auth/refresh` - Refrescar token

### Recursos Protegidos

- `GET /api/users` - Listar usuarios (requiere ROLE_ADMIN)
- `GET /api/users/{id}` - Obtener usuario (requiere autenticación)
- `PUT /api/users/{id}` - Actualizar usuario (requiere autenticación)
- `DELETE /api/users/{id}` - Eliminar usuario (requiere ROLE_ADMIN)

## Estructura del Proyecto

```
proyecto2-oauth2-jwt-functional/
├── src/main/java/com/example/auth/
│   ├── AuthApplication.java
│   ├── config/
│   │   ├── SecurityConfig.java
│   │   └── JwtConfig.java
│   ├── domain/
│   │   ├── User.java
│   │   └── Role.java
│   ├── application/
│   │   ├── AuthService.java
│   │   └── UserService.java
│   ├── infrastructure/
│   │   ├── controller/
│   │   ├── repository/
│   │   └── security/
│   └── shared/
│       └── functional/
└── README.md
```

