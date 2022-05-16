FROM openjdk:11
ADD target/simplesystem-1.0.jar simplesystem.jar
ENTRYPOINT ["java", "-jar", "simplesystem.jar"]