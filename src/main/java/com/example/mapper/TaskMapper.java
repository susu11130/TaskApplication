package com.example.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.model.Task;

@Mapper
public interface TaskMapper {

	/**
	 * タスクIDの一覧に一致するタスクを取得
	 * 
	 * @param ids タスクIDの一覧
	 * @return タスク一覧
	 */
	public List<Task> selectByIds(@Param("ids") List<String> ids);

	/**
	 * 全てのタスク一覧を取得
	 * 
	 * @return タスク一覧
	 */
	public List<Task> selectAll();

	/**
	 * 登録
	 * 
	 * @param task
	 */
	public void insert(Task task);

	/**
	 * 更新
	 * 
	 * @param task
	 * @return
	 */
	public int update(Task task);

	/**
	 * 削除
	 * 
	 * @param task
	 * @return
	 */
	public int deleteTask(Task task);
}
