package com.example.TaskController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.model.Task;
import com.example.service.TaskService;

/**
 * タスク管理アプリケーション
 */
@Controller
public class TaskController {

//	// Field Injection
//	@Autowired
//	TaskService taskService;
	
	// フィールドインジェクションとコンストラクタインジェクションの違い
	// 新規開発では再代入の観点からコンストラクタインジェクションが推奨
	// ただし、現場の記載統一や、テストプログラムによってはフィールドインジェクションを使用しているところも

	private final TaskService taskService;
	/**
	 * Constructor Injection
	 * 
	 * @param taskService　DIコンテナのBeanクラス
	 */
	public TaskController(TaskService taskService) {
		this.taskService = taskService;
	}

	/**
	 * 初期表示
	 * 
	 * @param mav
	 * @return
	 */
	@GetMapping("/task")
	public ModelAndView index() {

		ModelAndView mav = new ModelAndView();

		mav.addObject("tasks", List.of(new Task()));
		mav.setViewName("task");
		return mav;
	}

	/**
	 * 登録処理
	 * 
	 * @param form
	 * @return
	 */
	@PostMapping("/task/new")
	public ModelAndView register(@ModelAttribute Task form) {

		ModelAndView mav = new ModelAndView();

		List<Task> tasks = taskService.registerProcess(form);

		mav.addObject(tasks);

		mav.setViewName("task_new");

		return mav;
	}
}
