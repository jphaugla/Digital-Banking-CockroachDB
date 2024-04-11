# for server testing to generate higher load levels.
# Use with startAppservers.sh
nohup curl 'http://localhost:8080/api/transaction/generateData?noOfCustomers=5000&noOfTransactions=100000&noOfDays=10&key_suffix=J&doKafka=false' > /tmp/generateJ.out 2>&1 &
nohup curl 'http://localhost:8081/api/transaction/generateData?noOfCustomers=5000&noOfTransactions=100000&noOfDays=10&key_suffix=P&doKafka=false' > /tmp/generateP.out 2>&1 &
nohup curl 'http://localhost:8082/api/transaction/generateData?noOfCustomers=5000&noOfTransactions=100000&noOfDays=10&key_suffix=H&doKafka=false' > /tmp/generateH.out 2>&1 &
nohup curl 'http://localhost:8083/api/transaction/generateData?noOfCustomers=5000&noOfTransactions=100000&noOfDays=10&key_suffix=C&doKafka=false' > /tmp/generateC.out 2>&1 &
nohup curl 'http://localhost:8084/api/transaction/generateData?noOfCustomers=5000&noOfTransactions=100000&noOfDays=10&key_suffix=A&doKafka=false' > /tmp/generateA.out 2>&1 &
nohup curl 'http://localhost:8085/api/transaction/generateData?noOfCustomers=5000&noOfTransactions=100000&noOfDays=10&key_suffix=G&doKafka=false' > /tmp/generateG.out 2>&1 &
