package com.talk2amareswaran.projects.taskservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class TaskserviceApplication {

	@Autowired
	TaskDAO taskDAO;
	
	public static void main(String[] args) {
		SpringApplication.run(TaskserviceApplication.class, args);
	}
	
	@RequestMapping(value="/tasks/{id}", method=RequestMethod.GET)
	public String getTaskName(@PathVariable("id") String taskid) {
		return taskDAO.getTaskNameById(taskid);
	}
}