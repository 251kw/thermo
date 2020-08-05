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

import com.shantery.thermo.entity.GroupMstEntity;
import com.shantery.thermo.entity.UserInfoEntity;
import com.shantery.thermo.util.ThermoUtil;

@Controller
public class UserInfoMultiController {
	
	@Autowired
	UserInfoMultiService uMService; //呼び出すクラス
	@Autowired
	UserInfoMultiRepository uimr;
	@Autowired
	GroupInfoMultiRepository gimr;
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
	public String userInfoInput(Model model) throws ParseException {
	
		return TO_USERS_MULTI_INP;
	}
	
	/**
	 * アップロードしたファイルを受けとり、解析したのち、
	 * エラーがなければ確認画面へ、エラーがあればアップロード画面へ戻す
	 * @param usersInfo
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(value = USERS_MULTI_GET , method = RequestMethod.POST)
	public String getCSVfile(@RequestParam(name = USERS_CSV) MultipartFile usersInfo,  Model model) throws ParseException {
		if (session.getAttribute(USERS_INFO_SES) != null) { // もしsessionスコープ内にデータがあるなら削除する
			session.removeAttribute(USERS_INFO_SES);
		}
		String rtn = TO_USERS_MULTI_INP;
		List<String[]> users = uMService.toStringList(usersInfo);
		Iterable<GroupMstEntity> glist = gimr.findAll();
		Iterable<UserInfoEntity> ulist = uimr.findAll();
		List<String> errmsg = new ArrayList<>();
		
		if(usersInfo.isEmpty()) {
			errmsg.add("ファイルが選択されていません"); //TODO 日本語の外部化
		}else if(uMService.checkUserinfo(users)){
			errmsg.add("ファイルの配列が正しくありません");
		}else if(uMService.checkUserId(users)) {
			errmsg.add("ログインIdが重複しています");
		}else {
			for (int i = 0; i < users.size(); i++) {
				String getmsg = null;
				getmsg = uMService.loginGroup(glist, users.get(i), i);
				if(getmsg != null) {
					errmsg.add(getmsg);
				}
				getmsg = null;
				getmsg = uMService.checUserIdDB(ulist, users.get(i), i);
				if(getmsg != null) {
					errmsg.add(getmsg);
				}
				
				ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		        Validator validator = factory.getValidator();
				
		        UserInfoMultiBeanValidation uimbv = new UserInfoMultiBeanValidation();
				
					uimbv.setAll(users.get(i));
					  Set<ConstraintViolation<UserInfoMultiBeanValidation>> constraintViolations = validator.validate(uimbv);
					  int errorCount = constraintViolations.size();
					  if (errorCount > 0) {
						  for (ConstraintViolation<UserInfoMultiBeanValidation> violation : constraintViolations) {
							  errmsg.add((i+1) + "行目：" + violation.getMessage());
						  }
				        }
				
		        
		        
			}
			
		}
		
		if (CollectionUtils.isEmpty(errmsg)) {
			session.setAttribute(USERS_INFO_SES, users);
			model.addAttribute(USERS_HEAD, ThermoUtil.getColumnName(msgPro.getMessage("view.usercolumns", new String[] {}, Locale.JAPAN)));
			model.addAttribute(USERS_HEAD_LENG, ThermoUtil.getColumnCount(msgPro.getMessage("view.usercolumns", new String[] {}, Locale.JAPAN)));
			model.addAttribute(USERS_INFO, users);
			rtn = TO_USERS_MULTI_CONF;
		}else {
			model.addAttribute(USERS_ERR, errmsg);
			rtn = TO_USERS_MULTI_INP;
		}
		
		return rtn ;
	}
	
	/**
	 * 確認画面で確定を押された時に、データベースに追加して、結果画面に
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(value = USERS_MULTI_CONF_SUC, method = RequestMethod.POST)
	//@Transactional
	public String userInfoConfirm(Model model) throws ParseException {
		model.addAttribute(USERS_HEAD, ThermoUtil.getColumnName(msgPro.getMessage("view.usercolumns", new String[] {}, Locale.JAPAN)));
		model.addAttribute(USERS_HEAD_LENG, ThermoUtil.getColumnCount(msgPro.getMessage("view.usercolumns", new String[] {}, Locale.JAPAN)));
		@SuppressWarnings("unchecked")
		List<String[]> users = (List<String[]>)session.getAttribute(USERS_INFO_SES);
		model.addAttribute(USERS_INFO, users);
		UserInfoEntity uEntity = new UserInfoEntity();
		for (String[] userInfo : users) {
			uEntity.setUserInfo(userInfo);
			uimr.save(uEntity);
		}
		
		
		return TO_USERS_MULTI_RES ;
	}
	
}