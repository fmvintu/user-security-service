FROM openjdk:8-jre-alpine
COPY target/dependencies /app/dependencies
COPY target/classes /app/classes
ENTRYPOINT java -cp /app/dependencies/*:/app/classes br.com.user.security.UserSecurityApplication
EXPOSE 8090
