package com.shantery.thermo.userInfoMulti;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.shantery.thermo.entity.GroupMstEntity;
import com.shantery.thermo.entity.UserInfoEntity;

@Service
public class UserInfoMultiService {
	
	@Autowired
	protected MessageSource msgPro;
	
	/**
	 * @param usersInfo 登録ユーザー情報の入ったＣＳＶファイル
	 * @return カンマで区切られた情報の配列
	 * @throws ParseException
	 */
	public List<String[]> toStringList(MultipartFile usersInfo) throws ParseException {
		
		//戻り値に設定する変数の宣言
		List<String[]> lines = new ArrayList<String[]>();
		//分割数の宣言
        String division = null;
        try {
        	//CSVファイルの解析
            InputStream stream = usersInfo.getInputStream();           
            Reader reader = new InputStreamReader(stream, "Shift_JIS");
            BufferedReader buf= new BufferedReader(reader);
            //解析したファイルをコンマで分割
            while((division = buf.readLine()) != null) {
            	String[] line = division.split(",", 0);
            	//戻り値に分割した情報をadd
            	lines.add(line);
            }

        } catch (IOException e) { //解析中にエラーを検出した場合nullを返す
            lines = null;
        }
        return lines;  //性別、学年、管理者権限は画面で年齢に変換 ThermoReplaceValue.java を使う
		
	}
	
	/**
	 * @param usersInfo ユーザー情報
	 * @return リプレースしたユーザー情報
	 * @throws ParseException
	 */
	public List<String[]> trimName(List<String[]> usersInfo) throws ParseException {
		
		for (String[] line : usersInfo) {
			line[4] = line[4].trim();
			line[4] = line[4].replace(" ", "　");
			line[4] = line[4].replace("	", "　");
		}
		
        return usersInfo;  //
		
	}
	
	/**
	 * CSVファイルの形式を調べる
	 * @param usersInfo ユーザー情報
	 * @return T/F CSVファイルの形式が正しくなければ T
	 */
	public boolean checkUserinfo(List<String[]> usersInfo) {
		boolean bool = false;
		
		//一括登録するユーザーの長さ繰り返す
		for (int i = 0; i < usersInfo.size(); i++) {
			//カラム数が9じゃなかった場合TRUEに
			if(usersInfo.get(i).length != 9) {
				bool = true;
				break;
			}
		}
		
		
		return bool;
	}
	
	/**
	 * 登録ユーザーIDの重複チェック
	 * @param usersInfo 登録ユーザー情報
	 * @return T/F 登録ユーザー情報に重複があれば T
	 */
	public boolean checkUserId(List<String[]> usersInfo) {
		boolean bool = false;
		
		//一括登録するユーザーの長さ繰り返す
		for (int i = 0; i < usersInfo.size(); i++) {
			String[] line = usersInfo.get(i);
			//1行目と二行目以降にログインIDに重複がないか調べる、その後ループに戻ると2行目と3行目以降を調べる..を繰り返す
			for(int j = i + 1;j < usersInfo.size(); j++) {
				String[] ckline = usersInfo.get(j);
				if(line[2].equals(ckline[2])) {
					bool = true;
					break;
				}
			}
		}
		
		return bool;
	}
	
	/**
	 * ログインの可否のチェックとエラーメッセージの排出
	 * @param glist DB内の情報
	 * @param usersInfo 登録ユーザー情報
	 * @param i 行数
	 * @return エラーメッセージ
	 */
	public String loginGroup(Iterable<GroupMstEntity> glist ,String[] usersInfo,int i) {
		boolean bool = true;
		String errmsg = null;
		
		
			bool = true;
			for (GroupMstEntity list : glist) {  //データベースにあるグループの長さ繰り返す
				if(list.getGroup_id().equals(usersInfo[0])) {  //入力したグループidと同じものが見つかった場合入る
					if(list.getGroup_pass().equals(usersInfo[1])) {  //入力したグループpassと
						bool = false;
					}
				}
				
			}
			
			if(bool == true) { //ログインできないユーザー情報が見つかった場合
				errmsg = ((i+1) + (msgPro.getMessage("view.errUserFileIdAndPassCK", new String[] {}, Locale.JAPAN)));  //TODO 日本語の外部化
			}
		
		return errmsg;
	}
	
	public String checUserIdDB(Iterable<UserInfoEntity> ulist ,String[] usersInfo, int i) {
		
		String errmsg = null;
		for (UserInfoEntity list : ulist) {
			if(list.getUser_id().equals(usersInfo[2])) { 
				errmsg = ((i+1) + (msgPro.getMessage("view.errUserFileIdAlreadyUsed", new String[] {}, Locale.JAPAN)));
			}
		}
		
		return errmsg;
	}
	
	/**
	 * バリデーションビーンからのエラーを解析して画面に表示させるエラーメッセージを返す
	 * @param vios バリデーションビーンから送られてきたエラーメッセージ
	 * @return エラーメッセージ
	 */
	public List<String> makeErrMsg(List<String> vios){
		
		//エアラーメッセージのリストの宣言
		List<String> errmsg = new ArrayList<String>();
		
		//調べる受け取ったエラー該当する場合にaddするための変数の宣言
		String gId = null;
		String gpass = null;
		String uId = null;
		String uPass = null;
		String uName = null;
		String gender = null;
		String birthday = null;
		String grade = null;
		String flg = null;
		
		//エラーの仕分けとメッセージの格納
		for (String vio : vios) {
			if(vio.equals("gId")) {
				gId = (msgPro.getMessage("view.errUserFileGroupId", new String[] {}, Locale.JAPAN));
			}else if(vio.equals("gpass")) {
				gpass = (msgPro.getMessage("view.errUserFileGroupPass", new String[] {}, Locale.JAPAN));
			}else if(vio.equals("uId")) {
				uId = (msgPro.getMessage("view.errUserFileUserId", new String[] {}, Locale.JAPAN));
			}else if(vio.equals("uPass")) {
				uPass = (msgPro.getMessage("view.errUserFileUserPass", new String[] {}, Locale.JAPAN));
			}else if(vio.equals("uName")) {
				uName = (msgPro.getMessage("view.errUserFileUserName", new String[] {}, Locale.JAPAN));
			}else if(vio.equals("gender")) {
				gender = (msgPro.getMessage("view.errUserFileUserGender", new String[] {}, Locale.JAPAN));
			}else if(vio.equals("birthday")) {
				birthday = (msgPro.getMessage("view.errUserFileUserBirthday", new String[] {}, Locale.JAPAN));
			}else if(vio.equals("grade")) {
				grade = (msgPro.getMessage("view.errUserFileUserGrade", new String[] {}, Locale.JAPAN));
			}else if(vio.equals("flg")) {
				flg = (msgPro.getMessage("view.errUserFileUserFlg", new String[] {}, Locale.JAPAN));
			}
			
		}
		
		//nullでない場合のみadd
		if(gId != null) {
			errmsg.add(gId);
		}
		if(gpass != null) {
			errmsg.add(gpass);
		}
		if(uId != null) {
			errmsg.add(uId);
		}
		if(uPass != null) {
			errmsg.add(uPass);
		}
		if(uName != null) {
			errmsg.add(uName);
		}
		if(gender != null) {
			errmsg.add(gender);
		}
		if(birthday != null) {
			errmsg.add(birthday);
		}
		if(grade != null) {
			errmsg.add(grade);
		}
		if(flg != null) {
			errmsg.add(flg);
		}
		
		return errmsg;
	}
	
}
