-- 컨테이너 볼륨이 비어 있을 때 한 번만 실행된다. 스키마를 바꿨으면 docker compose down -v 후 다시 올린다.
CREATE TABLE users (
    id             BIGINT       NOT NULL AUTO_INCREMENT PRIMARY KEY,
    login_id       VARCHAR(50)  NOT NULL UNIQUE,
    password_hash  VARCHAR(100) NOT NULL,          -- BCrypt
    name           VARCHAR(50)  NOT NULL,
    pwd_changed_at DATETIME(3)  NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
    created_at     DATETIME(3)  NOT NULL DEFAULT CURRENT_TIMESTAMP(3)
);

CREATE TABLE rooms (
    id         BIGINT       NOT NULL AUTO_INCREMENT PRIMARY KEY,  -- = 소켓 room 이름
    type       VARCHAR(10)  NOT NULL,                             -- DIRECT / GROUP
    name       VARCHAR(100),
    created_by BIGINT       NOT NULL,
    created_at DATETIME(3)  NOT NULL DEFAULT CURRENT_TIMESTAMP(3)
);

CREATE TABLE room_members (
    room_id              BIGINT      NOT NULL,
    user_id              BIGINT      NOT NULL,
    joined_at            DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
    alarm_off            BOOLEAN     NOT NULL DEFAULT FALSE,
    last_read_message_id BIGINT,
    PRIMARY KEY (room_id, user_id),
    INDEX idx_room_members_user (user_id)
);

CREATE TABLE messages (
    id         BIGINT       NOT NULL AUTO_INCREMENT PRIMARY KEY,
    room_id    BIGINT       NOT NULL,
    sender_id  BIGINT,                               -- SYSTEM 메시지는 NULL
    type       VARCHAR(10)  NOT NULL,                -- TEXT / SYSTEM
    content    TEXT         NOT NULL,
    created_at DATETIME(3)  NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
    INDEX idx_messages_room_id (room_id, id)
);
