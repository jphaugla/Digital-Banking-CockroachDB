#  easiest to look up an account using redinsight and edit this script to find an existing account
# date can be looked up and put in range
curl -X GET -H "Content-Type: application/json"  'http://localhost:8080/api/transaction/account?accountId=2fde8b74-053f-4255-b6ae-82622476ab1f&from=2023-07-21&to=2024-11-21'
