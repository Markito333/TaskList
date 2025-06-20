CREATE TABLE IF NOT EXISTS tasks (
    id BIGSERIAL PRIMARY KEY,
    description TEXT NOT NULL,
    completed BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP
);

CREATE TABLE IF NOT EXISTS task_tags (
    task_id BIGINT NOT NULL,
    tag VARCHAR(255),
    FOREIGN KEY (task_id) REFERENCES tasks(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS task_mentions (
    task_id BIGINT NOT NULL,
    mention VARCHAR(255),
    FOREIGN KEY (task_id) REFERENCES tasks(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS task_emails (
    task_id BIGINT NOT NULL,
    email VARCHAR(255),
    FOREIGN KEY (task_id) REFERENCES tasks(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS task_links (
    task_id BIGINT NOT NULL,
    link VARCHAR(255),
    FOREIGN KEY (task_id) REFERENCES tasks(id) ON DELETE CASCADE
);