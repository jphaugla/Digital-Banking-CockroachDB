# get using a customer id.  Use getByEmail.sh or getByPhone.sh to get the generated UUID
curl -X GET -H "Content-Type: application/json"  'http://localhost:8080/api/dispute/get?disputeId=silly'
