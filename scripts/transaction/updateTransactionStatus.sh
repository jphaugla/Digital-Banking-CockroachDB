#not working
# generate new transactions to move all of one transaction 
# Status up to the next transaction status. Parameter is target status.
#  Can choose SETTLED or POSTED
curl -X PUT -H "Content-Type: application/json"  'http://localhost:5001/api/transaction/statusChange?targetStatus=POSTED&numberOfTransactions=10000'
