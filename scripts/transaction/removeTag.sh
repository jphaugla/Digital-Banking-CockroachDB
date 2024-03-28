export newTag=Super
#  add a tag to a transaction.  Tags allow user to mark  transactions to be in a buckets such as Travel or Food for budgetary tracking purposes
# date can be looked up and put in range
curl -X PUT -H "Content-Type: application/json"  "http://localhost:8080/api/transaction/addTag?transactionId=ffde473d-4cc9-45f8-bf79-2b865901ad7f&tag=${newTag}&operation=delete"
