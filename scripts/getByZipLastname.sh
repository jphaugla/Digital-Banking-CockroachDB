#  get by zipcode and lastname.  Lastname is a generated integer
curl -X GET -H "Content-Type: application/json"  'http://localhost:5001/api/customer/getZipLastname?zipcode=55444&lastname=Emerson'
