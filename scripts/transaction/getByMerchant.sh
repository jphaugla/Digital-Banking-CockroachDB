# find all transactions for an account from one merchant in date range 
curl -X GET -H "Content-Type: application/json"  'http://localhost:5001/api/transaction/merchantTransactions?merchant=Walmart&account=2fde8b74-053f-4255-b6ae-82622476ab1f&from=2022-06-27&to=2024-11-23'
