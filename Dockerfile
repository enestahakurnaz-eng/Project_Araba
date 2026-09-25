FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY . .
RUN javac src/Project_Araba/*.java
CMD ["java", "-cp", "src", "Project_Araba.Main"]