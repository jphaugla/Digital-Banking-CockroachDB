export COCKROACH_HOST=localhost
export COCKROACH_PORT=26256
export COCKROACH_USER=jhaugland
export USE_SSL=false
# substitue in actual KAFKA_HOST internal IP
# this will not run using a public ip-use the private ip on app node.  Did not set up the advertized listeners in kafka to make this work
export KAFKA_HOST=localhost
export KAFKA_PORT=9092
