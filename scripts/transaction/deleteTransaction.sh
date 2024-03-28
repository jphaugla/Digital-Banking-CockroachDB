# get one transaction by ID  
# test invalid
# curl -X DELETE -H "Content-Type: application/json"  'http://localhost:8080/api/transaction/delete?transactionId=0000342e-cf94-4a8d-'
curl -X DELETE -H "Content-Type: application/json"  'http://localhost:8080/api/transaction/delete?transactionId=e36d0c28-9e5b-4f92-8258-09d80a6b4ee5'
