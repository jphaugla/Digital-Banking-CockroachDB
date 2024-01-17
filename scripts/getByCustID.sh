# get using a customer id.  Use getByEmail.sh or getByPhone.sh to get the generated UUID
curl -X GET -H "Content-Type: application/json"  'http://localhost:5001/api/customer/get?customerId=9e9e54f9-cae7-43bc-b89b-7091971d0001'
