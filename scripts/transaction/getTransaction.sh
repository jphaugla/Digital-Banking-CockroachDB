# get one transaction by ID  
# test invalid
# curl -X GET -H "Content-Type: application/json"  'http://localhost:8080/api/transaction/get?transactionId=0000342e-cf94-4a8d-'
curl -X GET -H "Content-Type: application/json"  'http://localhost:8080/api/transaction/get?transactionId=ffde473d-4cc9-45f8-bf79-2b865901ad7f'
