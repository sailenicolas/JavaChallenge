# Challenge Java (Docker Compose | Helm)

Resumen breve
- Este repositorio contiene dos formas de ejecutar la solución:
    1. Docker Compose (archivo `docker-compose.yml` + `.env`) — ejecución local con Docker.
    2. Helm (carpeta `helm`) — desplegar en Kubernetes (Minikube se utiliza de ejemplo).

## Docker
Prerrequisitos
- Docker (y Docker Compose)

Estructura
- docker-compose.yml
- .env (variables de entorno usadas por docker-compose)
- Dockerfile por ARG declarando servicio 
- README.md (este archivo)

:::mermaid
sequenceDiagram
title POST WEBHOOK create subscription
    actor Cliente
    participant api as ms-api
    participant pos as ms-pos
    participant cached as ms-cached
    participant datos as REDIS
    Cliente->>api: POST /api/aaa
    activate api
    activate pos
    api->>pos: Call
    activate cached
    pos->>cached: POST /api/VCC
    activate cached
    cached -->> datos: create
    deactivate cached
    activate datos
    datos -->> cached: return
    deactivate datos
    cached -->> pos: return
    pos -->> api: return
    api -->> Cliente: return
:::

Ejecutar con Docker Compose
1. Crear `.env` (ejemplo mínimo):
     ```
     POSTGRES_USER=app
     POSTGRES_PASSWORD=changeme
     POSTGRES_DB=appdb
     REDIS_HOST=redis
     BACKEND_IMAGE=my-backend:latest
     FRONTEND_IMAGE=my-frontend:latest
     ```
2. Construir imágenes (si aplica):
     - docker build -t my-backend:latest ./backend
     - docker build -t my-frontend:latest ./frontend
3. Levantar servicios:
     - docker-compose up -d --build
4. Ver logs / parar:
     - docker-compose logs -f
     - docker-compose down -v

Notas Docker Compose
- Usar volúmenes para persistencia de PostgreSQL.
- Variables sensibles (contraseñas) pueden colocarse en `.env` o usar Docker secrets en entornos más seguros.
- Exponer puertos locales según `docker-compose.yml` (ej.: backend: 8080, frontend: 3000).


## Helm

### Prerrequisitos
- Docker
- Minikube
- kubectl
- Helm 3

### Estructura

- helm/
    - Chart.yaml
    - values.yaml
    - templates/...
- Dockerfile por ARG declarando servicio
- README.md (este archivo)


### Ejecutar con Helm en Minikube
1. Instalar 
     - Minikube 
     - Docker (O podman)
     - Helm
2. Iniciar Minikube:
     - minikube start
3. Instalar el chart:
     - helm upgrade --install challenge1 ./helm -n challenge --create-namespace -f ./helm/values.yaml
4. Ver estado:
     - kubectl get pods -n challenge
     - kubectl get svc -n challenge
5. Acceso:
     - minikube service <servicio> -n challenge
     - o kubectl port-forward svc/backend 8080:8080 -n challenge

Notas Helm
- values.yaml contiene imágenes, recursos, persistencia y configuración de Redis / Postgres.
- Secrets y ConfigMaps se declaran como recursos en templates/ para inyectar variables en runtime.
- Se Habilitan persistence (PVC) para Postgres y Redis.


## Diseño de arquitectura y decisiones técnicas
- Microservicios:
    - Cada servicio es responsable de su dominio y sus datos (Recomendado: base de datos por servicio).
    - Comunicación: HTTP/REST o eventos (si aplica).
- WebFlux / Reactivo:
    - Backend implementado con Spring WebFlux (non-blocking) para alto rendimiento y manejo eficiente de conexiones.
    - Uso de programacion reactiva (Flux/Mono) en capas de controlador, servicio y repositorio (cuando sea compatible).
- MVC vs Reactivo:
    - MVC clásico (Spring MVC) es bloqueante; WebFlux mantiene separación de responsabilidades (controlador -> servicio -> repositorio) pero con tipos reactivos.
    - Controladores reaccionan a flujos de datos (no a hilos bloqueantes).
- RedisReactivo:
    - Redis usado como cache reactivo o pub/sub con cliente reactivo (lettuce/reactor).
    - Interacciones no bloqueantes para mantener la cadena reactiva.
- PostgreSQL:
    - Persistencia relacional; usar R2DBC si se desea acceso reactivo a Postgres.
    - Transacciones y consultas complejas siguen en la capa de persistencia.
- DRY (Don't Repeat Yourself)
  DRY: extraer lógica común en utilidades/services compartidos o librerías internas.
- SOLID:
  - SOLID: aplicar Single Responsibility (controladores ligeros), Open/Closed (extensibilidad), Dependency Injection (inversión de dependencias), interfaces para repositorios, etc.

Principios asociados y su aplicación
- Escalabilidad: microservicios + Docker/Helm permiten escalar por servicio; WebFlux mejora concurrencia.
- Resiliencia: circuit breakers, timeouts, retries; separación de responsabilidades ayuda a aislar fallos.
- Observabilidad: health endpoints, métricas (Prometheus), logs centralizados (EFK/ELK).
- Seguridad: gestión de secretos en Kubernetes (Secrets), TLS para servicios, validar inputs y aplicar roles.
- Mantenibilidad: aplicar SOLID/DRY, tests unitarios y de integración, CI/CD para builds y despliegues.

Recomendaciones prácticas
- En Minikube, usar `eval $(minikube docker-env)` para evitar push a registry.
- Configurar readiness/liveness probes en Kubernetes.
- Proteger credenciales con Kubernetes Secrets o herramientas como HashiCorp Vault.
- Documentar valores en `helm/values.yaml` para facilitar customización.

Contacto y soporte
- Incluir en el repo instrucciones de CI/CD (GitHub Actions/GitLab CI) para build/push de imágenes y despliegues automáticos si se desea.

Fin.