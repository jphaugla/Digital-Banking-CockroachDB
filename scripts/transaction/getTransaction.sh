# get one transaction by ID  
# test invalid
# curl -X GET -H "Content-Type: application/json"  'http://localhost:5001/api/transaction/get?transactionId=0000342e-cf94-4a8d-'
curl -X GET -H "Content-Type: application/json"  'http://localhost:5001/api/transaction/get?transactionId=0000342e-cf94-4a8d-868f-c22b7597b465'
