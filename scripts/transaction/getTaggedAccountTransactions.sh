export searchTag=$1
# get all tags on an account 
# 
curl -X GET -H "Content-Type: application/json"  "http://localhost:8080/api/transaction/getTagged?accountId=a72cc81e-6ee1-4a3f-b25e-7db296c68741&tag=${searchTag}"
