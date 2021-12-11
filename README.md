# Verificación y Validación del Software 2021

GRUPO 1 IWT-42  

## Instrucciones Maven

### Pre-requisitos 📋

- Para utilizar los comandos directamente en su terminal deber tener instalado, como mínimo, [Maven 3.6.3](https://downloads.apache.org/maven/maven-3/3.6.3/binaries/).

- También puede utilizar los comandos con el plugin de su IDE. En Eclipse: ```Run > Run Configurations… ``` 

### Ejecutar los test ⚙️

- Para compilar y ejecutar los test use el comando:
```
mvn test 
```

### Generar informes 📄
- Al ejecutar los test se generan informes en formato ```.txt``` y ```.xml``` en ```.target/surefire-reports```
- Para generar un informe en formato ```.html``` utilice el comando:
```
mvn surefire-report:report
```

### Empaquetar proyecto 📦

- Para empaquetar el proyecto en un ```.jar``` utilice:
```
mvn package
```
-  El fichero estará en ```./target```

### Limpiar fichero generados 🧹
- Para eliminar los ficheros generados en la carpeta ```./target``` use el comando:
```
mvn clean
```

 


