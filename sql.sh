if [ $# -lt 1 ]; then
    echo 'must provide node index from 0 to 3'
    exit 1
fi
export NODE=$1
export BASEVALUE=26256
# node0 is actually the loadbalancer
echo 'NODE is ' ${NODE} 
(( PORT = ${BASEVALUE} + ${NODE} ))
echo 'PORT is ' ${PORT} 
cockroach sql --insecure --host=localhost:${PORT}
