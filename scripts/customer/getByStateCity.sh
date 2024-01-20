# get customers by city and state
# curl -X GET -H "Content-Type: application/json"  'http://localhost:8080/api/customer/getStateCity?state=MN&city=Minneapolis'
curl -X GET -H "Content-Type: application/json"  'http://localhost:8080/api/customer/getStateCity?state=MN&city=xx'
