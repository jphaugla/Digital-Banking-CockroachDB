# find all transactions for an account from merchant category for date range
curl -X GET -H "Content-Type: application/json"  'http://localhost:5001/api/transaction/merchantCategory?merchantCategory=5912&account=55a7b79c-1aac-4328-be58-d12831d585a4&from=2022-07-27&to=2024-10-30'
