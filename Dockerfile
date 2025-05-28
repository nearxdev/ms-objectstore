# Etapa 1: Build da aplicação com Maven
FROM maven:3.9.9-eclipse-temurin-24-alpine AS build

WORKDIR /app

# Copiar todos os arquivos do projeto
COPY . .

# Fazer o build do projeto e pular os testes
RUN mvn clean package -DskipTests

# Etapa 2: Imagem final, somente com o JAR
FROM eclipse-temurin:24-jre-alpine AS production

WORKDIR /app

# Copiar o JAR gerado do build
COPY --from=build /app/target/*.jar app.jar
# Comando para rodar o JAR
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=prod", "/app/app.jar"]
