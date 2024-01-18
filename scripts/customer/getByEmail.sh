# retrieve customer record using email address
# get by email only
curl -X GET -H "Content-Type: application/json"  'http://localhost:5001/api/customer/email?email_string=jasonhaugland@gmail.com'
