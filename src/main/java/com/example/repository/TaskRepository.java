package com.example.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.mapper.TaskMapper;
import com.example.model.Task;

import lombok.RequiredArgsConstructor;

/**
 * タスク管理アプリケーション
 */
@RequiredArgsConstructor
@Repository
public class TaskRepository {

	private List<Task> tasks = new ArrayList<>();

	private final TaskMapper taskMapper;

	/**
	 * 登録
	 * 
	 * @param form
	 */
	public void register(Task form) {

		taskMapper.insert(form);
	}

	/**
	 * タスク一覧を取得する
	 * 
	 * @return タスク一覧
	 */
	public List<Task> findAll() {
		return taskMapper.selectAll();
	}

	/**
	 * IDから該当のタスクを取得
	 * 
	 * @param id 検索対象のID
	 * @return 検索結果タスク
	 */
	public Task findById(Integer id) {
		return tasks.stream()
		    .filter(i -> i.getId().equals(id))
		    .findFirst()
		    .orElse(null);
	}

	/**
	 * 更新
	 * 
	 * @param form フォーム
	 * @return 更新件数
	 */
	public int update(Task form) {
		return taskMapper.update(form);
	}

	/**
	 * 削除
	 * 
	 * @param form フォーム
	 * @return 削除件数
	 */
	public int delete(Task form) {
		return taskMapper.deleteTask(form);
	}

}