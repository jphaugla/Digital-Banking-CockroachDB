FROM cnfldemos/cp-server-connect-datagen:0.6.2-7.5.0
ENV POSTGRES_DRIVER_VERSION 42.7.1
RUN confluent-hub install --no-prompt confluentinc/kafka-connect-jdbc:latest
RUN cd /usr/share/confluent-hub-components/confluentinc-kafka-connect-jdbc/lib && wget https://jdbc.postgresql.org/download/postgresql-42.7.1.jar 
