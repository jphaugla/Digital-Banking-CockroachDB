#  easiest to look up a credit card using redinsight and edit this script to find an existing credit card
# date can be looked up and put in range
curl -X GET -H "Content-Type: application/json"  'http://localhost:5001/api/transaction/creditCard?creditCard=f2c6af25x8507x42f3xb077xa3ccc9959304&from=2023-07-27&to=2024-11-30'
