# SQL Guidelines

## 1. Use SQLite as a database
- You can use SQLite as a database

## 2. Schema for database
- Use liquibase to generate the schema. 
- Store the XML configuration for liquibase under `/src/main/resources/liquibase/`
- Apply scripts on startup of the application.
- Add some mock dummy data to the database.

## 3. Location
- Write queries to separate files in `/src/main/resources/sql`
- Queries should use named parameters, e.g. `:id` rather than question marks
