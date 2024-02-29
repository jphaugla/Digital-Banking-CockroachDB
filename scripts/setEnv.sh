# if running on azure app-node set crdb host to the haproxy internal address
export COCKROACH_URL="jdbc:postgresql://${COCKROACH_HOST}:${COCKROACH_PORT}/defaultdb?sslmode=verify-full&sslrootcert=../certs/ca.crt"
export COCKROACH_HOST=localhost
export COCKROACH_PORT=26257
export COCKROACH_DB_USER=jhaugland
export USE_SSL=false
# substitute in actual KAFKA_HOST internal IP
# this will not run using a public ip-use the private ip on app node.  Did not set up the advertised listeners in kafka to make this work
export KAFKA_HOST=localhost
export KAFKA_PORT=9092
