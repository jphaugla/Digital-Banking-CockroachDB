# delete cuatomer and associated email and phone 
# use getPhone.sh or getEmail.sh to get UUID for a customer
curl -X DELETE -H "Content-Type: application/json"  'http://localhost:5001/api/customer/delete?customerId=9e9e54f9-cae7-43bc-b89b-7091971d0001'
