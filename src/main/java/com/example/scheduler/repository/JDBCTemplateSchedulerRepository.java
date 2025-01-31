package com.example.scheduler.repository;

import com.example.scheduler.dto.SchedulerRequestDto;
import com.example.scheduler.dto.SchedulerResponseDto;
import com.example.scheduler.entity.Schedule;
import com.example.scheduler.entity.User;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;

@Repository
public class JDBCTemplateSchedulerRepository implements SchedulerRepository {

    private final JdbcTemplate jdbcTemplate;

    public JDBCTemplateSchedulerRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // user를 테이블에 저장하고, 이미 존재하는 경우 해당 user_id를 반환하는 메서드
    @Override
    public Long findOrSaveUser(User user) {
        String email = user.getEmail();
        String userPassword = user.getPassword();
        Long userId = null;

        // user 테이블에서 user_id를 검색
        List<Long> userIds = jdbcTemplate.queryForList("SELECT user_id FROM user WHERE email=?", new Object[]{email}, Long.class);

        if (userIds.isEmpty()) {
            SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
            jdbcInsert.withTableName("user").usingGeneratedKeyColumns("user_id");

            Map<String, Object> parameters = new HashMap<>();
            parameters.put("password", user.getPassword());
            parameters.put("email", user.getEmail());
            parameters.put("created_at", LocalDateTime.now());
            parameters.put("updated_at", LocalDateTime.now());
            parameters.put("writer", user.getWriter());

            Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
            userId = key.longValue();
        } else {
            userId = userIds.get(0);

            String existingPassword = jdbcTemplate.queryForObject("SELECT password FROM user WHERE user_id=?", new Object[]{userId}, String.class);

            if (!existingPassword.equals(userPassword)) {
                throw new IllegalArgumentException("Wrong password");
            }
        }

        return userId;
    }

    @Override
    public SchedulerResponseDto saveSchedule(Schedule schedule) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("schedule").usingGeneratedKeyColumns("schedule_id");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("contents", schedule.getContents());
        parameters.put("updated_at", schedule.getUpdatedAt());
        parameters.put("user_id", schedule.getUserId());

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));

        return new SchedulerResponseDto(key.longValue(), schedule.getContents(), schedule.getUser().getWriter(), schedule.getUpdatedAt());
    }

    @Override
    public List<SchedulerResponseDto> findScheduleByFilter(SchedulerRequestDto dto) {
        StringBuilder sql = new StringBuilder("SELECT s.*, u.writer FROM schedule s JOIN user u ON s.user_id = u.user_id WHERE 1=1");
        List<Object> params = new ArrayList<>();

        if (dto.getWriter() != null) {
            sql.append(" AND u.writer=?");
            params.add(dto.getWriter());
        }

        if (dto.getUpdateDate() != null) {
            sql.append(" AND DATE(s.updated_at)=?");
            params.add(dto.getUpdateDate());
        }

        return jdbcTemplate.query(sql.toString(), scheduleRowMapper(), params.toArray());
    }

    @Override
    public Optional<Schedule> findScheduleById(Long id) {
        List<Schedule> result = jdbcTemplate.query(
                "SELECT * FROM schedule s JOIN user u ON s.user_id = u.user_id WHERE s.schedule_id=?"
        , scheduleRowMapperV2(), id);

        return result.stream().findAny();
    }

    @Override
    public int updateSchedule(Long id, String contents, String writer) {
        return jdbcTemplate.update("UPDATE schedule s JOIN user u ON s.user_id = u.user_id SET s.contents=?, u.writer=? WHERE schedule_id=?", contents, writer, id);
    }

    @Override
    public int deleteSchedule(Long id) {
        return jdbcTemplate.update("DELETE FROM schedule WHERE schedule_id=?", id);
    }

    @Override
    public Boolean checkPassword(Long scheduleId, String password) {
        String existingPassword = jdbcTemplate.queryForObject("SELECT password FROM schedule s JOIN user u ON s.user_id = u.user_id WHERE s.schedule_id=?", new Object[]{scheduleId}, String.class);

        return existingPassword.equals(password) ? true : false;
    }

    private RowMapper<SchedulerResponseDto> scheduleRowMapper() {
        return (rs, rowNum) -> {
            return new SchedulerResponseDto(
                    rs.getLong("schedule_id"),
                    rs.getString("contents"),
                    rs.getString("writer"),
                    rs.getTimestamp("updated_at").toLocalDateTime()
            );
        };
    }

    private RowMapper<Schedule> scheduleRowMapperV2() {
        return (rs, rowNum) -> {
            Long userId = rs.getLong("user_id");
            String email = rs.getString("email");
            String password = rs.getString("password");
            String writer = rs.getString("writer");
            LocalDateTime createdAt = rs.getTimestamp("updated_at").toLocalDateTime();
            LocalDateTime updatedAt = rs.getTimestamp("updated_at").toLocalDateTime();

            return new Schedule(
                    rs.getLong("schedule_id"),
                    rs.getString("contents"),
                    rs.getTimestamp("updated_at").toLocalDateTime(),
                    rs.getLong("user_id"),
                    new User(userId, email, password, writer, createdAt, updatedAt)
            );
        };
    }


}
