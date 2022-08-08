docker cp inInterviewSqlWriting.sql payroll_database:/
docker exec -it payroll_database psql -U postgres -f inInterviewSqlWriting.sql
