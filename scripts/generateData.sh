# This script uses an API call to generate sample banking customers, 
# accounts and transactions.  It uses Spring ASYNC techniques to 
# generate higher load.  A flag chooses between running the transactions 
# pipelined in Redis or in normal non-pipelined method.
curl 'http://localhost:8080/api/transaction/generateData?noOfCustomers=50&noOfTransactions=1000&noOfDays=5&key_suffix=J&doKafka=true'
# with ssl
# curl -cacert='..//src/main/resources/ssl/proxy-cert.pem' 'http://localhost:8080/generateData?noOfCustomers=500&noOfTransactions=100000&noOfDays=5&key_suffix=J'
