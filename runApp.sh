source scripts/setEnv.sh
nohup java -jar target/cockroach-0.0.1-SNAPSHOT.jar > /mnt/data1/bank-app/app.out 2>&1 &
