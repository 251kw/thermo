package com.shantery.thermo.dataOrganize;

import javax.servlet.http.HttpSession;

import static com.shantery.thermo.util.ThermoConstants.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class dataOrganizeController {
	@Autowired
	dataOrganizeRepository do_repository;
	@Autowired
	dataOrganizeRepositoryUser dou_repository;
	@Autowired
	dataOrganizeRepositoryGroup dog_repository;
	@Autowired
	dataOrganizeService do_service;
	
	@Autowired
	HttpSession session;
	
	/**
	 * @param model
	 * @return
	 * 検索画面からデータ整備画面に遷移
	 */
	@RequestMapping(value = DATA_ORGANIZE_INP, method = RequestMethod.GET)
	public String thermoDelInput(Model model){
		return TO_DATA_ORGANIZE_INP;		
	}	
	
	/**
	 * @param model
	 * @return
	 * データ整備画面からデータ整備確認画面に遷移
	 */
	@RequestMapping(value = DATA_ORGANIZE_CONF, method = RequestMethod.POST)
	public String thermoDelConfirm(Model model){
		return TO_DATA_ORGANIZE_CONF;		
	}	
	
	/**
	 * @param model
	 * @return
	 * データ整備確認画面から遷移
	 * 一括削除処理を行う
	 * データ整備完了画面へ
	 */
	@RequestMapping(value = DATA_ORGANIZE_REZ, method = RequestMethod.POST)
	public String thermoDel(Model model){
		//最終更新から一年以上経過した検温情報を削除
		do_service.thrmodelete();
		//検温情報の最終更新が一年以上経過しているユーザーを削除
		do_service.userDelete();
		//スーパーユーザー以外の検温情報を保持していないかつ、最終更新日が一年以上経過しているユーザーの情報を取得
		dou_repository.thermoNullList();
		//ユーザー情報を持たないかつ、更新日が一年以上前のグループを削除する	
		dog_repository.groupDel();
		//do_service.groupDelete();
		return TO_DATA_ORGANIZE_REZ;		
	}	

}
