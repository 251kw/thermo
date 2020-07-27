package com.shantery.thermo.util;

/**
 * @author y.okino
 * 共通 定数を保持するクラス
 * 共通して使用する値はこのクラスで定義する
 */
public class ThermoConstants {

	/** インスタンス生成禁止 **/
	private ThermoConstants() {
	}
	
	// URLのパラメータを示す変数
	/** 起動時やTOPページに戻るときに使う変数 **/
	public static final String TOP = "/";
	/** ユーザー一括登録ボタンを押された時  **/
	public static final String USERS_INPUT_MULTI = "/userInfoMultiInpu";
	
	// 遷移先を示す変数
	/** TOPページの遷移先 **/
	public static final String TO_TOP = "index";
	
	// キーを示す変数
	/** ユーザー情報のCSVファイルをinputするときのキー **/
	public static final String USERS_INFO = "users_info";
	
	// @RequestParamのname属性
	
	// その他よく使う文字の変数
}
