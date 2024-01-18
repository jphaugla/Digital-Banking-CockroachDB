# get using a customer id.  Use getByEmail.sh or getByPhone.sh to get the generated UUID
curl -X GET -H "Content-Type: application/json"  'http://localhost:5001/api/phone/getCustomerPhone?customerId=686bf612-7df1-4ae0-b1bf-ed9623fc5d5e'
