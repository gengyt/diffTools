package com.cn.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class Diff {
	public static void main(String[] args) {

		// 存放接口文档返回字段
		List<String> excel_Response = new ArrayList<String>();
		// 存放接口运行后的json格式数据
		List<String> json_Response = new ArrayList<String>();

		String excelFilePath = "excel文件存放路径";
		String jsonFilePath = "json文件存放路径";
		String diffFilePath = "差分结果存放路径";

		readFile(excelFilePath, excel_Response);
		readFile(jsonFilePath, json_Response);

		System.out.println("读入的接口文档字段个数为：" + excel_Response.size());
		System.out.println("读入的json字段个数为：" + json_Response.size());

		Writer w;
		try {
			w = new FileWriter(diffFilePath);

			BufferedWriter buffWriter = new BufferedWriter(w);

			for (int i = 0; i < excel_Response.size(); i++) {
				String str[] = excel_Response.get(i).split("=");
				if (!json_Response.contains(str[0])) {
					System.out.println(excel_Response.get(i));
					buffWriter.write(excel_Response.get(i) + "\r\n");
				}
			}
			buffWriter.close();
			w.close();
			System.out.println("查询结果输出成功");
			System.out.println("请查询XXXX盘文件！");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static List<String> readFile(String filePath, List<String> list) {
		try {
			String encoding = "UTF-8";
			File file = new File(filePath);
			// 判断文件是否存在
			if (file.isFile() && file.exists()) {

				InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);

				BufferedReader bufferedReader = new BufferedReader(read);

				String lineTxt = null;
				while ((lineTxt = bufferedReader.readLine()) != null) {
					// 存放jsonFilePath
					if (filePath.contains("Json")) {
						if (filePath.contains(":")) {
							// 截取：前半部分
							String a[] = lineTxt.split(":");
							// 截取引号中间部分
							String para = a[0].substring(1, a[0].length() - 1);
							list.add(para);
						}
					}
					if (filePath.contains("Excel")) {
						list.add(lineTxt);
					}
				}
				read.close();
			} else {
				System.out.println("找不到制定的文件");
			}
		} catch (IOException e) {
			System.out.println("读取文件内容出错");
			e.printStackTrace();
		}
		return list;
	}
}
