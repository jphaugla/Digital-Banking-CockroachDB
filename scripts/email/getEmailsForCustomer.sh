# get using a customer id.  Use getByEmail.sh or getByPhone.sh to get the generated UUID
curl -X GET -H "Content-Type: application/json"  'http://localhost:5001/api/email/getCustomerEmail?customerId=c92d312e-635c-45c8-b69b-d76aca6f4652'
