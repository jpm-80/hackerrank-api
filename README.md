Proyecto Spring Boot: API País y Teléfono
Descripción
Este proyecto es una aplicación Spring Boot con Java 17, diseñada para consumir la API pública de HackerRank (https://jsonmock.hackerrank.com/api/countries). 
El objetivo principal es recibir un país y un número de teléfono como parámetros HTTP (RequestParam) y devolver el número en formato internacional, es decir, concatenando el símbolo "+" y el código de país extraído de la API a partir del país recibido.

Ejemplo de uso:
Solicitud: GET /hackerrank/v1?country=Angola&phone=9543104545
Respuesta: +244 9543104545

No se utilizan dependencias adicionales, más allá de lo que trae Java 17 y Spring Boot, ni utilidades de Spring para el consumo de servicios REST: el consumo de la API remota se realiza exclusivamente con HttpClient y HttpRequest.

Características principales
Implementado en Java 17 y Spring Boot (sin dependencias agregadas en el pom.xml).
Consumo de API REST de países usando únicamente clases estándar de Java (HttpClient, HttpRequest).
Recolección del código internacional del país a partir del nombre recibido como parámetro.

Múltiples métodos de extracción de la información deseada:
1. Con Matcher y Pattern (expresiones regulares)
2. Mediante métodos básicos de String
3. Usando Scanner

Salida estandarizada: +<código de país> <número de teléfono>

Instalación
Requisitos previos:
Java 17 o superior
Maven
Git

Clone el repositorio:
bash
git clone <url-del-repositorio>
cd <nombre-del-proyecto>
Construcción:

bash
mvn clean install

Ejecución:
bash
mvn spring-boot:run
Por defecto, la aplicación se inicia en http://localhost:8080.

Uso
Endpoint principal: /hackerrank/v1

Parámetros:

country (String): Nombre del país a buscar
phone (String): Número de teléfono

Ejemplo de solicitud:
text
GET http://localhost:8080/hackerrank/v1?name=Brazil&phone=1199999999
Ejemplo de Respuesta:

text
+55 1199999999
Implementación técnica
El consumo de la API se realiza usando:

HttpClient y HttpRequest para realizar la solicitud HTTP.
Procesamiento de la respuesta JSON sin usar librerías de parsing (ni Gson, ni Jackson, ni JsonPath).
La extracción del código internacional se implementa de tres formas, todas accesibles desde el controlador:

A) Matcher y Pattern: Uso de expresiones regulares para buscar el código del país en la respuesta JSON.
B) String Methods: Manipulación de cadenas para localizar el código de país.
C) Scanner: Uso de la clase Scanner para recorrer el JSON línea por línea y extraer el dato necesario.

Recomiendo revisar la clase del controlador para ver las diferencias entre los tres métodos.

Estructura del proyecto
text
src/
 └── main/
      ├── java/
      │    └── com/tuempresa/paquete/
      │         ├── Application.java
      │         ├── controller/
      │         │      └── PhoneController.java
      │         ├── service/
      │         │      ├── CountryCodeServicePattern.java
      │         │      ├── CountryCodeServiceString.java
      │         │      └── CountryCodeServiceScanner.java
      └── resources/
           └── application.properties
pom.xml
README.md
Ejemplo de código de consumo de API (HttpClient)
java
HttpClient client = HttpClient.newHttpClient();
HttpRequest request = HttpRequest.newBuilder()
    .uri(URI.create("https://jsonmock.hackerrank.com/api/countries?name=" + URLEncoder.encode(country, "UTF-8")))
    .GET()
    .build();

HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
String json = response.body();
// Aquí llamar a la lógica para extraer el código de país
Notas
No se usan dependencias externas de parseo JSON ni más paquetes de Spring que los mínimos requeridos para levantar un REST controller.
El proyecto ejemplifica buenas prácticas de aislamiento de lógica y simplicidad en el consumo de servicios externos solo con Java estándar.

Autor
Este proyecto fue creado por [Tu Nombre].
Si tienes sugerencias, dudas o encuentras un error, por favor crea un issue o contacta a [Tu email/profesional]
