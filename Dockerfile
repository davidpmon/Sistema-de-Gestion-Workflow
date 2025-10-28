# Imagen base de Java
FROM eclipse-temurin:17-jdk

# Directorio de trabajo
WORKDIR /app

# Copiar archivos del proyecto
COPY . .

# Construir el JAR
RUN ./mvnw clean package -DskipTests

# Ejecutar la app
CMD ["java", "-jar", "target/*.jar"]
