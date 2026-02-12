# Build stage: build the JAR with Maven (no need to run mvn package manually)
FROM maven:3.9-eclipse-temurin-21 AS builder
WORKDIR /build

# Copy root POM, all module POMs (for reactor), and discovery-server source
COPY pom.xml .
COPY discovery-server discovery-server
COPY product-service/pom.xml product-service/
COPY order-service/pom.xml order-service/
COPY inventory-service/pom.xml inventory-service/
# Build only the discovery-server module
RUN mvn -f pom.xml package -pl discovery-server -DskipTests -B

# Run stage
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=builder /build/discovery-server/target/discovery-server-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8761
ENTRYPOINT ["java", "-jar", "app.jar"]
