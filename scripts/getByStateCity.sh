# get customers by city and state
curl -X GET -H "Content-Type: application/json"  'http://localhost:5001/api/customer/getStateCity?state=MN&city=Minneapolis'
