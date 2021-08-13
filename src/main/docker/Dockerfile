FROM java:8
MAINTAINER guanpeng
EXPOSE 3090
RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime
RUN echo 'Asia/Shanghai' >/etc/timezone
ADD jdc-monitor-api-1.0.0-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar","--mpw.key=11b3d71073af8b31"]
