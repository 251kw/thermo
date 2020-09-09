package com.shantery.thermo.editUserInfoMulti;

import static com.shantery.thermo.util.ThermoConstants.THERMO_REGEX_PATTERN;
import static com.shantery.thermo.util.ThermoConstants.NAME_PATTERN;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.shantery.thermo.entity.UserInfoEntity;
import com.shantery.thermo.util.ThermoUtil;

/**
 * @author k.takahashi
 * ユーザー情報一括変更画面での処理で使うメソッド
 */
@Service
public class EditUserInfoMultiService {

	@Autowired
	EditUserInfoMultiRepository repository;

		/**
		 * ログイン中のユーザーと同じグループ内のユーザー情報を取得する
		 * @param groupid
		 * @param original
		 * @return UserInfoEntity型のリスト
		 */
		public ArrayList<UserInfoEntity> storeOriginalInfo(String groupid,ArrayList<UserInfoEntity> original) {
			
			// ログイン中のユーザーと同じグループ内のユーザー情報を取得
			original = repository.findByGroupIdOrderByUpdateTime(groupid);
			
			return original;
		}
		
		/**
		 * originallistをformに合うように整形する
		 * @param formlist
		 * @param originallist
		 * @return EditUserInfoMultiForm.contents型のリスト
		 */
		public ArrayList<EditUserInfoMultiForm.contents> makeFormList(ArrayList<EditUserInfoMultiForm.contents> formlist, ArrayList<UserInfoEntity> originallist) {

			int i = 0;
			
			// originallistを回す
			for(UserInfoEntity original : originallist) {
				
				// formの型に情報を詰めて最後にリストに追加
				EditUserInfoMultiForm.contents form = new EditUserInfoMultiForm.contents();
				form.setUser_name(original.getUser_name());
				form.setUser_id(original.getUser_id());
				form.setUser_pass(original.getUser_pass());
				form.setGender(original.getGender());
				form.setBirthday(original.getBirthday());
				form.setGrade(original.getGrade());
				form.setAdmin_flg(original.getAdmin_flg());
				
				formlist.add(i, form);
				i++;
			}
			
			return formlist;
		}
		
		/**
		 * @param updatelist
		 * @param inputlist
		 * @param originallist
		 * @return updatelist
		 */
		public ArrayList<EditUserInfoMultiForm.contents> makeUpdateList(
				ArrayList<EditUserInfoMultiForm.contents> updatelist,
				ArrayList<EditUserInfoMultiForm.contents> inputlist,
				ArrayList<EditUserInfoMultiForm.contents> originallist) {
			
			int i = 0;
			
			// inputlistを回す
	        for(EditUserInfoMultiForm.contents input : inputlist) {
	            
	        	// 元々のデータと違う内容が入力されていた場合、リストに追加
        		if(!(input.getUser_name().equals(originallist.get(i).getUser_name()) &&
        				input.getUser_id().equals(originallist.get(i).getUser_id()) &&
        				input.getUser_pass().equals(originallist.get(i).getUser_pass()) &&
        				input.getGender().equals(originallist.get(i).getGender()) &&
        				input.getBirthday().equals(originallist.get(i).getBirthday()) &&
        				input.getGrade().equals(originallist.get(i).getGrade()) &&
        				input.getAdmin_flg().equals(originallist.get(i).getAdmin_flg()))) {
        			
        			updatelist.add(input);
	        	}
    			i++;
	        }
	        
			return updatelist;
		}
		
		/**
		 * 登録するデータをデータベースの型に合うように整形
		 * @param userlist
		 * @param groupid
		 * @return registlist
		 */
		public ArrayList<UserInfoEntity> makeRegistList(
				ArrayList<EditUserInfoMultiForm.contents> userlist, String groupid) {
			
			//現在日時の取得と日付の書式設定
			Calendar calendar = Calendar.getInstance();
			//登録時間
			SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			ArrayList<UserInfoEntity> registlist = new ArrayList<>();
			
			// userlistを回す
			for(EditUserInfoMultiForm.contents form : userlist) {
				
				// 時間を取得しつつUserInfoEntity型のリストにデータを詰めて、最後にリストに追加
				UserInfoEntity entity = new UserInfoEntity();
				
				entity.setUser_id(form.getUser_id());
				entity.setGroup_id(groupid);
				entity.setUser_pass(form.getUser_pass());
				entity.setUser_name(form.getUser_name());
				entity.setGender(form.getGender());
				entity.setBirthday(form.getBirthday());
				entity.setGrade(form.getGrade());
				entity.setAdmin_flg(form.getAdmin_flg());
				entity.setUpdate_time(time.format(calendar.getTime()));
				
				registlist.add(entity);
			}
			
			return registlist;
		}
		
		/**
		 * リスト内の情報をデータベースに登録
		 * @param registlist
		 */
		public void registInfo(ArrayList<UserInfoEntity> registlist) {
			
			// registlistを回す
			for(UserInfoEntity registinfo : registlist) {
				
				// 情報を更新
				repository.saveAndFlush(registinfo);
			
			}
			return;
		}
		
		/**
		 * エラーメッセージを取得
		 * @param errormsg
		 * @return errmsg
		 */
		public String setErrorMsg(String errormsg) {
			
			errormsg = "変更された内容がありません";
			
			return errormsg;
		}
		
		/**
		 * userpass,username,birthdayが有効かチェック
		 * @param updatelist
		 * @param errlist
		 * @return
		 */
		public ArrayList<String[]> checkInputList(ArrayList<EditUserInfoMultiForm.contents> updatelist, ArrayList<String[]> errlist) {
			
			boolean check = false;
			int i = 0;
			
			// updatelistを回す
			for(EditUserInfoMultiForm.contents userinfo : updatelist) {
				
				String text[] = new String[3];
				String passmsg = null;
				String namemsg = null;
				String birthmsg = null;
				
				// それぞれの値をチェックして、無効な値ならエラーメッセージを取得する
				
				// user_pass
				check = checkUserPass(userinfo.getUser_pass(),check);
				if(check==false) {
					passmsg = setInputErrorMsg(passmsg, 1);
					text[0] = passmsg;
				}
				
				// user_name
				check = checkUserName(userinfo.getUser_name(),check);
				if(check==false) {
					namemsg = setInputErrorMsg(namemsg, 2);
					text[1] = namemsg;
				}
				
				// birthday
				check = ThermoUtil.ageLimit(userinfo.getBirthday());
				if(check==false) {
					birthmsg = setInputErrorMsg(birthmsg, 3);
					text[2] = birthmsg;
				}
				
				// リストに追加
				errlist.add(i, text);
				i++;
			}
			return errlist;
		}
		
		/**
		 * checkInputListで使う
		 * @param userpass
		 * @param check
		 * @return
		 */
		public boolean checkUserPass(String userpass, boolean check) {
			
			// 正しければここでtrue
			check=ThermoUtil.checkLogic(THERMO_REGEX_PATTERN, userpass);
			
			// trueなら文字数もチェック
			if(check==true) {
				// 文字数が不正ならfalseにする
				if(!(userpass.length()<=16 && userpass.length()>=4)) { 
					check = false;
				}
			}
			
			return check;
		}
		
		/**
		 * checkInputListで使う
		 * @param username
		 * @param check
		 * @return
		 */
		public boolean checkUserName(String username, boolean check) {
			
			// 正しければここでtrue
			check=ThermoUtil.checkLogic(NAME_PATTERN, username);
			
			// trueなら文字数もチェック
			if(check==true) {
				// 文字数が不正ならfalseにする
				if(!(username.length()<=64 && username.length()>=1)) { 
					check = false;
				}
			}
			
			return check;
		}
		
		/**
		 * checkInputListで使う
		 * @param str
		 * @param n
		 * @return
		 */
		public String setInputErrorMsg(String str, int n) {
			

			switch (n) {
				
				case 1:
					str = "※半角英数字4～64文字";
					break;
					
				case 2:					
					str = "※記号を除く1～64文字";
					break;
					
				case 3:
					str = "※不正な日付";
					break;
			}
			
			return str;
		}
		
		/**
		 * リスト内のnullを削除していく
		 * @param errlist
		 * @param nullcheck
		 * @return
		 */
		@SuppressWarnings("unchecked")
		public boolean checkNull(ArrayList<String[]> errlist, boolean nullcheck) {
			
			ArrayList<String[]> clonelist = new ArrayList<>();
			
			// errlistの中身を変えたくないのでコピーを生成
			clonelist = (ArrayList<String[]>) errlist.clone();
			
			// clonelistをリストの順番が乱れないように逆順に回す
			for (int i = clonelist.size() - 1; i >= 0; i--) {
				
			    String[] value = clonelist.get(i);
			    
			    // リストの要素である配列の中身が全てnullの場合はその要素ごと削除
				if(value[0]==null && value[1]==null && value[2]==null) {
					
					clonelist.remove(i);
				}
			}
			
			// clonelistが空になた場合==入力された値が正常な時
			if(clonelist.isEmpty()) {
				nullcheck = true;
			}

			return nullcheck;
		}
		
}
