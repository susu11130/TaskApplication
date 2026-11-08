package com.example.model;

import lombok.Data;

/**
 * 入力項目ごとの検証エラー表示用DTO
 */
@Data
public class FieldError {
	
	private String titleErrorMessage;
	private String categoryErrorMessage;
	private String dueDateErrorMessage;

}
