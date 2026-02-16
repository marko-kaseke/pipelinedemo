# ---------- BUILD STAGE ----------
FROM maven:3.9.8-eclipse-temurin-21 AS build
WORKDIR /app

# Copy pom.xml first to cache dependencies
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy source code and build
COPY src ./src
RUN mvn -q clean package -DskipTests

# ---------- RUNTIME STAGE ----------
FROM eclipse-temurin:21-jre
WORKDIR /app

# Copy the built jar from build stage
COPY --from=build /app/target/*.jar sourceafs.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "sourceafs.jar"]
