FROM openjdk:8
LABEL maintainer="javaguides.net"
EXPOSE 8079
ADD ./build/libs/Paypal-Payment-0.0.1-SNAPSHOT.jar paypal-payment.jar
ENTRYPOINT ["java", "-jar", "paypal-payment.jar"]

