-- ForoHub - V1: crear tabla topics

CREATE TABLE IF NOT EXISTS topics (
  id             BIGINT AUTO_INCREMENT PRIMARY KEY,
  title          VARCHAR(140)   NOT NULL,
  message        VARCHAR(4000)  NOT NULL,
  creation_date  DATETIME(6)    NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  status         ENUM('OPEN','CLOSED','ARCHIVED','DELETED') NOT NULL DEFAULT 'OPEN',
  author         VARCHAR(120)   NOT NULL,
  course         VARCHAR(120)   NOT NULL,
  INDEX idx_topics_creation_date (creation_date),
  INDEX idx_topics_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;