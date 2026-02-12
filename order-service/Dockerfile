# Build stage: build the JAR with Maven
FROM maven:3.9-eclipse-temurin-21 AS builder
WORKDIR /build

COPY pom.xml .
COPY order-service order-service
COPY discovery-server/pom.xml discovery-server/
COPY product-service/pom.xml product-service/
COPY inventory-service/pom.xml inventory-service/
RUN mvn -f pom.xml package -pl order-service -DskipTests -B

# Run stage
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=builder /build/order-service/target/order-service-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]
