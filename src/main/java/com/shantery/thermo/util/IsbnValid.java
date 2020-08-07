package com.shantery.thermo.util;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;

@Documented  //公開APIの一部とする
@Constraint(validatedBy={IsbnValidator.class})  //クラスの指定
@Target({ElementType.FIELD,ElementType.ANNOTATION_TYPE}) //フィールド宣言と型パラメータ宣言での使用を明示
@Retention(RetentionPolicy.RUNTIME)  //実行時に情報を読み込ませる
@ReportAsSingleViolation  //message()で定義された内容が使われるようにする
public @interface IsbnValid { //アノテーション定義
	String message() default "Isbn err"; //デフォルトエラーメッセージの指定
	
	//アノテーション作成の必須定義
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};

	@Target({ ElementType.FIELD, ElementType.ANNOTATION_TYPE }) //フィールド宣言と型パラメータ宣言での使用を明示
	@Retention(RetentionPolicy.RUNTIME) //実行時に情報を読み込ませる
	@Documented //公開APIの一部とする
	public @interface List {  
		IsbnValid[] value();
	}
}
