package com.example.scheduler.repository;

import com.example.scheduler.dto.SchedulerRequestDto;
import com.example.scheduler.dto.SchedulerResponseDto;
import com.example.scheduler.entity.Schedule;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class JDBCTemplateSchedulerRepository implements SchedulerRepository {

    private final JdbcTemplate jdbcTemplate;

    public JDBCTemplateSchedulerRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public SchedulerResponseDto saveSchedule(Schedule schedule) {
        String userId = schedule.getWriter();
        String userPassword = schedule.getPassword();

        Integer userCount = jdbcTemplate.queryForObject("SELECT count(*) from user where user_id=?", new Object[]{userId}, Integer.class);

        if (userCount == null || userCount == 0) {
            jdbcTemplate.update("INSERT INTO user (user_id, password) VALUES (?, ?)", userId, userPassword);
        } else {
            String existingPassword = jdbcTemplate.queryForObject("SELECT password FROM user WHERE user_id=?", new Object[]{userId}, String.class);

            if (!existingPassword.equals(userPassword)) {
                throw new IllegalArgumentException("Wrong password");
            }
        }

        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("schedule").usingGeneratedKeyColumns("schedule_id");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("contents", schedule.getContents());
        parameters.put("writer", schedule.getWriter());
        parameters.put("password", schedule.getPassword());
        parameters.put("updated_at", schedule.getUpdateTime());

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));

        return new SchedulerResponseDto(key.longValue(), schedule.getContents(), schedule.getWriter(), schedule.getUpdateTime());
    }

    @Override
    public List<SchedulerResponseDto> findScheduleByFilter(SchedulerRequestDto dto) {
        StringBuilder sql = new StringBuilder("SELECT * FROM schedule WHERE 1=1");
        List<Object> params = new ArrayList<>();

        if (dto.getWriter() != null) {
            sql.append(" AND writer=?");
            params.add(dto.getWriter());
        }

        if (dto.getUpdateDate() != null) {
            sql.append(" AND DATE(updated_at)=?");
            params.add(dto.getUpdateDate());
        }

        return jdbcTemplate.query(sql.toString(), scheduleRowMapper(), params.toArray());
    }

    @Override
    public Optional<Schedule> findScheduleById(Long id) {
        List<Schedule> result = jdbcTemplate.query("SELECT * FROM schedule WHERE schedule_id=?", scheduleRowMapperV2(), id);

        return result.stream().findAny();
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
            String password = "";
            return new Schedule(
                    rs.getLong("schedule_id"),
                    rs.getString("contents"),
                    rs.getString("writer"),
                    password,
                    rs.getTimestamp("updated_at").toLocalDateTime()
            );
        };
    }
}
