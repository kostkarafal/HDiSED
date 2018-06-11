package com.talk2amareswaran.projects.taskservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class TaskDAO {

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	public String getTaskNameById(String taskid) {
		return jdbcTemplate.queryForObject("SELECT TASK_NAME FROM TASK WHERE ID=?", new Object[] {taskid},String.class);
	}
}