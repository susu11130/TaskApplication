package com.example.taskcontroller;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.model.FieldError;
import com.example.model.Task;
import com.example.service.TaskService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * タスク管理アプリケーション
 */
@RequiredArgsConstructor
@Controller
public class TaskController {

	private final TaskService taskService;

	/**
	 * 初期表示
	 * 
	 * @param taskForm
	 * @return
	 */
	@GetMapping("/task")
	public ModelAndView index(Model model) {

		ModelAndView mav = new ModelAndView();

		// 検証エラー時の入力値を上書きしないよう、フォームがない場合のみ初期化
		if (!model.containsAttribute("taskForm")) {
			mav.addObject("taskForm", new Task());
		}

		mav.addObject("tasks", taskService.findAll());
		mav.setViewName("task");
		return mav;
	}

	/**
	 * 登録処理
	 * 
	 * @param form
	 * @return 初期表示へリダイレクト
	 */
	@PostMapping("/task/register")
	public ModelAndView register(
	    @ModelAttribute @Valid Task taskForm,
	    BindingResult bindingResult,
	    RedirectAttributes redirectAttributes) {

		// 検証エラーがある場合は、登録処理を中断する
		if (bindingResult.hasErrors()) {
			redirectAttributes.addFlashAttribute("taskForm", taskForm);
			redirectAttributes
			    .addFlashAttribute(BindingResult.MODEL_KEY_PREFIX + "taskForm", bindingResult);
			redirectAttributes.addFlashAttribute("openRegisterModal", true);
			return new ModelAndView("redirect:/task");
		}
		try {
			taskService.registerProcess(taskForm);
			redirectAttributes.addFlashAttribute("successMessage", "タスクを登録しました");
		} catch (DataAccessException e) {
			redirectAttributes.addFlashAttribute("errorMessage", "タスクの登録に失敗しました");
		}
		return new ModelAndView("redirect:/task");
	}

	/**
	 * 更新処理
	 * 
	 * @param form
	 * @return
	 */
	@PutMapping("/task/update")
	@ResponseBody
	public ResponseEntity<?> update(@RequestBody @Valid Task form, BindingResult bindingResult) {

		// 入力エラーがある場合はエラーメッセージをHTTP 400で返す
		if (bindingResult.hasErrors()) {
			FieldError errors = setErrorMessage(bindingResult);
			return ResponseEntity.badRequest().body(errors);
		}

		try {
			Task updatedTask = taskService.updateProcess(form);
			return ResponseEntity.ok(updatedTask);
		} catch (OptimisticLockingFailureException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
		}
	}

	/**
	 * 削除処理
	 * 
	 * @param form
	 * @return
	 */
	@DeleteMapping("/task/delete")
	@ResponseBody
	public ResponseEntity<?> delete(@RequestBody Task form) {
		
		try {
			Task deleteTask = taskService.deleteProcess(form);
			return ResponseEntity.ok(deleteTask);
		} catch(OptimisticLockingFailureException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
		}
	}

	/**
	 * 入力エラーがある場合にエラーメッセージをセット
	 * 
	 * @param bindingResult
	 * @return 検証エラー表示用DTO
	 */
	private FieldError setErrorMessage(BindingResult bindingResult) {

		FieldError errors = new FieldError();
		// タイトルに検証エラーがある場合は、エラーをセット
		if (bindingResult.hasFieldErrors("title")) {
			errors.setTitleErrorMessage(bindingResult.getFieldError("title").getDefaultMessage());
		}
		// カテゴリーに検証エラーがある場合は、エラーをセット
		if (bindingResult.hasFieldErrors("category")) {
			errors.setCategoryErrorMessage(bindingResult.getFieldError("category").getDefaultMessage());
		}
		// 期限に検証エラーがある場合は、エラーをセット
		if (bindingResult.hasFieldErrors("dueDate")) {
			errors.setDueDateErrorMessage(bindingResult.getFieldError("dueDate").getDefaultMessage());
		}
		return errors;
	}
}
