package com.shantery.thermo.search;

import java.util.List;

import com.shantery.thermo.entity.ThermoInfoEntity;
/**
 * @author y.sato
 * 動的クエリ実行インターフェイス
 * 
 */
public interface SearchRepositoryCustom {
	//検索条件でデータを取得するメソッド
	public List<ThermoInfoEntity> searchQuery(String groupId, SearchInfoForm form);
}
