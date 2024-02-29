export TGT_HAPROXY=192.168.3.101
export PASSWORD=jasonrocks
export CERT_DIR=../certs
export URL_REQUIRE="postgresql://jhaugland:${PASSWORD}@${TGT_HAPROXY}:26257/defaultdb?sslmode=require"
export URL_VERIFY_FULL="postgresql://jhaugland@${TGT_HAPROXY}:26257/?sslmode=verify-full&sslrootcert=${CERT_DIR}/ca.crt&sslcert=${CERT_DIR}/client.jhaugland.crt&sslkey=${CERT_DIR}/client.jhaugland.key"
export URL_VERIFY_CA="postgresql://jhaugland:${PASSWORD}@${TGT_HAPROXY}:26257/?sslmode=verify-ca&sslrootcert=${CERT_DIR}/ca.crt"
echo "this is URL "${URL_VERIFY_CA}
# this works
# cockroach-sql sql --url=${URL_REQUIRE}
# this works
# cockroach-sql sql --url=${URL_VERIFY_FULL}
cockroach-sql sql --url=${URL_VERIFY_CA}
