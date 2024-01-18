# get one transaction by ID  
# test invalid
# curl -X DELETE -H "Content-Type: application/json"  'http://localhost:5001/api/transaction/delete?transactionId=0000342e-cf94-4a8d-'
curl -X DELETE -H "Content-Type: application/json"  'http://localhost:5001/api/transaction/delete?transactionId=0000342e-cf94-4a8d-868f-c22b7597b465'
