package com.example.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.model.Task;
import com.example.repository.TaskRepository;

@Service
public class TaskService {

	private final TaskRepository taskRepository;

	/**
	 * コンストラクタインジェクション
	 * 
	 * @param taskRepository
	 */
	public TaskService(TaskRepository taskRepository) {
		this.taskRepository = taskRepository;
	}

	public List<Task> registerProcess(Task form) {

		List<Task> tasks = taskRepository.register(form);

		return tasks;
	}

}
