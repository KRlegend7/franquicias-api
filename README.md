# API de Gestión de Franquicias

Esta API permite gestionar franquicias, sus sucursales y los productos ofrecidos en cada sucursal.

## Descripción

La API está construida con Spring Boot y utiliza MongoDB como base de datos. Ofrece endpoints para:
- Gestionar franquicias (crear, actualizar nombre)
- Gestionar sucursales (agregar, actualizar nombre)
- Gestionar productos (agregar, eliminar, modificar stock, actualizar nombre)
- Obtener estadísticas como el producto con más stock por sucursal

## Requisitos

- Java 11 o superior
- Maven 3.8 o superior
- MongoDB 5.0 o superior (o acceso a MongoDB Atlas)
- Docker y Docker Compose (opcional, para despliegue con contenedores)

## Estructura del Proyecto

```
franquicias-api/
src/
├── main/
│ ├── java/
│ │ └── com/
│ │ └── franquicias/
│ │ ├── controller/ # Controladores REST
│ │ ├── model/ # Entidades
│ │ ├── repository/ # Repositorios de MongoDB
│ │ ├── service/ # Servicios de negocio
│ │ ├── exception/ # Manejo de excepciones
│ │ ├── dto/ # Objetos de transferencia de datos
│ │ └── FranquiciasApplication.java # Clase principal
│ └── resources/
│ └── application.properties # Configuración
├── Dockerfile
├── docker-compose.yml
├── main.tf # Configuración de Terraform para MongoDB Atlas
├── variables.tf
└── pom.xml
```

## Despliegue

### Opción 1: Despliegue en la Nube con MongoDB Atlas (Recomendado para Producción)

#### Provisión de Infraestructura con Terraform

1. Crea una cuenta en MongoDB Atlas (https://www.mongodb.com/cloud/atlas)
2. Instala Terraform (https://www.terraform.io/downloads.html)
3. Configura las variables de Terraform:

```bash
export TF_VAR_mongodb_atlas_api_pub_key="tu-clave-publica"
export TF_VAR_mongodb_atlas_api_pri_key="tu-clave-privada"
export TF_VAR_mongodb_atlas_org_id="tu-id-organizacion"
export TF_VAR_mongodb_atlas_user_password="tu-contraseña"
   ```

4. Inicializa y aplica la configuración de Terraform:

```bash
terraform init
terraform apply
   ```

5. Actualiza la configuración de conexión a MongoDB en application.properties con la URI de conexión proporcionada por Terraform


### Opción 2: Despliegue Local con Docker

1. Clone este repositorio:
   ```bash
   git clone https://github.com/KRlegend7/franquicias-api.git
   cd franquicias-api
   ```

2. Construya y ejecute los contenedores con Docker Compose:
   ```bash
   docker-compose up -d
   ```

4. La API estará disponible en: `http://localhost:8080`

### Opción 3: Despliegue Local sin Docker

1. Asegúrate de tener MongoDB ejecutándose en tu máquina local en el puerto 27017
2. Clona este repositorio
3. Navega al directorio del proyecto
4. Ejecuta la aplicación con Maven:

```bash
mvn spring-boot:run
   ```

4. La API estará disponible en: `http://localhost:8080`

## Endpoints de la API

### Franquicias

- **GET /api/franquicias**: Obtener todas las franquicias
- **GET /api/franquicias/{franquiciaId}**: Obtener una franquicia por ID
- **POST /api/franquicias**: Crear una nueva franquicia
- **PUT /api/franquicias/{franquiciaId}/nombre**: Actualizar el nombre de una franquicia

### Sucursales

- **POST /api/franquicias/{franquiciaId}/sucursales**: Agregar una nueva sucursal a una franquicia
- **PUT /api/franquicias/{franquiciaId}/sucursales/{sucursalId}/nombre**: Actualizar el nombre de una sucursal

### Productos

- **POST /api/franquicias/{franquiciaId}/sucursales/{sucursalId}/productos**: Agregar un nuevo producto a una sucursal
- **DELETE /api/franquicias/{franquiciaId}/sucursales/{sucursalId}/productos/{productoId}**: Eliminar un producto de una sucursal
- **PUT /api/franquicias/{franquiciaId}/sucursales/{sucursalId}/productos/{productoId}/stock**: Actualizar el stock de un producto
- **PUT /api/franquicias/{franquiciaId}/sucursales/{sucursalId}/productos/{productoId}/nombre**: Actualizar el nombre de un producto

### Consultas Especiales

- **GET /api/franquicias/{franquiciaId}/productos-mas-stock**: Obtener el producto con más stock por sucursal en una franquicia


### Archivo de configuración para CI/CD con GitHub Actions
```yaml
# Archivo: .github/workflows/ci-cd.yml
name: CI/CD Pipeline

on:
  push:
    branches: [ main ]
  pull_request:
    branches: [ main ]

jobs:
  build-and-test:
    runs-on: ubuntu-latest

    steps:
    - uses: actions/checkout@v2
    
    - name: Set up JDK 11
      uses: actions/setup-java@v2
      with:
        java-version: '11'
        distribution: 'adopt'
        
    - name: Build with Maven
      run: mvn -B package --file pom.xml
      
    - name: Run tests
      run: mvn test

  build-and-push-docker:
    needs: build-and-test
    runs-on: ubuntu-latest
    if: github.event_name == 'push' && github.ref == 'refs/heads/main'
    
    steps:
    - uses: actions/checkout@v2
    
    - name: Set up Docker Buildx
      uses: docker/setup-buildx-action@v1
      
    - name: Login to DockerHub
      uses: docker/login-action@v1
      with:
        username: ${{ secrets.DOCKERHUB_USERNAME }}
        password: ${{ secrets.DOCKERHUB_TOKEN }}
        
    - name: Build and push Docker image
      uses: docker/build-push-action@v2
      with:
        context: .
        push: true
        tags: ${{ secrets.DOCKERHUB_USERNAME }}/api-franquicias:latest
  ```

  ### Script para prueba rápida con cURL
```bash
# Archivo: test-api.sh
#!/bin/bash
# Configuración
API_URL="http://localhost:8080/api"
FRANQUICIA_ID=""
SUCURSAL_ID=""
PRODUCTO_ID=""

# Crear una franquicia
echo "Creando franquicia..."
RESPONSE=$(curl -s -X POST "${API_URL}/franquicias" \
  -H "Content-Type: application/json" \
  -d '{"nombre": "Mi Franquicia"}')
FRANQUICIA_ID=$(echo $RESPONSE | grep -o '"id":"[^"]*' | cut -d'"' -f4)
echo "Franquicia creada con ID: $FRANQUICIA_ID"

# Agregar una sucursal
echo "Agregando sucursal..."
RESPONSE=$(curl -s -X POST "${API_URL}/franquicias/${FRANQUICIA_ID}/sucursales" \
  -H "Content-Type: application/json" \
  -d '{"nombre": "Sucursal Central"}')
SUCURSAL_ID=$(echo $RESPONSE | grep -o '"sucursales":$$.*$$' | grep -o '"id":"[^"]*' | head -1 | cut -d'"' -f4)
echo "Sucursal agregada con ID: $SUCURSAL_ID"

# Agregar productos
echo "Agregando productos..."
RESPONSE=$(curl -s -X POST "${API_URL}/franquicias/${FRANQUICIA_ID}/sucursales/${SUCURSAL_ID}/productos" \
  -H "Content-Type: application/json" \
  -d '{"nombre": "Producto A", "stock": 100}')
PRODUCTO_ID=$(echo $RESPONSE | grep -o '"productos":$$.*$$' | grep -o '"id":"[^"]*' | head -1 | cut -d'"' -f4)
echo "Producto agregado con ID: $PRODUCTO_ID"

curl -s -X POST "${API_URL}/franquicias/${FRANQUICIA_ID}/sucursales/${SUCURSAL_ID}/productos" \
  -H "Content-Type: application/json" \
  -d '{"nombre": "Producto B", "stock": 50}'

# Modificar stock
echo "Modificando stock del producto..."
curl -s -X PUT "${API_URL}/franquicias/${FRANQUICIA_ID}/sucursales/${SUCURSAL_ID}/productos/${PRODUCTO_ID}/stock" \
  -H "Content-Type: application/json" \
  -d '{"nuevoStock": 200}'

# Obtener producto con más stock
echo "Obteniendo producto con más stock por sucursal..."
curl -s -X GET "${API_URL}/franquicias/${FRANQUICIA_ID}/productos-mas-stock" | json_pp

echo "Pruebas completadas"

  ```


## Contribución

1. Haga un Fork del proyecto
2. Cree una rama para su funcionalidad (`git checkout -b feature/nueva-funcionalidad`)
3. Comitee sus cambios (`git commit -m 'Añadir nueva funcionalidad'`)
4. Haga push a la rama (`git push origin feature/nueva-funcionalidad`)
5. Abra un Pull Request

## Autor
Kevin Ramirez
