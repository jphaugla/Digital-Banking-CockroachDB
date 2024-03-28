#  easiest to look up an account using redinsight and edit this script to find an existing account
# date can be looked up and put in range
curl -X GET -H "Content-Type: application/json"  'http://localhost:8080/api/transaction/account?accountId=3bb4822a-4a83-42e4-a9cd-ad095d0eaed3&from=2023-07-21&to=2024-11-21'
