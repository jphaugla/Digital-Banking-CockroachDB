echo "first"
curl -X POST -H "Content-Type: application/json" http://localhost:5001/api/customer/postCustomer?customer --data @customer.json
echo "\nsecond"
curl -X POST -H "Content-Type: application/json" http://localhost:5001/api/customer/postCustomer?customer --data @customer1.json
echo "\nthird"
curl -X POST -H "Content-Type: application/json" http://localhost:5001/api/customer/postCustomer?customer --data @customer2.json
echo "\nfourth"
curl -X POST -H "Content-Type: application/json" http://localhost:5001/api/customer/postCustomer?customer --data @customer3.json
echo "\nfifth"
curl -X POST -H "Content-Type: application/json" http://localhost:5001/api/customer/postCustomer?customer --data @customer4.json
echo "\nsixth"
curl -X POST -H "Content-Type: application/json" http://localhost:5001/api/customer/postCustomer?customer --data @customer5.json
echo "\nseventh"
curl -X POST -H "Content-Type: application/json" http://localhost:5001/api/customer/postCustomer?customer --data @customer6.json
