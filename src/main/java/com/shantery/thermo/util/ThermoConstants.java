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
	/** ユーザー一括登録ボタンを押された時 **/
	public static final String USERS_MULTI_SET = "/users_multi_set";
	/** ユーザー一括登録でファイルをアップロードした時 **/
	public static final String USERS_MULTI_GET = "/users_multi_get";
	/** ユーザー一括登録の確認画面で確定したとき **/
	public static final String USERS_MULTI_CONF_SUC = "/users_multi_conf_suc";
	/** ユーザー一括登録の確認画面でキャンセルしたとき **/
	public static final String USERS_MULTI_CONF_BACK = "/users_multi_conf_back";
	/** ユーザー新規登録の入力画面で確認ボタンを押されたとき **/
	public static final String USER_INFO_INP_SUC = "/user_info_inp_suc";
	/** ユーザー新規登録の入力画面で戻るボタンを押されたとき **/
	public static final String USER_INFO_INP_BACK = "/user_info_inp_back";
	/** ユーザー新規登録の確認画面で登録ボタンを押されたとき **/
	public static final String USER_INFO_CONF_SUC = "/user_info_conf_suc";
	/** ユーザー新規登録の確認画面で修正ボタンを押されたとき **/
	public static final String USER_INFO_CONF_BACK = "/user_info_conf_back";
	/** グループ新規登録の入力画面で確認ボタンを押されたとき **/
	public static final String GROUP_INFO_INP_SUC = "/group_info_inp_suc";
	/** グループ新規登録の入力画面で戻るボタンを押されたとき **/
	public static final String GROUP_INFO_INP_BACK = "/group_info_inp_back";
	/** グループ新規登録の確認画面で登録ボタンを押されたとき **/
	public static final String GROUP_INFO_CONF_SUC = "/group_info_conf_suc";
	/** グループ新規登録の確認画面で修正ボタンを押されたとき **/
	public static final String GROUP_INFO_CONF_BACK = "/group_info_conf_back";
	
	// 遷移先を示す変数
	/** TOPページの遷移先 **/
	public static final String TO_TOP = "index";
	/** ユーザー一括登録のINPUTへの遷移 **/
	public static final String TO_USERS_MULTI_INP = "userInfoMultiInput";
	/** ユーザー一括登録のCONFIRMへの遷移 **/
	public static final String TO_USERS_MULTI_CONF = "userInfoMultiConfirm";
	/** ユーザー一括登録のRESULTへの遷移 **/
	public static final String TO_USERS_MULTI_RES = "userInfoMultiResult";
	/** ユーザー新規登録のINPUTへの遷移 **/
	public static final String TO_USER_INFO_INP = "userInfoInput";
	/** ユーザー新規登録のCONFIRMへの遷移 **/
	public static final String TO_USER_INFO_CONF = "userInfoConfirm";
	/** ユーザー新規登録のRESULTへの遷移 **/
	public static final String TO_USER_INFO_RES = "userInfoResult";
	/** グループ新規登録のINPUTへの遷移 **/
	public static final String TO_GROUP_INFO_INP = "groupInfoInput";
	/** グループ新規登録のCONFIRMへの遷移 **/
	public static final String TO_GROUP_INFO_CONF = "groupInfoConfirm";
	/** グループ新規登録のRESULTへの遷移 **/
	public static final String TO_GROUP_INFO_RES = "groupInfoResult";
	
	// キーを示す変数
	/** ユーザー情報のCSVファイルをinputするときのキー **/
	public static final String USERS_CSV = "users_csv";
	/** ユーザー情報をセッションにいれるときのキー **/
	public static final String USERS_INFO_SES = "usersInfoSes";
	/** ユーザー情報をモデルにいれるときのキー **/
	public static final String USERS_INFO = "users_info";
	/** ユーザー情報のheadをモデルに入れるときのキー **/
	public static final String USERS_HEAD = "users_head_info";
	/** ユーザー情報のheadの長さをモデルに入れるときのキー **/
	public static final String USERS_HEAD_LENG = "columnlength";
	/** ユーザー情報のエラーメッセージをモデルに入れるするのキー **/
	public static final String USERS_ERR = "users_err_info";
	/** ログイン中のユーザー情報をセッションに入れるときのキー **/
	public static final String LOGIN_USER = "login_user";
	/** ログイン画面に出すエラーメッセージをモデルに入れるときのキー **/
	public static final String LOGIN_ERROR = "login_error";
	
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
		
	// trimBlankで使う定数
		/** 半角スペース **/
		public static final String HALF_SPACE = " ";
		/** 全角スペース **/
		public static final String FULL_SPACE = "　";
		
}
