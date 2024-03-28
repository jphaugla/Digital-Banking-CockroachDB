#  easiest to look up a credit card using redinsight and edit this script to find an existing credit card
# date can be looked up and put in range
curl -X GET -H "Content-Type: application/json"  'http://localhost:8080/api/transaction/creditCard?creditCard=9e17ab60x7920x4012xa65ex865e7e88e2ec&from=2023-07-27&to=2024-11-30'
