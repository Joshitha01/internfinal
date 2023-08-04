FROM eclipse-temurin:17-jre-jammy

WORKDIR /app

COPY --from=builder /app/target/*.jar /app/app.jar

EXPOSE 8080

ENTRYPOINT [ "java","-jar","/app/app.jar"]
