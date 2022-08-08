docker exec -it payroll_backend javac *.java
docker exec -it payroll_backend java -ea -cp .:postgresql-42.4.1.jar Main
