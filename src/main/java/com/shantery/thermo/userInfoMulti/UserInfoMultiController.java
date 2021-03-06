package com.shantery.thermo.userInfoMulti;

import static com.shantery.thermo.util.ThermoConstants.*;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.servlet.http.HttpSession;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.shantery.thermo.util.ThermoUtil;

@Controller
class UserInfoMultiController {

	@Autowired
	UserInfoMultiService uMService; //呼び出すクラス
	@Autowired
	protected MessageSource msgPro;
	@Autowired
	HttpSession session; //呼び出すクラス

	/**
	 * ユーザーを一括登録するファイルをアップロードする画面に遷移させる
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(value = USERS_MULTI_SET, method = RequestMethod.GET) // アプリケーションを起動させたとき、もしくは会社のロゴが押されたとき
	String userInfoInput(Model model) throws ParseException {

		return TO_USERS_MULTI_INP;
	}

	/**
	 * アップロードしたファイルを受けとり、解析したのち、
	 * エラーがなければ確認画面へ、エラーがあればアップロード画面へ戻す
	 * @param usersInfo
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(value = USERS_MULTI_GET, method = RequestMethod.POST)
	String getCSVfile(@RequestParam(name = USERS_CSV) MultipartFile usersInfo, Model model)
			throws ParseException {
		if (session.getAttribute(USERS_INFO_SES) != null) { // もしsessionスコープ内にデータがあるなら削除する
			session.removeAttribute(USERS_INFO_SES);
		}
		
		String rtn;  //遷移先の宣言
		List<String[]> users = uMService.toStringList(usersInfo); //CSVファイルの解析,トリミング
		List<String> errmsg = new ArrayList<>();

		if (usersInfo.isEmpty() && ThermoUtil.getSuffix(usersInfo.getOriginalFilename()).equals(NO_SEND)) { //ファイルが選択されていない場合
			errmsg.add((msgPro.getMessage("view.errUserFileExisting", new String[] {}, Locale.JAPAN)));
		}else if(users == null || usersInfo.isEmpty() || uMService.checkUserinfo(users)) {  //CSVファイルの解析でエラーを検出した場合,ファイルの形式が正しくない場合,空のファイルを送信された場合
			errmsg.add((msgPro.getMessage("view.errUserFileOrder", new String[] {}, Locale.JAPAN)));
		} else if (uMService.checkUserId(users)) { //一括登録するユーザーIDに重複がある場合
			errmsg.add((msgPro.getMessage("view.errUserFileDuplication", new String[] {}, Locale.JAPAN)));
		} else {
			//一括登録するユーザーの数だけ繰り返す
			for (int i = 0; i < users.size(); i++) {
				String getmsg = null; //エラーメッセージの宣言
				getmsg = uMService.loginGroup(users.get(i), i); //グループにログインできるかチェック
				if (getmsg != null) {
					errmsg.add(getmsg); //nullじゃない場合エラーメッセージをadd
				}
				getmsg = null; //エラーメッセージの初期化
				getmsg = uMService.checUserIdDB(users.get(i), i); //DBにログインIDの重複があるか調べる
				if (getmsg != null) {
					errmsg.add(getmsg); //nullじゃない場合エラーメッセージをadd
				}

				//BeanValidation のエラーの受け取り準備
				ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
				Validator validator = factory.getValidator();

				//BeanValidation のインスタンス化
				UserInfoMultiBeanValidation uimbv = new UserInfoMultiBeanValidation();

				//インスタンス化されたBeanにユーザーをset
				uimbv.setAll(users.get(i));

				//エラーをチェック
				Set<ConstraintViolation<UserInfoMultiBeanValidation>> constraintViolations = validator.validate(uimbv);
				int errorCount = constraintViolations.size();
				//エラーを格納するListの宣言
				List<String> getVio = new ArrayList<String>();
				//エラーが検出された場合、エラー仕分けしてエラーメッセージを取得
				if (errorCount > 0) {
					//エラーの数繰り返す
					for (ConstraintViolation<UserInfoMultiBeanValidation> violation : constraintViolations) {
						getVio.add(violation.getMessage());  //エラーの種類を取得
					}
					//エラーメッセージに行数を追加しerrmsgに格納
					for (String getErr : uMService.makeErrMsg(getVio)) {
						errmsg.add((i + 1) + (msgPro.getMessage("view.errUserFileLine", new String[] {}, Locale.JAPAN)) + getErr);
					}

				}

			}

		}

		if (CollectionUtils.isEmpty(errmsg)) { //エラーメッセージがない場合確認画面に
			session.setAttribute(USERS_INFO_SES, users);  //ユーザー情報をセッションにいれる
			model.addAttribute(USERS_HEAD,uMService.getColumnName());  //ヘッドをmodelにいれる
			model.addAttribute(USERS_INFO, users);  //ユーザー情報をmodelにいれる
			rtn = TO_USERS_MULTI_CONF;  //遷移先を確認画面に指定
		} else {  //エラーメッセージがある場合
			model.addAttribute(USERS_ERR, errmsg);  //エラーメッセージをmodelに入れる
			rtn = TO_USERS_MULTI_INP;  //遷移先を入力（アップロード画面）に指定
		}

		return rtn;
	}

	/**
	 * 確認画面で確定を押された時に、データベースに追加して、結果画面に
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(value = USERS_MULTI_CONF_SUC, method = RequestMethod.POST)
	//@Transactional
	String userInfoConfirm(Model model) throws ParseException {
		model.addAttribute(USERS_HEAD,uMService.getColumnName());  //ヘッドをmodelにいれる
		@SuppressWarnings("unchecked")  //未検査のキャストをするため
		List<String[]> users = (List<String[]>) session.getAttribute(USERS_INFO_SES);  //セッションから登録するユーザー情報を取得
		model.addAttribute(USERS_INFO, users);  //modelに登録するユーザー情報をいれる
		uMService.saveUserInfo(users);  //DBに登録
		return TO_USERS_MULTI_RES;
	}

}