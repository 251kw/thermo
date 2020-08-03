package com.shantery.thermo.userInfoMulti;

import static com.shantery.thermo.util.ThermoConstants.*;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpSession;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

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
	@RequestMapping(value = USERS_MULTI_SET, method = RequestMethod.POST)
	public ModelAndView userInfoInput(ModelAndView mav) throws ParseException {
		mav.setViewName(TO_USERS_MULTI_INP);
		return mav;
	}
	
	/**
	 * アップロードしたファイルを受けとり、解析したのち、
	 * エラーがなければ確認画面へ、エラーがあればアップロード画面へ戻す
	 * @param usersInfo
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(value = USERS_MULTI_GET , method = RequestMethod.POST)
	public ModelAndView getCSVfile(@RequestParam(name = USERS_CSV) MultipartFile usersInfo,  ModelAndView mav) throws ParseException {
		if (session.getAttribute(USERS_INFO_SES) != null) { // もしsessionスコープ内にデータがあるなら削除する
			session.removeAttribute(USERS_INFO_SES);
		}
		mav.setViewName(TO_USERS_MULTI_INP);
		List<String[]> users = uMService.toStringList(usersInfo);
		List<UserInfoEntity> setUsers = new ArrayList<>();
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
				
				//TODO 入力チェックとメッセージの追加
				UserInfoEntity setUser = new UserInfoEntity();
				setUser.setUser_id(users.get(i)[0]);
				setUser.setGroup_id(users.get(i)[1]);
				setUser.setUser_pass(users.get(i)[2]);
				setUser.setUser_name(users.get(i)[3]);
				setUser.setGender(users.get(i)[4]);
				setUser.setBirthday(users.get(i)[5]);
				setUser.setGrade(users.get(i)[6]);
				setUser.setAdmin_flg(users.get(i)[7]);
				setUser.setUpdate_time(users.get(i)[8]);
				
				setUsers.add(setUser); 
				
			}
			
		}
		
		if (CollectionUtils.isEmpty(errmsg)) {
			session.setAttribute(USERS_INFO_SES, users);
			mav.addObject(USERS_HEAD, ThermoUtil.getColumnName(msgPro.getMessage("view.usercolumns", new String[] {}, Locale.JAPAN)));
			mav.addObject(USERS_HEAD_LENG, ThermoUtil.getColumnCount(msgPro.getMessage("view.usercolumns", new String[] {}, Locale.JAPAN)));
			mav.addObject(USERS_INFO, users);
			mav.setViewName(TO_USERS_MULTI_CONF);
		}else {
			mav.addObject(USERS_ERR, errmsg);
			mav.setViewName(TO_USERS_MULTI_INP);
		}
		
		return mav ;
	}
	
	/**
	 * 確認画面で確定を押された時に、データベースに追加して、結果画面に
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(value = USERS_MULTI_CONF_SUC, method = RequestMethod.POST)
	//@Transactional
	public ModelAndView userInfoConfirm(ModelAndView mav) throws ParseException {
		mav.setViewName(TO_USERS_MULTI_RES);
		mav.addObject(USERS_HEAD, ThermoUtil.getColumnName(msgPro.getMessage("view.usercolumns", new String[] {}, Locale.JAPAN)));
		mav.addObject(USERS_HEAD_LENG, ThermoUtil.getColumnCount(msgPro.getMessage("view.usercolumns", new String[] {}, Locale.JAPAN)));
		@SuppressWarnings("unchecked")  //TODO 川原さんに確認
		List<String[]> users = (List<String[]>)session.getAttribute(USERS_INFO_SES);
		mav.addObject(USERS_INFO, users);
		UserInfoEntity uEntity = new UserInfoEntity();
		for (String[] userInfo : users) {
			uEntity.setUserInfo(userInfo);
			uimr.save(uEntity);
		}
		
		
		return mav ;
	}
	
	@RequestMapping(value = "aaa", method = RequestMethod.GET) // アプリケーションを起動させたとき、もしくは会社のロゴが押されたとき
	public ModelAndView index(ModelAndView mav) throws ParseException {
		mav.setViewName(TO_USERS_MULTI_INP);
	
		return mav;
	}
	
	@RequestMapping(value = "bbb", method = RequestMethod.GET) // アプリケーションを起動させたとき、もしくは会社のロゴが押されたとき
	public ModelAndView sample(ModelAndView mav) throws ParseException {
		mav.setViewName("sample");
	
		return mav;
	}
	
}