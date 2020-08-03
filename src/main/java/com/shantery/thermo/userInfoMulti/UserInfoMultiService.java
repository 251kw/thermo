package com.shantery.thermo.userInfoMulti;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.shantery.thermo.entity.GroupMstEntity;
import com.shantery.thermo.entity.UserInfoEntity;

@Service
public class UserInfoMultiService {
	
	/**
	 * @param usersInfo ユーザー情報の入ったＣＳＶファイル
	 * @return カンマで区切られた情報の配列
	 * @throws ParseException
	 */
	public List<String[]> toStringList(MultipartFile usersInfo) throws ParseException {
		
		List<String[]> lines = new ArrayList<String[]>();
        String division = null;
        try {
            InputStream stream = usersInfo.getInputStream();           
            Reader reader = new InputStreamReader(stream, "Shift_JIS");
            BufferedReader buf= new BufferedReader(reader);
            while((division = buf.readLine()) != null) {
            	String[] line = division.split(",", 0);
            	lines.add(line);
            }
            division = buf.readLine();

        } catch (IOException e) {
            lines = null;
        }
        return lines;
		
	}
	
	public boolean checkUserinfo(List<String[]> usersInfo) {
		boolean bool = false;
		
		for (int i = 0; i < usersInfo.size(); i++) {
			String[] line = usersInfo.get(i);
			if(line.length != 9) {
				bool = true;
				break;
			}
		}
		
		
		return bool;
	}
	
	public boolean checkUserId(List<String[]> usersInfo) {
		boolean bool = false;
		
		for (int i = 0; i < usersInfo.size(); i++) {
			String[] line = usersInfo.get(i);
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
				errmsg = ((i+1) + "行目のログインができませんでした");  //TODO 日本語の外部化
			}
		
		return errmsg;
	}
	
	public String checUserIdDB(Iterable<UserInfoEntity> ulist ,String[] usersInfo, int i) {
		
		String errmsg = null;
		for (UserInfoEntity list : ulist) {
			if(list.getUser_id().equals(usersInfo[2])) { 
				errmsg = ((i+1) + "行目のユーザーIdは既に使われています");
			}
		}
		
		return errmsg;
	}
	
}
