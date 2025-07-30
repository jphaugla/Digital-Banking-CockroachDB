#!/bin/bash
source scripts/setEnv.sh; java -jar target/cockroach-0.0.1-SNAPSHOT.jar  --spring.datasource.url="${COCKROACH_URL}" --spring.datasource.username="${COCKROACH_DB_USER}" --spring.datasource.password="${COCKROACH_DB_PASS}"
