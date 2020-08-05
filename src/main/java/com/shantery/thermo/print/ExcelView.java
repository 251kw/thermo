package com.shantery.thermo.print;


import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Header;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import com.shantery.thermo.entity.ThermoInfoEntity;
import com.shantery.thermo.search.SearchEntity;

@Component
public class ExcelView extends AbstractXlsxView {

	@SuppressWarnings("unchecked")
	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// セッションに接続
		HttpSession session = request.getSession();

		// 検索結果をリストで取得
		List<SearchEntity> list = (List<SearchEntity>) session.getAttribute("samplelist");
		// シート作成
		Sheet sheet = workbook.createSheet("検温情報");
		// 検温表の項目を配列化
		String[] rowTmp = { "名前", "性別", "学年", "年齢", "体温", "味覚障害", "嗅覚障害", "咳", "その他" };

 		sheet.setColumnWidth(1, 1150);
 		sheet.setColumnWidth(5, 1150);
 		sheet.setColumnWidth(6, 1150);
 		sheet.setColumnWidth(7, 1150);
 		sheet.setColumnWidth(8, 5500);
		// セルスタイルを指定
		CellStyle cellstyle = workbook.createCellStyle();

		int rowNum = 0;
		Row row = sheet.createRow(rowNum++);
		
		//ヘッダー作成
		Header header = sheet.getHeader();
		header.setCenter("検温情報");
		
		for (int i = 0; i <= list.size(); i++) {
			if (i == 0) {
				for (int j = 0; j < rowTmp.length; j++) {
					Cell cell = row.createCell(j);
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
				//1列目に名前を挿入
				Cell cell = row.createCell(0);
				cell.setCellValue(list.get(i - 1).getUser_name());
				cellstyle.setBorderLeft(BorderStyle.MEDIUM);
				cellstyle.setBorderRight(BorderStyle.MEDIUM);
				cellstyle.setBorderTop(BorderStyle.MEDIUM);
				cellstyle.setBorderBottom(BorderStyle.MEDIUM);
				cell.setCellStyle(cellstyle);
				//2列目に性別を挿入
				cell = row.createCell(1);
				cell.setCellValue(list.get(i - 1).getGender());
				cellstyle.setBorderLeft(BorderStyle.MEDIUM);
				cellstyle.setBorderRight(BorderStyle.MEDIUM);
				cellstyle.setBorderTop(BorderStyle.MEDIUM);
				cellstyle.setBorderBottom(BorderStyle.MEDIUM);
				cell.setCellStyle(cellstyle);
				//3列目に学年を挿入
				cell = row.createCell(2);
				cell.setCellValue(list.get(i - 1).getGrade());
				cellstyle.setBorderLeft(BorderStyle.MEDIUM);
				cellstyle.setBorderRight(BorderStyle.MEDIUM);
				cellstyle.setBorderTop(BorderStyle.MEDIUM);
				cellstyle.setBorderBottom(BorderStyle.MEDIUM);
				cell.setCellStyle(cellstyle);
				//4列目に年齢を挿入
				cell = row.createCell(3);
				cell.setCellValue(list.get(i - 1).getAge());
				cellstyle.setBorderLeft(BorderStyle.MEDIUM);
				cellstyle.setBorderRight(BorderStyle.MEDIUM);
				cellstyle.setBorderTop(BorderStyle.MEDIUM);
				cellstyle.setBorderBottom(BorderStyle.MEDIUM);
				cell.setCellStyle(cellstyle);
				//5列目に体温を挿入
				cell = row.createCell(4);
				cell.setCellValue(list.get(i - 1).getThermo());
				cellstyle.setBorderLeft(BorderStyle.MEDIUM);
				cellstyle.setBorderRight(BorderStyle.MEDIUM);
				cellstyle.setBorderTop(BorderStyle.MEDIUM);
				cellstyle.setBorderBottom(BorderStyle.MEDIUM);
				cell.setCellStyle(cellstyle);
				//6列目に味覚障害を挿入
				cell = row.createCell(5);
				cell.setCellValue(list.get(i - 1).getTaste_disorder());
				cellstyle.setBorderLeft(BorderStyle.MEDIUM);
				cellstyle.setBorderRight(BorderStyle.MEDIUM);
				cellstyle.setBorderTop(BorderStyle.MEDIUM);
				cellstyle.setBorderBottom(BorderStyle.MEDIUM);
				cell.setCellStyle(cellstyle);
				//7列目に嗅覚障害を挿入
				cell = row.createCell(6);
				cell.setCellValue(list.get(i - 1).getOlfactory_disorder());
				cellstyle.setBorderLeft(BorderStyle.MEDIUM);
				cellstyle.setBorderRight(BorderStyle.MEDIUM);
				cellstyle.setBorderTop(BorderStyle.MEDIUM);
				cellstyle.setBorderBottom(BorderStyle.MEDIUM);
				cell.setCellStyle(cellstyle);
				//8列目に咳を挿入
				cell = row.createCell(7);
				cell.setCellValue(list.get(i - 1).getCough());
				cellstyle.setBorderLeft(BorderStyle.MEDIUM);
				cellstyle.setBorderRight(BorderStyle.MEDIUM);
				cellstyle.setBorderTop(BorderStyle.MEDIUM);
				cellstyle.setBorderBottom(BorderStyle.MEDIUM);
				cell.setCellStyle(cellstyle);
				//9列目に性別を挿入
				cell = row.createCell(8);
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
