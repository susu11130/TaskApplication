package com.example.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.model.Task;

@Repository
public class TaskRepository {

	/**
	 * 登録_画面表示用Listに追加する
	 * 
	 * @param form
	 * @return
	 */
	public List<Task> register(Task form) {

		List<Task> tasks = new ArrayList<>();

		tasks.add(form);

		return tasks;

	}

}
