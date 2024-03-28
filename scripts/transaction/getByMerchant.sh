# find all transactions for an account from one merchant in date range 
curl -X GET -H "Content-Type: application/json"  'http://localhost:8080/api/transaction/merchantTransactions?merchant=Walmart&account=eda92ea2-4e93-4726-a69e-76b989a75fda&from=2022-06-27&to=2024-11-23'
