# Arquitectura Hexagonal (Guía del proyecto)

## Objetivo
Mantener una separación estricta entre **dominio**, **casos de uso (application)** e **infraestructura (web, JPA, security técnica)**.

---

## Capas y responsabilidades

### 1) `domain`
- Contiene reglas de negocio y modelos (`record`, value objects, invariantes).
- **No depende** de Spring, JPA, controllers ni DTOs HTTP.

### 2) `application`
- Define y ejecuta casos de uso (`port/in`, `service`).
- Define puertos de salida (`port/out`) para persistencia o servicios externos.
- **Solo usa dominio + puertos**.
- **No importa** `controller.dto` ni clases `infra`.

### 3) `infra`
- Implementa adaptadores técnicos:
  - Controllers REST (entrada HTTP)
  - Repositorios JPA (salida a BD)
  - Entidades JPA
- Aquí se hace el mapeo entre DTO ↔ dominio y entidad ↔ dominio.

### 4) `security`
- Debe operar vía puertos/casos de uso cuando necesite datos de negocio.
- Evitar dependencias directas a `SpringData...` o `*Entity` en servicios de negocio.

---

## Regla de dependencias

Dirección permitida:

`infra -> application -> domain`

`domain` no depende de nadie.

Dependencias prohibidas:
- `application -> controller.dto`
- `application -> infra.*`
- `domain -> spring/jpa/web`

---

## Convenciones del proyecto

- **Casos de uso (`port/in`)**: reciben/retornan **dominio** (o comandos propios de application), no DTO HTTP.
- **Puertos de salida (`port/out`)**: trabajan con dominio.
- **Controllers**: únicos responsables de transformar:
  - `Request DTO -> dominio`
  - `dominio -> Response DTO`
- **Adapters JPA**: únicos responsables de transformar:
  - `dominio -> Entity`
  - `Entity -> dominio`

---

## Checklist para PRs

Antes de mergear:

- [ ] No hay imports `controller.dto` dentro de `application`.
- [ ] No hay imports `infra.*` dentro de `application`.
- [ ] `security` no depende directo de `SpringData*` para lógica de negocio.
- [ ] Los controllers hacen el mapeo DTO ↔ dominio.
- [ ] Los adapters hacen el mapeo Entity ↔ dominio.
- [ ] `domain` no tiene anotaciones/framework de infraestructura.

---

## Ejemplo de flujo correcto

1. `POST /autores` llega al Controller con `AutoresRequest`.
2. Controller mapea a `Autore` y llama `CreateAutoresCase`.
3. `AutoresService` usa `AutoresRepositoryPort`.
4. `JpaAutoresRepositoryAdapter` persiste en JPA y retorna `Autore`.
5. Controller mapea `Autore` a `AutoresResponse`.

---

## Nota
Si en el futuro se necesitan objetos específicos para casos de uso, crearlos en `application` (por ejemplo `CreateUserCommand`), no reutilizar DTOs de HTTP.