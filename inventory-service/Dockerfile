# Build stage: build the JAR with Maven
FROM maven:3.9-eclipse-temurin-21 AS builder
WORKDIR /build

COPY pom.xml .
COPY inventory-service inventory-service
COPY discovery-server/pom.xml discovery-server/
COPY product-service/pom.xml product-service/
COPY order-service/pom.xml order-service/
RUN mvn -f pom.xml package -pl inventory-service -DskipTests -B

# Run stage
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=builder /build/inventory-service/target/inventory-service-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "app.jar"]
