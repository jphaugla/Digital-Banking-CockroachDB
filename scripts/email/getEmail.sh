# retrieve customer record using email address
# get by email only
#  for error handle testing
# curl -X GET -H "Content-Type: application/json"  'http://localhost:8080/api/email/getEmail?email=jasonhaugland@'
curl -X GET -H "Content-Type: application/json"  'http://localhost:8080/api/email/getEmail?email=jasonhaugland@gmail.com'
