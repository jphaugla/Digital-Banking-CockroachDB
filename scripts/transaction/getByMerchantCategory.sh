# find all transactions for an account from merchant category for date range
curl -X GET -H "Content-Type: application/json"  'http://localhost:8080/api/transaction/merchantCategory?merchantCategory=5912&account=eda92ea2-4e93-4726-a69e-76b989a75fda&from=2022-07-27&to=2024-10-30'
