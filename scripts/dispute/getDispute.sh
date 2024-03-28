# get using a customer id.  Use getByEmail.sh or getByPhone.sh to get the generated UUID
curl -X GET -H "Content-Type: application/json"  'http://localhost:8080/api/dispute/get?disputeId=fa3814f7-6c7b-4ac7-aa19-7e3de8a4301f'
