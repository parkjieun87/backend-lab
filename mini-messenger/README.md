# Mini Messenger

React + Electron + Spring Boot로 만드는 1:1·그룹 데스크톱 메신저 (7단계 학습 프로젝트).
회사 코드·설정·도메인은 가져오지 않고, 개념(세션, JWT, room, pub/sub, IPC)만 옮긴다.

## 구조

```
mini-messenger/
├── docker-compose.yml          MariaDB(3306) + Redis(6380)
├── docker/mariadb/init/        스키마 DDL (users, rooms, room_members, messages)
├── server/                     Spring Boot 4.1 / Java 21 — 1~3단계는 모듈 하나
├── web/                        React + Vite (TypeScript) — 1~5단계는 브라우저 웹
└── NOTES.md                    단계별 한 줄 노트
```

나중에 생길 것:
- 4단계: `server/`를 `api-server/`(REST, 저장 → `chat-event` 발행)와 `socket-server/`(netty-socketio, 구독 → room 전송)로 분리
- 6단계: `desktop/` (electron-vite, main·preload·renderer) — `web/`의 화면을 renderer로 옮김

## 실행

```bash
docker compose up -d
```

```bash
cd server && ./mvnw spring-boot:run
```

```bash
cd web && npm install && npm run dev
```

- 웹: http://localhost:5173 — `/auth`, `/rooms`, `/messages`는 Vite 프록시로 8080에 넘어간다 (같은 출처 → 쿠키 유지)
- Redis 확인: `docker exec -it messenger-redis redis-cli MONITOR`
- DB 초기화: `docker compose down -v && docker compose up -d`

## 단계

| 단계 | 내용 | 상태 |
|---|---|---|
| 1 | 세션 로그인 (`/auth/login`, `/auth/me`, `/auth/logout`) | ☐ |
| 2 | JWT + refreshToken (`/auth/token`, `/auth/refresh`) | ☐ |
| 3 | 소켓 연결과 room, 방 목록·만들기 | ☐ |
| 4 | 메시지 송수신, API/소켓 서버 분리, Redis pub/sub | ☐ |
| 5 | 채팅방 목록 Redis Set 캐시, 초대·나가기 | ☐ |
| 6 | Electron으로 감싸기, IPC 로그인, 토스트 | ☐ |
| 7 | 자동 로그인(safeStorage), 토큰 만료 재연결, 절전 복귀 | ☐ |

각 단계는 설계서의 완성 기준을 모두 체크한 뒤 다음으로 넘어간다.
