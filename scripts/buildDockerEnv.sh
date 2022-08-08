# Make the database container first to make sure it finished by the time we exec the database
docker run --name payroll_database -e POSTGRES_PASSWORD=cashew -e POSTGRES_USER=postgres -e POSTGRES_DATABASE=payroll -d -p 127.0.0.1:5434:5432 postgres
docker cp initDatabase.sql payroll_database:/

docker build -f Dockerfile -t payroll_backend .
docker run -d -it -v ${PWD}:/app --name payroll_backend payroll_backend

docker network create payroll
docker network connect payroll payroll_backend
docker network connect payroll payroll_database

docker exec -it payroll_database psql -U postgres -f initDatabase.sql
