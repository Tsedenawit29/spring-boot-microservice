# Build stage: build the JAR with Maven
FROM maven:3.9-eclipse-temurin-21 AS builder
WORKDIR /build

COPY pom.xml .
COPY product-service product-service
COPY discovery-server/pom.xml discovery-server/
COPY order-service/pom.xml order-service/
COPY inventory-service/pom.xml inventory-service/
RUN mvn -f pom.xml package -pl product-service -DskipTests -B

# Run stage
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=builder /build/product-service/target/product-service-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
