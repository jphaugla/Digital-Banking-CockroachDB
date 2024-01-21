export newTag=Super
#  add a tag to a transaction.  Tags allow user to mark  transactions to be in a buckets such as Travel or Food for budgetary tracking purposes
# date can be looked up and put in range
curl -X PUT -H "Content-Type: application/json"  "http://localhost:8080/api/transaction/addTag?transactionId=bdb5af85-92b0-4ee7-b990-6c8403efb83d&tag=${newTag}&operation=delete"
