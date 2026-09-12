package com.example.model;

import java.util.Date;

import lombok.Data;

@Data
public class Task {
	
	// タスクID
	private Integer id;
	// タイトル
	private String title;
	// カテゴリ
	private String category;
	// 期限
	private Date dueDate;
}
