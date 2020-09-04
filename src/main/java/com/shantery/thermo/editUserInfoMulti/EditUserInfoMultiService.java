package com.shantery.thermo.editUserInfoMulti;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.shantery.thermo.entity.UserInfoEntity;

@Service
public class EditUserInfoMultiService {

	@Autowired
	EditUserInfoMultiRepository repository;

		public ArrayList<UserInfoEntity> storeOriginalInfo(String groupid,ArrayList<UserInfoEntity> original) {
			
			original = repository.findByGroupId(groupid);
			
			return original;
		}
		
		public ArrayList<EditUserInfoMultiForm.contents> makeFormList(ArrayList<EditUserInfoMultiForm.contents> formlist, ArrayList<UserInfoEntity> originallist) {

			int i = 0;
			for(UserInfoEntity original : originallist) {
				
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
		
		public ArrayList<EditUserInfoMultiForm.contents> makeUpdateList(
				ArrayList<EditUserInfoMultiForm.contents> updatelist,
				ArrayList<EditUserInfoMultiForm.contents> inputlist,
				ArrayList<EditUserInfoMultiForm.contents> originallist) {
			
			int i = 0;
	        for(EditUserInfoMultiForm.contents input : inputlist) {
	            
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
		
		public ArrayList<UserInfoEntity> makeRegistList(
				ArrayList<EditUserInfoMultiForm.contents> userlist, String groupid) {
			
			//現在日時の取得と日付の書式設定
			Calendar calendar = Calendar.getInstance();
			//登録時間
			SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			ArrayList<UserInfoEntity> registlist = new ArrayList<>();
			
			for(EditUserInfoMultiForm.contents form : userlist) {
				
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
		
		public void registInfo(ArrayList<UserInfoEntity> registlist) {
			
			for(UserInfoEntity registinfo : registlist) {
				
				repository.saveAndFlush(registinfo);
			
			}
			return;
		}
		
}
