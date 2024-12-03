# Sử dụng hình ảnh chính thức của Java 17 với Alpine (nhẹ và hiệu quả)
FROM eclipse-temurin:17-jdk-alpine

# Đặt thư mục làm việc trong container
WORKDIR /app

# Sao chép file JAR từ local vào container
COPY target/BeFruit-0.0.1-SNAPSHOT.jar app.jar

# Khai báo cổng mà ứng dụng sẽ chạy
EXPOSE 8081

# Chạy ứng dụng Spring Boot
ENTRYPOINT ["java", "-jar", "app.jar"]
