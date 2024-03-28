# get using a customer id.  Use getByEmail.sh or getByPhone.sh to get the generated UUID
curl -X GET -H "Content-Type: application/json"  'http://localhost:8080/api/phone/getCustomerPhone?customerId=9f8283f3-d30e-4279-80b2-acec7d3e2c7a'
