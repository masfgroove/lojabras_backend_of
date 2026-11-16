# Etapa 1: Build da aplicação usando Maven
FROM eclipse-temurin:17-jdk-jammy AS build
WORKDIR /app
COPY . .

# Dá permissão de execução ao script do Maven Wrapper
RUN chmod +x ./mvnw

# Executa o build sem testes
RUN ./mvnw clean package -DskipTests

# Etapa 2: Execução da aplicação
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# O Render define dinamicamente a porta na variável de ambiente $PORT.
# O Spring Boot vai ler essa porta através do parâmetro server.port
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar", "--server.port=${PORT:-8080}"]