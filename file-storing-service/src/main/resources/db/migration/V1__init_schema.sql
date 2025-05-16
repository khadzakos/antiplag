CREATE TABLE IF NOT EXISTS files (
                                     id SERIAL PRIMARY KEY,
                                     original_name VARCHAR(255) NOT NULL,
                                     size_bytes BIGINT NOT NULL,
                                     hash_code VARCHAR(64) NOT NULL UNIQUE,
                                     upload_timestamp TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
                                     storage_path VARCHAR(255) NOT NULL
);
