CREATE TABLE IF NOT EXISTS analysis_results
(
    id                 SERIAL PRIMARY KEY,
    file_id            INT          NOT NULL,
    file_name         VARCHAR(255) NOT NULL,
    file_size         BIGINT       NOT NULL,
    hash_code     VARCHAR(64)  NOT NULL,
    num_of_chars       INT          NOT NULL,
    num_of_words       INT          NOT NULL,
    num_of_paragraphs  INT          NOT NULL,
    word_cloud_path    VARCHAR(255) NOT NULL,
    analysis_timestamp TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);