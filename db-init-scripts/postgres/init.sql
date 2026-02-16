-- Conditionally create 'keycloak' database if it doesn't exist
SELECT 'CREATE DATABASE keycloak'
WHERE NOT EXISTS (
  SELECT FROM pg_database WHERE datname = 'keycloak'
)
\gexec

-- Create 'postgres' role if it doesn't exist
CREATE ROLE IF NOT EXISTS postgres WITH LOGIN PASSWORD 'Psdt@2025';

-- Grant all privileges on 'keycloak' database to 'postgres'
GRANT ALL PRIVILEGES ON DATABASE keycloak TO postgres;
