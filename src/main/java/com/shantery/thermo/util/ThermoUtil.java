package com.shantery.thermo.util;

import static com.shantery.thermo.util.ThermoConstants.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author y.okino
 *Thermoシステムで使用する共通クラス
 *Thermo固有の共通した処理はこのクラスに集約
 */
public class ThermoUtil {
	
	/** インスタンス生成禁止 **/
	private ThermoUtil() {
	}
	
	/**
	 * 表示する見出しを返却する
	 * 列数名はapplication.propertiesより取得
	 * @param columns 列名（当クラスで設定ファイルからインジェクションすることは不可の為、引数で受け取る）
	 * @return 表示する見出し
	 */
	public static List<String> getColumnName(String columns) {

		// プロパティファイルから取得した項目をカンマで分割
		String[] columnArrays = columns.split(COMMA);
		// 表示する見出しリスト
		List<String> columnList = new ArrayList<>();
		// 表示する件数分繰り返す
		Arrays.stream(columnArrays).forEach(columnList::add);
		// 表示する見出しを返却
		return columnList;
	}

	/**
	 * 表示する見出しの数を返却する
	 * 列数名はapplication.propertiesより取得
	 * @param columns 列名（当クラスで設定ファイルからインジェクションすることは不可の為、引数で受け取る）
	 * @return 表示する見出しの個数
	 */
	public static int getColumnCount(String columns) {

		// プロパティファイルから取得した項目をカンマで分割
		String[] columnArrays = columns.split(COMMA);
		// 表示する見出しの数を返却
		return columnArrays.length;
	}

}
