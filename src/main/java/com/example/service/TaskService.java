package com.example.service;

import java.util.List;

import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.model.Task;
import com.example.repository.TaskRepository;

/**
 * タスク管理アプリケーション
 */
@Service
public class TaskService {

	private final TaskRepository taskRepository;

	/**
	 * コンストラクタインジェクション
	 * 
	 * @param taskRepository タスクDB接続を行うレポジトリ
	 */
	public TaskService(TaskRepository taskRepository) {
		this.taskRepository = taskRepository;
	}

	/**
	 * 登録処理
	 * 
	 * @param form フォーム
	 */
	@Transactional
	public void registerProcess(Task form) {
		taskRepository.register(form);
	}

	/**
	 * 全タスクを取得
	 * 
	 * @return タスク一覧
	 */
	public List<Task> findAll() {
		return taskRepository.findAll();
	}

	/**
	 * IDから対象のタスクを取得
	 * 
	 * @param id
	 * @return タスク
	 */
	public Task findById(Integer id) {
		return taskRepository.findById(id);
	}

	/**
	 * 更新処理
	 * 
	 * @param form フォーム
	 * @return 更新対象のタスク
	 */
	@Transactional
	public Task updateProcess(Task form) {

		int count = taskRepository.update(form);
		// 更新件数が1件以外の場合はエラー
		if (count != 1) {
			throw new OptimisticLockingFailureException("更新に失敗しました。");
		}
		// 更新成功後に、versionを加算
		form.setVersion(form.getVersion() + 1);
		return form;
	}

	/**
	 * 削除処理
	 * 
	 * @param form フォーム
	 * @return 削除対象のタスク
	 */
	@Transactional
	public Task deleteProcess(Task form) {
		int count = taskRepository.delete(form);
		// 削除件数が1件以外の場合はエラー
		if (count != 1) {
			throw new OptimisticLockingFailureException("削除に失敗しました。");
		}
		return form;
	}

}
