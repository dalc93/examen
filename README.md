
X-MEN
=============
Este proyecto se encarga de verificar si una persona es mutante de acuerdo a su cadena de ADN.

Comenzando
-------------
Estas instrucciones te permitiran utilizar este servicio REST de manera local

**Pre-requisitos**
Tener instalada la version 11 de java.
Tener un editor de código ya sea intelliJ IDEA, VSCode, Eclipse etc.
Tener instalado MySQL o un servicio como XAMPP para tener una Base de Datos en Local.

**Instalación**
Descargar o clonar el proyecto y abrirlo en un editor de código de su preferencia.
Iniciar el servicio se base de datos MySQL y crear la base de datos con el nombre 'mutantes', el servicio esta configurado para ejecutarse en el puerto 3306, en caso de requerir una configuracion adicional, modificarlo en la ruta de recursos del servicio el archivo application.properties.
En este archivo podemos definir el nombre de la Base de Datos, el puerto, el usuario y la clave con el que se conecta a la BD.
Una vez se clone el proyecto, ejecutarlo a través de su editor de código preferido.

Para poder realizar el consumo del servicio API REST, se debe crear un nuevo Request desde Postman con la siguiente URL

`http://ec2-35-153-79-14.compute-1.amazonaws.com:8080/mutantes/mutant`
Si es un ambiente local cambiar la ruta 'http://ec2-35-153-79-14.compute-1.amazonaws.com' por localhost.
Este Método se consume a través del Método POST y se envie el siguiente JSON de prueba en el body del request.
`{
    "dna": [
        "ATGCGA",
        "CAGTGC",
        "TTATGT",
        "AGAAGG",
        "CCCCTA",
        "TCACTG"
    ]
}`

El servicio REST cuenta con una base de datos y almacena la información de las secuencias de ADN, de tal manera tambien existe un servicio en la ruta /mutantes/stats la cual nos da una estadística de las secuencias de ADN validadas.
`http://ec2-35-153-79-14.compute-1.amazonaws.com:8080/mutantes/stats`
Este servicio nos responde con la cantidad de personas mutantes y personas no mutantes, junto con un ratio.
`{
    "count_mutant_dna": 1,
    "count_human_dna": 1,
    "ratio": 1,
}`

Ejecución de Pruebas Unitarias
-------------
El servicio cuenta con sus pruebas unitarias y un reporte que se genera para revisar la cobertura que este posee. Para la ejecucion de pruebas y reporte ubiquese en la carpeta del proyecto y ejecute el siguiente comando:
`mvn clean test`
Se empezara a ejecutar las pruebas y el reporte se genera en la ruta del proyecto dentro de la carpeta "target\site\jacoco\index.html"

Despliegue y consumo
-------------
Actualmente el servicio REST se encuentra desplegado en una cuenta de AWS en una instancia EC2, con un servidor ubuntu, para poder consumir el servicio en Producción se debe crear en Postman un nuevo request con la siguiente URL y puerto
`http://ec2-35-153-79-14.compute-1.amazonaws.com:8080`
El servicio tiene la siguiente ruta 
`/mutantes/mutant`
En donde recibe un JSON en el body del request para validar si una persona es mutante o no. 

**Respuestas del Servicio**
- 200 - OK = cadena de ADN enviada corresponde a una persona mutante
- 403 - Forbidden = cadena de ADN enviada NO corresponde a una persona mutante

Construido con
-------------
- Java 11
- Editor de codigo IntelliJ
- SpringBoot

Autor
-------------
Diego Armando Leal Castellanos - Documentación y Desarrollo