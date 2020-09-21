FROM openjdk:8-jre
ENV IGNITE_CLIENT_MODE false
ADD ./target/marina-test-service-1.1.jar /app/marina-test-service-1.1.jar
CMD ["java", "-Xdebug", "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005", "-jar", "/app/marina-test-service-1.1.jar"]