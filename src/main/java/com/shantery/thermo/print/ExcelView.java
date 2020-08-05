package com.shantery.thermo.print;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HeaderFooter;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Footer;
import org.apache.poi.ss.usermodel.Header;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import com.shantery.thermo.entity.ThermoInfoEntity;
/**
 * @author d.ito
 *検索結果をExcelに 
 */
@Component
public class ExcelView extends AbstractXlsxView {
	public String flagCheck(String flag) {
		String text = null;
		if(flag.equals("0")) {
			text = "なし";
		}else {
			text = "あり";
		}
		return text;
	}
	@SuppressWarnings("unchecked")
	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// セッションに接続
		HttpSession session = request.getSession();
		// 検索結果をリストで取得
		List<ThermoInfoEntity> list = (List<ThermoInfoEntity>) session.getAttribute("samplelist");	
		Sheet sheet = workbook.createSheet("検温情報"); // シート作成
		// 検温表の項目を配列化
		String[] rowTmp = {"計測日", "名前", "性別", "学年", "年齢", "体温", "味覚障害", "嗅覚障害", "咳", "その他" };
		//列の幅を設定する
		sheet.setColumnWidth(0, 2800); //計測日の幅
 		sheet.setColumnWidth(2, 1150); //性別の幅
 		sheet.setColumnWidth(4, 1150); //年齢の幅
 		sheet.setColumnWidth(5, 1150); //体温の幅
 		sheet.setColumnWidth(6, 1150); //味覚障害の幅
 		sheet.setColumnWidth(7, 1150); //嗅覚障害の幅
 		sheet.setColumnWidth(8, 1150); //咳の幅
 		sheet.setColumnWidth(9, 5500); //その他の幅
		// セルスタイルを指定
		CellStyle cellstyle = workbook.createCellStyle();
		
		int rowNum = 0;
		String text = null;
		Row row = sheet.createRow(rowNum++);
		Cell cell = null;	
		
		Header header = sheet.getHeader(); //ヘッダーの作成
		header.setCenter("検温情報");
		header.setLeft(HeaderFooter.date()); //日付の指定
	    Footer footer = sheet.getFooter(); //フッターの作成
	    footer.setCenter(HeaderFooter.page() + "/" + HeaderFooter.numPages());
		
		for (int i = 0; i <= list.size(); i++) {
			if (i == 0) {
				for (int j = 0; j < rowTmp.length; j++) {
					cell = row.createCell(j);
					cell.setCellValue(rowTmp[j]);
					cellstyle.setBorderLeft(BorderStyle.MEDIUM);
					cellstyle.setBorderRight(BorderStyle.MEDIUM);
					cellstyle.setBorderTop(BorderStyle.MEDIUM);
					cellstyle.setBorderBottom(BorderStyle.MEDIUM);
					cell.setCellStyle(cellstyle);
				}
			} else {
				//改行を行う
				row = sheet.createRow(rowNum++);
				//1列目に日付を挿入
				cell = row.createCell(0);
				cell.setCellValue(list.get(i - 1).getRegist_date());
				cellstyle.setBorderLeft(BorderStyle.MEDIUM);
				cellstyle.setBorderRight(BorderStyle.MEDIUM);
				cellstyle.setBorderTop(BorderStyle.MEDIUM);
				cellstyle.setBorderBottom(BorderStyle.MEDIUM);
				cell.setCellStyle(cellstyle);
				//2列目に名前を挿入
				cell = row.createCell(1);
				cell.setCellValue(list.get(i - 1).getCough());
				cellstyle.setBorderLeft(BorderStyle.MEDIUM);
				cellstyle.setBorderRight(BorderStyle.MEDIUM);
				cellstyle.setBorderTop(BorderStyle.MEDIUM);
				cellstyle.setBorderBottom(BorderStyle.MEDIUM);
				cell.setCellStyle(cellstyle);
				//3列目に性別を挿入
				cell = row.createCell(2);
				cell.setCellValue(list.get(i - 1).getThermo());
				cellstyle.setBorderLeft(BorderStyle.MEDIUM);
				cellstyle.setBorderRight(BorderStyle.MEDIUM);
				cellstyle.setBorderTop(BorderStyle.MEDIUM);
				cellstyle.setBorderBottom(BorderStyle.MEDIUM);
				cell.setCellStyle(cellstyle);
				//4列目に学年を挿入
				cell = row.createCell(3);
				cell.setCellValue(list.get(i - 1).getOlfactory_disorder());
				cellstyle.setBorderLeft(BorderStyle.MEDIUM);
				cellstyle.setBorderRight(BorderStyle.MEDIUM);
				cellstyle.setBorderTop(BorderStyle.MEDIUM);
				cellstyle.setBorderBottom(BorderStyle.MEDIUM);
				cell.setCellStyle(cellstyle);
				//5列目に年齢を挿入
				cell = row.createCell(4);
				cell.setCellValue(list.get(i - 1).getThermo_id());
				cellstyle.setBorderLeft(BorderStyle.MEDIUM);
				cellstyle.setBorderRight(BorderStyle.MEDIUM);
				cellstyle.setBorderTop(BorderStyle.MEDIUM);
				cellstyle.setBorderBottom(BorderStyle.MEDIUM);
				cell.setCellStyle(cellstyle);
				//6列目に体温を挿入
				cell = row.createCell(5);
				cell.setCellValue(list.get(i - 1).getThermo());
				cellstyle.setBorderLeft(BorderStyle.MEDIUM);
				cellstyle.setBorderRight(BorderStyle.MEDIUM);
				cellstyle.setBorderTop(BorderStyle.MEDIUM);
				cellstyle.setBorderBottom(BorderStyle.MEDIUM);
				cell.setCellStyle(cellstyle);
				//7列目に味覚障害を挿入
				cell = row.createCell(6);
				if(list.get(i - 1).getTaste_disorder().equals("0")) {
					text = "なし";
				}else {
					text = "あり";
				}
				cell.setCellValue(text);
				cellstyle.setBorderLeft(BorderStyle.MEDIUM);
				cellstyle.setBorderRight(BorderStyle.MEDIUM);
				cellstyle.setBorderTop(BorderStyle.MEDIUM);
				cellstyle.setBorderBottom(BorderStyle.MEDIUM);
				cell.setCellStyle(cellstyle);
				//8列目に嗅覚障害を挿入
				cell = row.createCell(7);
				cell.setCellValue(flagCheck(list.get(i - 1).getTaste_disorder()));
				cell.setCellValue(text);
				cellstyle.setBorderLeft(BorderStyle.MEDIUM);
				cellstyle.setBorderRight(BorderStyle.MEDIUM);
				cellstyle.setBorderTop(BorderStyle.MEDIUM);
				cellstyle.setBorderBottom(BorderStyle.MEDIUM);
				cell.setCellStyle(cellstyle);
				//9列目に咳を挿入
				cell = row.createCell(8);
				if(list.get(i - 1).getCough().equals("0")) {
					text = "なし";
				}else {
					text = "あり";
				}
				cell.setCellValue(text);
				cellstyle.setBorderLeft(BorderStyle.MEDIUM);
				cellstyle.setBorderRight(BorderStyle.MEDIUM);
				cellstyle.setBorderTop(BorderStyle.MEDIUM);
				cellstyle.setBorderBottom(BorderStyle.MEDIUM);
				cell.setCellStyle(cellstyle);
				//10列目にその他を挿入
				cell = row.createCell(9);
				cell.setCellValue(list.get(i - 1).getOther());
				cellstyle.setBorderLeft(BorderStyle.MEDIUM);
				cellstyle.setBorderRight(BorderStyle.MEDIUM);
				cellstyle.setBorderTop(BorderStyle.MEDIUM);
				cellstyle.setBorderBottom(BorderStyle.MEDIUM);
				cell.setCellStyle(cellstyle);
			}
		}
		cellstyle.setWrapText(true); //改行を有効化
	}
}
