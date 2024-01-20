export searchTag=$1
# get all tags on an account 
# 
curl -X GET -H "Content-Type: application/json"  "http://localhost:8080/api/transaction/getTagged?accountId=2fde8b74-053f-4255-b6ae-82622476ab1f&tag=${searchTag}"
