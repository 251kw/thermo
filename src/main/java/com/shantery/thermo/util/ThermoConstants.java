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
	/** ログインボタンを押された時 **/
	public static final String LOGOUT = "/logout";
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
	/** 体温情報登録の入力画面で確認ボタンを押されたとき **/
	public static final String THERMO_INFO_INP_SUC = "/thermo_info_inp_suc";
	/** 体温情報登録の確認画面で登録ボタンを押されたとき **/
	public static final String THERMO_INFO_CONF_SUC = "/thermo_info_conf_suc";
	/** 体温情報登録の確認画面で修正ボタンを押されたとき **/
	public static final String THERMO_INFO_CONF_BACK = "/thermo_info_conf_back";
	/** 体温情報登録から検索に戻るとき **/
	public static final String SEARCH_RETURN = "/search_return";
	/** 体温検索画面で検索ボタンが押されたとき **/
	public static final String SEARCH_INFO = "/search_info";
	
	
	
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
	/** 体温情報登録のINPUTへの遷移 **/
	public static final String TO_THERMO_INFO_INP = "thermoInput";
	/** 体温情報登録のCONFIRMへの遷移 **/
	public static final String TO_THERMO_INFO_CONF = "thermoConfirm";
	/** 体温情報登録のRESULTへの遷移 **/
	public static final String TO_THERMO_INFO_RES = "thermoResult";
	/** 検索画面への遷移 **/
	public static final String TO_SEARCH = "search";
	
	
	
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
	public static final String LOGIN_USER = "loginuser";
	/** ログイン画面に出すエラーメッセージをモデルに入れるときのキー **/
	public static final String LOGIN_ERROR = "login_error";
	/** 検索結果Listをセッションに入れるときのキー**/
	public static final String SCH_LIST = "schlist";
	
	//ユーザー情報一括登録の配列を使うときの数値
	public static final int M_GID = 0;
	public static final int M_GPASS = 1;
	public static final int M_UID = 2;
	public static final int M_UPASS = 3;
	public static final int M_UNAME = 4;
	public static final int M_GENDER = 5;
	public static final int M_BIRTH = 6;
	public static final int M_GRADE = 7;
	public static final int M_FLG = 8;
	
	//イコールメソッドで比べる定数
	/** 何も選択しない場合を判別 **/
	public static final String NO_SEND = "";
	/** 拡張子がcsvファイルであることを判別 **/
	public static final String CSV_SEND = "csv";
	
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
		
	// ThermoReplaceValueで使う定数
		// calcAge
		public static final String HYPHEN = "-";
		public static final String SLASH = "/";
		public static final String DATE_PATTERN = "yyyy/MM/dd";
		
		// valueToName
		/** 区分の種類 **/
		public static final String KBN_TYPE_GRADE = "grade";
		public static final String KBN_TYPE_ADMIN = "admin";
		public static final String KBN_TYPE_GENDER = "gender";
		public static final String KBN_TYPE_EXISTENCE = "exist";
		
		/** 学年区分の区分値 **/
		public static final String KBN_VALUE_GRADE_ZERO = "0";
		public static final String KBN_VALUE_GRADE_ONE = "1";
		public static final String KBN_VALUE_GRADE_TWO = "2";
		public static final String KBN_VALUE_GRADE_THREE = "3";
		public static final String KBN_VALUE_GRADE_FOUR = "4";
		public static final String KBN_VALUE_GRADE_FIVE = "5";
		public static final String KBN_VALUE_GRADE_SIX = "6";
		public static final String KBN_VALUE_GRADE_A = "A";
		public static final String KBN_VALUE_GRADE_B = "B";
		public static final String KBN_VALUE_GRADE_C = "C";
		
		/** 学年区分の区分名 **/
		public static final String KBN_NAME_GRADE_ZERO = "なし";
		public static final String KBN_NAME_GRADE_ONE = "小学1年生";
		public static final String KBN_NAME_GRADE_TWO = "小学2年生";
		public static final String KBN_NAME_GRADE_THREE = "小学3年生";
		public static final String KBN_NAME_GRADE_FOUR = "小学4年生";
		public static final String KBN_NAME_GRADE_FIVE = "小学5年生";
		public static final String KBN_NAME_GRADE_SIX = "小学6年生";
		public static final String KBN_NAME_GRADE_A = "中学1年生";
		public static final String KBN_NAME_GRADE_B = "中学2年生";
		public static final String KBN_NAME_GRADE_C = "中学3年生";
		
		/** 管理者権限の区分値 **/
		public static final String KBN_VALUE_ADMIN_ON = "1";
		public static final String KBN_VALUE_ADMIN_OFF = "0";
		
		/** 管理者権限の区分名 **/
		public static final String KBN_NAME_ADMIN_ON = "管理者";
		public static final String KBN_NAME_ADMIN_OFF = "一般";
		
		/** 有無の区分値 **/
		public static final String KBN_VALUE_WITH = "1";
		public static final String KBN_VALUE_WITHOUT = "0";
		
		/** 有無の区分名 **/
		public static final String KBN_NAME_WITH = "あり";
		public static final String KBN_NAME_WITHOUT = "なし";
		
		/** 性別の区分値 **/
		public static final String KBN_VALUE_GENDER_MALE = "M";
		public static final String KBN_VALUE_GENDER_FEMALE = "F";
		
		/** 性別の区分名 **/
		public static final String KBN_NAME_GENDER_MALE = "男性";
		public static final String KBN_NAME_GENDER_FEMALE = "女性";
		
		// trimBlank
		/** 半角スペース **/
		public static final String HALF_SPACE = " ";
		/** 全角スペース **/
		public static final String FULL_SPACE = "　";
		
		// makeSelect
		/** セレクトボックスの種類を決めるdivisionの値 **/
		public static final String SELECT_TYPE_GRADE = "grade";
		public static final String SELECT_TYPE_GRADE_AND_BLANK = "gradeAndBlank";
		public static final String SELECT_TYPE_GENDER = "gender";
		public static final String SELECT_TYPE_REGIST = "regist";
		public static final String SELECT_TYPE_ADMINFLG = "adminFlg";
		
		/** mapのvalue **/
		public static final String SELECT_REGIST_VALUE_GROUP = "グループ";
		public static final String SELECT_REGIST_VALUE_USER = "ユーザー（個人）";
		public static final String SELECT_REGIST_VALUE_MULTIUSER = "ユーザー（複数）";
		public static final String SELECT_GENDER_VALUE_MALE = "男性";
		public static final String SELECT_GENDER_VALUE_FEMALE = "女性";
		public static final String SELECT_GRADE_VALUE_ZERO = "なし";
		public static final String SELECT_GRADE_VALUE_ONE = "小学1年生";
		public static final String SELECT_GRADE_VALUE_TWO = "小学2年生";
		public static final String SELECT_GRADE_VALUE_THREE = "小学3年生";
		public static final String SELECT_GRADE_VALUE_FOUR = "小学4年生";
		public static final String SELECT_GRADE_VALUE_FIVE = "小学5年生";
		public static final String SELECT_GRADE_VALUE_SIX = "小学6年生";
		public static final String SELECT_GRADE_VALUE_A = "中学1年生";
		public static final String SELECT_GRADE_VALUE_B = "中学2年生";
		public static final String SELECT_GRADE_VALUE_C = "中学3年生";
		public static final String SELECT_ADMINFLG_VALUE_NORMAL = "一般";
		public static final String SELECT_ADMINFLG_VALUE_ADMIN = "管理者";
		
		/** mapのkey **/
		public static final String SELECT_REGIST_KEY_GROUP = "group";
		public static final String SELECT_REGIST_KEY_USER = "user";
		public static final String SELECT_REGIST_KEY_MULTIUSER = "multiuser";
		public static final String SELECT_GENDER_KEY_MALE = "M";
		public static final String SELECT_GENDER_KEY_FEMALE = "F";
		public static final String SELECT_GRADE_KEY_ZERO = "0";
		public static final String SELECT_GRADE_KEY_ONE = "1";
		public static final String SELECT_GRADE_KEY_TWO = "2";
		public static final String SELECT_GRADE_KEY_THREE = "3";
		public static final String SELECT_GRADE_KEY_FOUR = "4";
		public static final String SELECT_GRADE_KEY_FIVE = "5";
		public static final String SELECT_GRADE_KEY_SIX = "6";
		public static final String SELECT_GRADE_KEY_A = "A";
		public static final String SELECT_GRADE_KEY_B = "B";
		public static final String SELECT_GRADE_KEY_C = "C";
		public static final String SELECT_ADMINFLG_KEY_NORMAL = "0";
		public static final String SELECT_ADMINFLG_KEY_ADMIN = "1";
		
		public static final String SELECT_HTML_PARTS_A1 = "<select name='";
		public static final String SELECT_HTML_PARTS_A2 = "<select id='sch_grade' name='sch_grade";
		public static final String SELECT_HTML_PARTS_B = "'";
		public static final String SELECT_HTML_PARTS_C = ">";
		public static final String SELECT_HTML_PARTS_D = " ";
		public static final String SELECT_HTML_PARTS_E = "id='";
		public static final String SELECT_HTML_PARTS_F = "</select>";
		public static final String SELECT_HTML_PARTS_G = "<option value='";
		public static final String SELECT_HTML_PARTS_H = "'>";
		public static final String SELECT_HTML_PARTS_I = "</option>";
		
	// その他thermoパッケージでしか使わなさそうな定数
		/** ログイン画面から受け取るパラメータを格納する **/
		public static final String THERMO_FORM = "ThermoForm";
		public static final String THERMO_REGEX_PATTERN = "[a-zA-Z0-9]*";
		
	// ThermoInputパッケージでしか使わない定数
		public static final String THERMO_INP_ER = "※半角数字で小数第一位まで入力してください";
		public static final String THERMO_INP_TEMP_ER = "※体温は30.0～49.9の範囲で入力してください";
		public static final String THERMO_ID = "t_";
		public static final String THERMO_LIST = "list";
		public static final String THERMO_USER_LIST = "ulist";
		public static final String THERMO_BIRTH = "birth";
		public static final String THERMO_INP_FORM = "thForm";
		
	// Searchパッケージで使用する定数
		public static final String COMBI_MSG = "チェックボックスは単体で使用してください。";
		public static final String NOLIST_MSG = "検索結果がありませんでした。";
		public static final String TODAY_NOLIST_MSG = "今日の検温情報は登録されていません。";
		public static final String OVER_LIST_MSG = "50件の検索結果を表示しています。";
		public static final String NAME_ERROR = "※記号は入力できません。";
		public static final String DATE_ERROR = "※有効な日付を入力してください。";
		public static final String EXAMPLE = "（例：2020/08/21)";
		public static final String SCH_INFO_REGEX_PATTERN = "[a-zA-Z0-9ａ-ｚA-Zぁ-んァ-ヶー一-龠]+$";
		public static final String MAX_SCH_LISTSTR = "100";
		public static final int MAX_SCH_LISTINT = 100;
		
		public static final String Q_PERCENT = "%";
		

	// UserInfoInput,GroupInfoInputでしか使わない定義
		public static final String USER_INP_GR_ER = "グループIDか、グループパスワードが間違っています";
		public static final String USER_IN_ID_ER = "既に存在しているユーザIDです";
		public static final String USER_INP_ID_ER= "既に登録されているグループIDです";
}
