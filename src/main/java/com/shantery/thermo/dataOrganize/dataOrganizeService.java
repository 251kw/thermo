package com.shantery.thermo.dataOrganize;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author d.ito
 *データ整備のサービスクラス
 */
@Service
public class dataOrganizeService {
	@Autowired
	dataOrganizeRepository do_repository;
	@Autowired
	dataOrganizeRepositoryUser dou_repository;
	@Autowired
	dataOrganizeRepositoryGroup dog_repository;
	@Autowired
	HttpSession session;
	
	/**
	 *最終更新から一年以上経過した検温情報を削除し、削除したユーザーの情報をリスト化
	 */
	public void thrmodelete() {
		List<String> tdList = do_repository.thermoDelList(); //検温情報を削除されたユーザーの情報
		session.setAttribute("tdList", tdList);
		do_repository.thermoDel();
	}

	/**
	 *検温情報の最終更新が一年以上経過しているユーザーを削除するメソッド
	 */
	public void userDelete() {
		@SuppressWarnings("unchecked")
		List<String> tdList = (List<String>) session.getAttribute("tdList");
		for (int i = 0; i <= tdList.size() - 1; i++) {
			dou_repository.userDel(tdList.get(i));
		}
		//セッションを削除
		session.removeAttribute("tdList");
	}	

}
