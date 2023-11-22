docker run --name springjpa_application01_mysql -d -p 3310:3306 \
-e MYSQL_ROOT_PASSWORD=1234 \
-e MYSQL_DATABASE=application01_db mysql:latest
