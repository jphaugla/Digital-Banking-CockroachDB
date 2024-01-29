curl -X POST -H "Content-Type: application/json" --data '
{
 "name": "cockroach-sink-json", "config": {  "connector.class": "io.confluent.connect.jdbc.JdbcSinkConnector", "connection.url": "jdbc:postgresql://haproxy:26256/defaultdb?sslmode=disable", "topics": "transactions", "tasks.max": "4", "key.converter": "org.apache.kafka.connect.storage.StringConverter", "value.converter": "org.apache.kafka.connect.json.JsonConverter", "connection.user": "jhaugland", "auto.create": true, "auto.evolve": true, "insert.mode": "insert", "pk.mode": "none", "pk.fields": "none", "batch.size": 128, "consumer.override.max.poll.records": 128 } 
} ' http://connect:8083/connectors -w "\n"
