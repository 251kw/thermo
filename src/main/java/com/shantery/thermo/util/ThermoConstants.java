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
	/** ログインボタンを押された時 **/
	public static final String LOGIN = "/login";
	/** ユーザー一括登録ボタンを押された時  **/
	public static final String USERS_MULTI_SET = "/users_multi_set";
	/** ユーザー一括登録でファイルをアップロードした時  **/
	public static final String USERS_MULTI_GET = "/users_multi_get";
	/** ユーザー一括登録の確認画面で確定したとき  **/
	public static final String USERS_MULTI_CONF_SUC = "/users_multi_conf_suc";
	/** ユーザー一括登録の確認画面でキャンセルしたとき  **/
	public static final String USERS_MULTI_CONF_BACK = "/users_multi_conf_back";
	
	// 遷移先を示す変数
	/** TOPページの遷移先 **/
	public static final String TO_TOP = "index";
	/** ユーザー一括登録のINPUTへの遷移 **/
	public static final String TO_USERS_MULTI_INP = "userInfoMultiInput";
	/** ユーザー一括登録のCONFIRMへの遷移 **/
	public static final String TO_USERS_MULTI_CONF = "userInfoMultiConfirm";
	/** ユーザー一括登録のRESULTへの遷移 **/
	public static final String TO_USERS_MULTI_RES = "userInfoMultiResult";
	
	// キーを示す変数
	/** ユーザー情報のCSVファイルをinputするときのキー **/
	public static final String USERS_CSV = "users_csv";
	/** ユーザー情報をモデルにいれるときのキー **/
	public static final String USERS_INFO = "users_info";
	/** ユーザー情報のheadをモデルに入れるするのキー **/
	public static final String USERS_HEAD = "users_head_info";
	/** ユーザー情報のエラーメッセージをモデルに入れるするのキー **/
	public static final String USERS_ERR = "users_err_info";
	
	// @RequestParamのname属性
	
	// その他よく使う文字の変数 TODO 不要なものを削除する
		/** LIKE検索用の最初の%、外部化済みなので削除推奨 **/
		public static final String TOP_PERCENT = "'%";
		/** LIKE検索用の最後の%、外部化済みなので削除推奨 **/
		public static final String END_PERCENT = "%'";
		/** 空文字 **/
		public static final String EMPTY = "";
		/** ページングに使う 0 **/
		public static final int ZERO = 0;
		/** ページングに使う 1 **/
		public static final int ONE = 1;
		/** ページングに使う 2 **/
		public static final int TWO = 2;
		/** ページングに使う 9 **/
		public static final int NINE = 9;
		/**URLの後ろの&page**/
		public static final String PREPAGE = "&page=";
		/**クエリ文字**/
		public static final String Query = "?";
		/**＆**/
		public static final String AND = "&";
		/**カンマ**/
		public static final String COMMA = ",";
		/** 空白で始まる正規表現 **/
		public static final String STARTEMPTY = "^[\\h]+";
		/** 空白で終わる正規表現 **/
		public static final String FINISHEMPTY = "[\\h]+$";
		/** ページ番号の初期値 **/
		public static final String DEFAULT_PEGE = "1";
}
