export newTag=Food
#  add a tag to a transaction.  Tags allow user to mark  transactions to be in a buckets such as Travel or Food for budgetary tracking purposes
# date can be looked up and put in range
curl -X PUT -H "Content-Type: application/json"  "http://localhost:8080/api/transaction/addTag?transactionId=0000342e-cf94-4a8d-868f-c22b7597b465&tag=${newTag}&operation=add"
