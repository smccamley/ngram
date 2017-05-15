FROM java:8-jre
COPY /index /opt/dropwizard/download/index
COPY ngramdocker.yml /opt/dropwizard/
COPY build/libs/ngram.jar /opt/dropwizard
WORKDIR /opt/dropwizard
CMD ["java", "-Xms1g", "-Xmx3g", "-Dfile.encoding=UTF-8", "-jar", "ngram.jar", "server", "ngramdocker.yml"]