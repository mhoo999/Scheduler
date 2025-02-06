![Scheduler_ERD drawio](https://github.com/user-attachments/assets/e516b667-04f2-4fd2-85be-70229e1efb37)

API Link
https://documenter.getpostman.com/view/41349915/2sAYX6q2UX

##SQL
```sql
CREATE TABLE schedule (
    schedule_id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '스케줄 식별자',
    contents TEXT COMMENT '내용',
    writer VARCHAR(100) NOT NULL COMMENT '작성자',
    password VARCHAR(100) NOT NULL COMMENT '비밀번호',
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '생성/수정 시간'
);

CREATE TABLE user (
    user_id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '유저 식별자',
    password VARCHAR(100) NOT NULL COMMENT '비밀번호',
    email VARCHAR(100) NOT NULL UNIQUE COMMENT '이메일',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '생성 시간',
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정 시간'
);
```

개요
- 스케줄을 저장하고, 조회, 업데이트, 삭제하는 기본 동작이 가능한 스케줄러
- 잘못된 쿼리를 식별하고, 적절한 경고 메시지를 제공

구현 내용
- 스케줄러 생성 시, 기존 사용자인지 판단
    - 신규 사용자일 경우 user 테이블에 사용자를 저장하고 신규 스케줄을 게시
    - 기존 사용자일 경우 패스워드 확인하여 게시 및 경고 메시지 제공
- 필터링 조회
- 단건 조회
- 수정
- 삭제
- 페이지네이션 처리
