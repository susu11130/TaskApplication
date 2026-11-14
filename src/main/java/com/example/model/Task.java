package com.example.model;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * タスクDTO
 */
@Data
public class Task {

	// タスクID
	@NotNull(message = "必須入力です。")
	private Integer id;

	// タイトル
	@NotBlank(message = "必須入力です。")
	@Size(max = 50)
	private String title;

	// カテゴリ
	@Size(max = 50, message = "50文字で入力してください。")
	private String category;

	// 期限
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd")
	@FutureOrPresent(message = "期限は本日以降の日付を指定してください")
	private LocalDate dueDate;

	// バージョン
	private Integer version;
}
