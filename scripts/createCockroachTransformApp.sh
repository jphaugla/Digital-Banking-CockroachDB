#  the connection.uri needs the internal haproxy address
#  the final ip address is thte public kafka address
curl -X POST -H "Content-Type: application/json" --data '
{
 "name": "cockroach-sink-json-transform", "config": {
    "connector.class": "io.confluent.connect.jdbc.JdbcSinkConnector",
    "tasks.max": "2",
    "key.converter": "org.apache.kafka.connect.storage.StringConverter",
    "value.converter": "org.apache.kafka.connect.json.JsonConverter",
    "errors.log.enable": "true",
    "errors.log.include.messages": "true",
    "topics": "transaction",
    "connection.url": "jdbc:postgresql://192.168.3.101:26257/defaultdb?sslmode=require",
    "connection.user": "jhaugland",
    "connection.password": "jasonrocks",
    "dialect.name": "PostgreSqlDatabaseDialect",
    "insert.mode": "upsert",
    "pk.mode": "record_value",
    "pk.fields": "id",
    "auto.create": "true",
    "transforms": "RenameField",
    "transforms.RenameField.type": "org.apache.kafka.connect.transforms.ReplaceField$Value",
    "transforms.RenameField.renames": "accountId:account_id,amountType:amount_type,disputeId:dispute_id,initialDate:initial_date,originalAmount:original_amount,postingDate:posting_date,referenceKeyValue:reference_key_value,tranCode:tran_code,referenceKeyType:reference_key_type,settlementDate:settlement_date,transactionReturn:transaction_return"
}
} ' http://40.77.53.105:8083/connectors -w "\n"
