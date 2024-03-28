# get using a customer id.  Use getByEmail.sh or getByPhone.sh to get the generated UUID
curl -X GET -H "Content-Type: application/json"  'http://localhost:8080/api/email/getCustomerEmail?customerId=4cd81f17-e532-456a-a721-71cc091b6f0d'
