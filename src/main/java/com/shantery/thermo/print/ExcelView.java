package com.shantery.thermo.print;


import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HeaderFooter;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Footer;
import org.apache.poi.ss.usermodel.Header;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import com.shantery.thermo.entity.ThermoInfoEntity;
import com.shantery.thermo.util.ThermoReplaceValue;
import static com.shantery.thermo.util.ThermoConstants.*;
/**
 * @author d.ito
 *検索結果をExcelに 
 */

@Component
public class ExcelView extends AbstractXlsxView {
	@Autowired
	protected MessageSource msgPro;
	/**
	 *
	 */
	@SuppressWarnings({ "unchecked" })
	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		//体温初期化
		double thermo = 0;
		//日付を取得
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		//excelファイル名の日付以下を設定
		String fileName = "_検温情報.xlsx";
		//日付とfileNameを結合
		String excelFileName =  sdf.format(new Date()) + URLEncoder.encode(fileName, "UTF-8");
		//ファイル名をセット
		response.setHeader("Content-Disposition", "attachment; filename=" + excelFileName);
		// セッションに接続
		HttpSession session = request.getSession();
		// 検索結果をリストで取得
		List<ThermoInfoEntity> list = (List<ThermoInfoEntity>) session.getAttribute("schlist");	
		Sheet sheet = workbook.createSheet("検温情報"); // シート作成
		// 検温表の項目を配列化
		String[] rowTmp = {"計測日", "名前", "性別", "学年", "年齢", "体温", "味覚障害", "嗅覚障害", "咳", "その他" };
		//列の幅を設定する
		sheet.setColumnWidth(0, 2800); //計測日の幅
		sheet.setColumnWidth(1, 2500); //名前の幅
 		sheet.setColumnWidth(2, 1200); //性別の幅
 		sheet.setColumnWidth(3, 2500); //学年の幅
 		sheet.setColumnWidth(4, 1200); //年齢の幅
 		sheet.setColumnWidth(5, 1650); //体温の幅
 		sheet.setColumnWidth(6, 1150); //味覚障害の幅
 		sheet.setColumnWidth(7, 1150); //嗅覚障害の幅
 		sheet.setColumnWidth(8, 1150); //咳の幅
 		sheet.setColumnWidth(9, 4500); //その他の幅
 
		// セルスタイルを指定
		CellStyle cellstyle = workbook.createCellStyle(); //入力項目のスタイル
		CellStyle indexCellstyle = workbook.createCellStyle(); //見出しのスタイル
		CellStyle highThermo = workbook.createCellStyle(); //37.0度以上の行のスタイル
		CellStyle highThermoRed = workbook.createCellStyle(); //37.5度以上の行のスタイル
		
		//フォントサイズ設定
 		Font font = workbook.createFont();
 		font.setFontHeightInPoints((short)9); //フォントサイズを9に指定
 		font.setFontName("游ゴシック"); //フォントを游ゴシックに指定
 		cellstyle.setFont(font); //通常項目にセット
 		highThermo.setFont(font); //37.0以上項目にセット
 		highThermoRed.setFont(font); //37.5以上項目にセット
		
		int rowNum = 0; //何行目かを指定する変数
		Row row = sheet.createRow(rowNum++); //先頭行を作成
		sheet.createFreezePane(0, 1, 0, 1); //ウィンドウ枠の固定有効化
		sheet.setRepeatingRows(new CellRangeAddress(0, 0, -1, -1)); //一行目の見出しを改ページに対応
		Cell cell = null;	//cellを指定する変数
		
		Header header = sheet.getHeader(); //ヘッダーの作成
		header.setCenter("検温情報"); 
		header.setLeft(HeaderFooter.date()); //日付の指定
	    Footer footer = sheet.getFooter(); //フッターの作成
	    footer.setCenter(HeaderFooter.page() + "/" + HeaderFooter.numPages());
	    //表に必要な分の罫線を作成
	    for (int i = 0; i <= list.size(); i++) {	    	
		    for (int j = 0; j < rowTmp.length; j++) {
				cell = row.createCell(j);
				cellstyle.setBorderLeft(BorderStyle.MEDIUM);
				cellstyle.setBorderRight(BorderStyle.MEDIUM);
				cellstyle.setBorderTop(BorderStyle.MEDIUM);
				cellstyle.setBorderBottom(BorderStyle.MEDIUM);
				cell.setCellStyle(cellstyle);
			}
		    row = sheet.createRow(rowNum++); //改行
	    }
	    rowNum = 0;
	    row = sheet.getRow(rowNum++); //行を先頭に戻す
	    //枠内に書き込む内容を反映する
	    //行の繰り返し
	    for (int i = 0; i <= list.size(); i++) {
	    	//1行目は見出しを出力
			if (i == 0) {
				for (int j = 0; j < rowTmp.length; j++) {
					cell = row.getCell(j);
					cell.setCellValue(rowTmp[j]); //見出しの文字を出力
					//背景色と枠線を出力
					indexCellstyle.setFillPattern(FillPatternType.SOLID_FOREGROUND); //塗りつぶし
					indexCellstyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex()); //背景色グレー
					indexCellstyle.setBorderLeft(BorderStyle.MEDIUM);
					indexCellstyle.setBorderRight(BorderStyle.MEDIUM);
					indexCellstyle.setBorderTop(BorderStyle.MEDIUM);
					indexCellstyle.setBorderBottom(BorderStyle.MEDIUM);
					cell.setCellStyle(indexCellstyle);
				}
			} else {
				//改行を行う
				row = sheet.getRow(rowNum++);
				//体温のnullチェック
				if(list.get(i - 1).getThermo() != null) {
					thermo = Double.parseDouble(list.get(i - 1).getThermo());
				}else {
					thermo = 0;
				}
				//37.0度以上はオレンジ色で塗りつぶす
				if(thermo >= 37.0 && thermo <= 37.5) {
					for (int j = 0; j < rowTmp.length; j++) {
						cell = row.getCell(j);
						highThermo.setFillPattern(FillPatternType.DIAMONDS); //塗りつぶし
						highThermo.setFillForegroundColor(IndexedColors.LIGHT_ORANGE .getIndex()); //背景色オレンジ
						highThermo.setBorderLeft(BorderStyle.MEDIUM);
						highThermo.setBorderRight(BorderStyle.MEDIUM);
						highThermo.setBorderTop(BorderStyle.MEDIUM);
						highThermo.setBorderBottom(BorderStyle.MEDIUM);
						cell.setCellStyle(highThermo);
					}
				}
				//37.0度以上は赤色で塗りつぶす
				if(thermo >= 37.5) {
					for (int j = 0; j < rowTmp.length; j++) {
						cell = row.getCell(j);
						highThermoRed.setFillPattern(FillPatternType.DIAMONDS); //塗りつぶし
						highThermoRed.setFillForegroundColor(IndexedColors.RED1 .getIndex()); //背景色赤
						highThermoRed.setBorderLeft(BorderStyle.MEDIUM);
						highThermoRed.setBorderRight(BorderStyle.MEDIUM);
						highThermoRed.setBorderTop(BorderStyle.MEDIUM);
						highThermoRed.setBorderBottom(BorderStyle.MEDIUM);
						cell.setCellStyle(highThermoRed);
					}
				}
				//1列目に日付を挿入
				cell = row.getCell(0);
				cell.setCellValue(list.get(i - 1).getRegist_date());
				//2列目に名前を挿入
				cell = row.getCell(1);
				cell.setCellValue(list.get(i - 1).getUserInfoEntity().getUser_name());
				//3列目に性別を挿入
				cell = row.getCell(2);
				cell.setCellValue(ThermoReplaceValue.valueToName(KBN_TYPE_GENDER, (list.get(i - 1).getUserInfoEntity().getGender())));
				//4列目に学年を挿入
				cell = row.getCell(3);
				cell.setCellValue(ThermoReplaceValue.valueToName(KBN_TYPE_GRADE, (list.get(i - 1).getUserInfoEntity().getGrade())));
				//5列目に年齢を挿入
				cell = row.getCell(4);
				cell.setCellValue(ThermoReplaceValue.calcAge(list.get(i - 1).getUserInfoEntity().getBirthday()) + "歳");
				//6列目に体温を挿入
				cell = row.getCell(5);
				//体温のnullチェック
				if(list.get(i - 1).getThermo() == null) {
					cell.setCellValue("");
				}else {
					cell.setCellValue(list.get(i - 1).getThermo() + "度");
				}
				//7列目に味覚障害を挿入
				cell = row.getCell(6);
				cell.setCellValue(ThermoReplaceValue.valueToName(KBN_TYPE_EXISTENCE, (list.get(i - 1).getTaste_disorder())));
				//8列目に嗅覚障害を挿入
				cell = row.getCell(7);
				cell.setCellValue(ThermoReplaceValue.valueToName(KBN_TYPE_EXISTENCE, (list.get(i - 1).getTaste_disorder())));
				//9列目に咳を挿入
				cell = row.getCell(8);
				cell.setCellValue(ThermoReplaceValue.valueToName(KBN_TYPE_EXISTENCE, (list.get(i - 1).getCough())));
				//10列目にその他を挿入
				cell = row.getCell(9);
				cell.setCellValue(list.get(i - 1).getOther());
			}
		}	
		cellstyle.setWrapText(true); //改行を有効化
		indexCellstyle.setWrapText(true); //見出しの改行を有効化
		highThermo.setWrapText(true); //色付きcellの改行を有効化
		highThermoRed.setWrapText(true); //色付きcellの改行を有効化
		cellstyle.setVerticalAlignment(VerticalAlignment.TOP); //cellサイズを調節した際文字を上揃え
		indexCellstyle.setVerticalAlignment(VerticalAlignment.TOP); //見出しのcellサイズを調節した際文字を上揃え
		highThermo.setVerticalAlignment(VerticalAlignment.TOP); //色付きcellサイズを調節した際文字を上揃え
		highThermoRed.setVerticalAlignment(VerticalAlignment.TOP); //色付きcellサイズを調節した際文字を上揃え
	}
}
