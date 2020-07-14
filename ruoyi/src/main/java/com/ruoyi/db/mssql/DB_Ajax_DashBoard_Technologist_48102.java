package com.ruoyi.db.mssql;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DB_Ajax_DashBoard_Technologist_48102 {

	private static final String IP = "192.168.48.102";
	private static final String DBName = "ERPDATA";
	private static final String USER = "sa";
	private static final String PWD = "park$1299";

	/** 创建数据库对象 */
	private static Connection getSQLConnection() {
		Connection con = null;
		try {
			Class.forName("net.sourceforge.jtds.jdbc.Driver");
			// 加上 useunicode=true;characterEncoding=UTF-8 防止中文乱码
			con = DriverManager.getConnection(
					"jdbc:jtds:sqlserver://" + IP + ":1433/" + DBName + ";useunicode=true;characterEncoding=UTF-8",
					USER, PWD);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			System.out.println("==连接数据库失败===");
		}
		// System.out.println("==连接数据库成功===");
		return con;
	}

	public static void main(String[] args) {

	}



	public static List<HashMap<String,String>> DoGetSampleConfirmation() {
		List<HashMap<String,String>> hashMapList = new ArrayList<>();
		try {
			Connection conn = getSQLConnection();
			String sql = "SELECT\n" +
					"\t内外销,\n" +
					"\tSUM ( CASE WHEN 客户是否下单 = '是' THEN 1 ELSE 0 END ) AS 下单数,\n" +
					"\tCOUNT (内外销) AS 打样数 \n" +
					"FROM\n" +
					"\t[dbo].[chen_打样分析汇总] \n" +
					"GROUP BY\n" +
					"\t内外销;";
			Statement stmt = conn.createStatement();//

			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				// 截面积(mm2)
				HashMap<String,String> map = new HashMap<>();
				map.put("NWX",rs.getString("内外销"));
				map.put("ConfirmationNum",rs.getString("下单数"));
				map.put("SampleNum",rs.getString("打样数"));
				hashMapList.add(map);

			}
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();

		}
		return hashMapList;
	}


	public static List<HashMap<String,String>> DoGetBestOEM() {
		List<HashMap<String,String>> hashMapList = new ArrayList<>();
		try {
			Connection conn = getSQLConnection();
			String sql = "SELECT TOP\n" +
					"\t20 MouldingStyleCode,\n" +
					"\t[折算人民币价税合计] AS CNY \n" +
					"FROM\n" +
					"\t[chen_12个月_A版本BOM销售额] \n" +
					"WHERE\n" +
					"\ttagname = 'OEM' \n" +
					"ORDER BY\n" +
					"\t折算人民币价税合计 DESC";
			Statement stmt = conn.createStatement();//

			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				// 截面积(mm2)
				HashMap<String,String> map = new HashMap<>();
				map.put("MouldingStyleCode",rs.getString("MouldingStyleCode"));
				map.put("CNY",rs.getString("CNY"));
				hashMapList.add(map);

			}
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();

		}
		return hashMapList;
	}

	public static List<HashMap<String,String>> DoGetBestODM() {
		List<HashMap<String,String>> hashMapList = new ArrayList<>();
		try {
			Connection conn = getSQLConnection();
			String sql = "SELECT TOP\n" +
					"\t20 MouldingStyleCode,\n" +
					"\t[折算人民币价税合计] AS CNY \n" +
					"FROM\n" +
					"\t[chen_12个月_A版本BOM销售额] \n" +
					"WHERE\n" +
					"\ttagname = 'ODM' \n" +
					"ORDER BY\n" +
					"\t折算人民币价税合计 DESC";
			Statement stmt = conn.createStatement();//

			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				// 截面积(mm2)
				HashMap<String,String> map = new HashMap<>();
				map.put("MouldingStyleCode",rs.getString("MouldingStyleCode"));
				map.put("CNY",rs.getString("CNY"));
				hashMapList.add(map);

			}
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();

		}
		return hashMapList;
	}

	/**
	 * 已完成订单交期
	 *	根据工艺员共计数量
	 */
	public static List<HashMap<String,String>> DoFinishedSampleAvgDay() {
		List<HashMap<String,String>> hashMapList = new ArrayList<>();
		try {
			Connection conn = getSQLConnection();
			String sql = "SELECT\n" +
					"\tTechnologist,\n" +
					"\tCOUNT(id) as num,\n" +
					"\tAVG (\n" +
					"\tCAST ( [分解天数] AS FLOAT )) AS avg_f,\n" +
					"\tAVG ( CAST ( [交期天数] AS FLOAT ) ) AS avg_j \n" +
					"FROM\n" +
					"\t[dbo].[chen_打样分析汇总] \n" +
					"WHERE\n" +
					"\tStatus = 3 \n" +
					"GROUP BY\n" +
					"\tTechnologist";
			Statement stmt = conn.createStatement();//

			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				// 截面积(mm2)
				HashMap<String,String> map = new HashMap<>();
				map.put("Technologist",rs.getString("Technologist"));
				map.put("avg_f",rs.getString("avg_f"));
				map.put("num",rs.getString("num"));
				map.put("avg_j",rs.getString("avg_j"));

				hashMapList.add(map);

			}
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();

		}
		return hashMapList;
	}

	/**
	 * 未完成打样交期
	 *	根据工艺员共计数量
	 */
	public static List<HashMap<String,String>> DoUnFinishedSampleAvgDay() {
		List<HashMap<String,String>> hashMapList = new ArrayList<>();
		try {
			Connection conn = getSQLConnection();
			String sql = "SELECT\n" +
					"\tTechnologist,\n" +
					"\tCOUNT ( id ) AS num,\n" +
					"\tSUM ( CASE WHEN use_date >= 15 THEN 1 ELSE 0 END )  as day15num,\n" +
					"\tAVG(use_date) as avg_use_date\n" +
					"\tFROM\n" +
					"\t\t( SELECT Technologist, id, DATEDIFF( DAY, [经理审核时间], GETDATE()) AS use_date FROM [dbo].[chen_打样分析汇总] WHERE ( Status = 2 OR Status = 1 ) AND [经理审核时间] IS NOT NULL ) AS db0 \n" +
					"GROUP BY\n" +
					"\tTechnologist";
			Statement stmt = conn.createStatement();//

			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				// 截面积(mm2)
				HashMap<String,String> map = new HashMap<>();
				map.put("Technologist",rs.getString("Technologist"));
				map.put("day15num",rs.getString("day15num"));
				map.put("num",rs.getString("num"));
				map.put("avg_use_date",rs.getString("avg_use_date"));



				hashMapList.add(map);

			}
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();

		}
		return hashMapList;
	}

	/**
	 * 新增订单
	 *
	 */
	public static List<HashMap<String,String>> DayFinishedSampleOrder() {
		List<HashMap<String,String>> hashMapList = new ArrayList<>();
		try {
			Connection conn = getSQLConnection();
			String sql = "SELECT\n" +
					"dbo.[chen_打样分析汇总].Technologist,\n" +
					"dbo.[chen_打样分析汇总].SalesMan,\n" +
					"dbo.[chen_打样分析汇总].MouldingStyleCode,\n" +
					"dbo.[chen_打样分析汇总].[折算人民币价税合计] as CNY\n" +
					"\n" +
					"FROM\n" +
					"dbo.[chen_打样分析汇总]\n" +
					"WHERE\n" +
					"dbo.[chen_打样分析汇总].CreatedOn >= dbo.GetWorkShiftStartDate ( GETDATE( ) - 1 ) AND\n" +
					"dbo.[chen_打样分析汇总].CreatedOn <= dbo.GetWorkShiftStartDate ( GETDATE( ) )\n";
			Statement stmt = conn.createStatement();//

			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				// 截面积(mm2)
				HashMap<String,String> map = new HashMap<>();
				map.put("Technologist",rs.getString("Technologist"));
				map.put("SalesMan",rs.getString("SalesMan"));
				map.put("MouldingStyleCode",rs.getString("MouldingStyleCode"));
				map.put("CNY",rs.getString("CNY"));



				hashMapList.add(map);

			}
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();

		}
		return hashMapList;
	}

	/**
	 * 新开模
	 *
	 */
	public static List<HashMap<String,String>> DayNewMould() {
		List<HashMap<String,String>> hashMapList = new ArrayList<>();
		try {
			Connection conn = getSQLConnection();
			String sql = "SELECT\n" +
					"dbo.MouldManufacture.MouldCode,\n" +
					"CASE dbo.MouldManufacture.ManufactureType\n" +
					"\tWHEN 1 THEN N'新开模'\n" +
					"\tELSE N'修模'\n" +
					"END as new_or_fix\n" +
					",\n" +
					"BD_User.UserName\n" +
					"FROM\n" +
					"dbo.MouldManufacture\n" +
					"INNER JOIN BD_User on dbo.MouldManufacture.CreatedBy = BD_User.UserId\n" +
					"WHERE\n" +
					"dbo.MouldManufacture.CreatedOn >= dbo.GetWorkShiftStartDate ( GETDATE( ) - 1 ) AND\n" +
					"dbo.MouldManufacture.CreatedOn <= dbo.GetWorkShiftStartDate ( GETDATE( ) ) AND\n" +
					"dbo.MouldManufacture.Status <> -1\n";
			Statement stmt = conn.createStatement();//

			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				// 截面积(mm2)
				HashMap<String,String> map = new HashMap<>();
				map.put("UserName",rs.getString("UserName"));
				map.put("new_or_fix",rs.getString("new_or_fix"));
				map.put("MouldCode",rs.getString("MouldCode"));
				hashMapList.add(map);

			}
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();

		}
		return hashMapList;
	}

	/**
	 * 打样完成
	 *
	 */
	public static List<HashMap<String,String>> DayFinishedSample() {
		List<HashMap<String,String>> hashMapList = new ArrayList<>();
		try {
			Connection conn = getSQLConnection();
			String sql = "SELECT\n" +
					"\tdbo.[chen_打样分析汇总].MouldingStyleCode,\n" +
					"\tdbo.[chen_打样分析汇总].Technologist,\n" +
					"\tdbo.[chen_打样分析汇总].FinishedDensity,\n" +
					"\tdbo.[chen_打样分析汇总].FinishedSpeed,\n" +
					"\tdbo.[chen_打样分析汇总].FinishedBy,\n" +
					"\tdbo.[chen_打样分析汇总].[交期天数] as UseDay\n" +
					"FROM\n" +
					"\tdbo.[chen_打样分析汇总] \n" +
					"WHERE\n" +
					"\tdbo.[chen_打样分析汇总].FinishedOn >= dbo.GetWorkShiftStartDate ( GETDATE( ) - 1 ) \n" +
					"\tAND dbo.[chen_打样分析汇总].FinishedOn <= dbo.GetWorkShiftStartDate ( GETDATE( ) )";
			Statement stmt = conn.createStatement();//

			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				// 截面积(mm2)
				HashMap<String,String> map = new HashMap<>();
				map.put("MouldingStyleCode",rs.getString("MouldingStyleCode"));
				map.put("Technologist",rs.getString("Technologist"));
				map.put("FinishedDensity",rs.getString("FinishedDensity"));
				map.put("FinishedSpeed",rs.getString("FinishedSpeed"));
				map.put("FinishedBy",rs.getString("FinishedBy"));
				map.put("UseDay",rs.getString("UseDay"));
				hashMapList.add(map);

			}
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();

		}
		return hashMapList;
	}

	/**
	 * BOM完成
	 *
	 */
	public static List<HashMap<String,String>> DayFinishedBOM() {
		List<HashMap<String,String>> hashMapList = new ArrayList<>();
		try {
			Connection conn = getSQLConnection();
			String sql = "SELECT\n" +
					"dbo.MouldingTechnicalForm.MouldingStyleCode,\n" +
					"dbo.MouldingTechnicalForm.Maker\n" +
					"\n" +
					"FROM\n" +
					"dbo.MouldingTechnicalForm\n" +
					"WHERE\n" +
					"dbo.MouldingTechnicalForm.ModifiedOn >= dbo.GetWorkShiftStartDate ( GETDATE( ) - 1 ) AND\n" +
					"dbo.MouldingTechnicalForm.ModifiedOn <= dbo.GetWorkShiftStartDate ( GETDATE( ) )";
			Statement stmt = conn.createStatement();//

			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				// 截面积(mm2)
				HashMap<String,String> map = new HashMap<>();
				map.put("MouldingStyleCode",rs.getString("MouldingStyleCode"));
				map.put("Maker",rs.getString("Maker"));
				hashMapList.add(map);

			}
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();

		}
		return hashMapList;
	}


	public static String All() {
		StringBuilder jsonpath = new StringBuilder();
		try {
			Connection conn = getSQLConnection();
			String sql = "SELECT * from [chen_打样分析汇总] order by CreatedOn desc FOR JSON PATH";
			Statement stmt = conn.createStatement();//

			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				// 截面积(mm2)
				jsonpath.append(rs.getString(1));


			}
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();

		}
		return jsonpath.toString();
	}


	public static String AllDataDataRange(String fDateStart,String fDateEnd) {
		StringBuilder jsonpath = new StringBuilder();
		try {
			Connection conn = getSQLConnection();
			String sql = "SELECT * from [chen_打样分析汇总] where CreatedOn >= '"+fDateStart+"' and CreatedOn <= '"+fDateEnd+"' order by CreatedOn desc FOR JSON PATH";
			Statement stmt = conn.createStatement();//

			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				// 截面积(mm2)
				jsonpath.append(rs.getString(1));


			}
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();

		}
		return jsonpath.toString();
	}



//除了 画框 婚纱框 相框 镜框

	public static List<HashMap<String,String>> getBestByProductionLine(String ProductionLine) {
		List<HashMap<String,String>> hashMapList = new ArrayList<>();
		try {
			Connection conn = getSQLConnection();
			String sql = "SELECT DISTINCT TOP\n" +
					"\t20 chen_12个月_A版本BOM销售额.MouldingStyleCode,\n" +
					"\t[折算人民币价税合计] AS CNY,\n" +
					"\tMouldingSampleMakingMasterSchedule.ProductionLine ,\n" +
					"\t(SELECT TOP\n" +
					"\t1 SHCountry \n" +
					"FROM\n" +
					"\tMouldingDataStatistics \n" +
					"WHERE MouldingCode = reverse(substring(reverse(chen_12个月_A版本BOM销售额.MouldingStyleCode),charindex('-',reverse(chen_12个月_A版本BOM销售额.MouldingStyleCode)) +1,500)) \n" +
					"GROUP BY SHCountry\n" +
					"ORDER BY sum(SHMouldingSaleLength) ) as Area\n" +
					"FROM\n" +
					"\t[chen_12个月_A版本BOM销售额]\n" +
					"\tLEFT JOIN MouldingSampleMakingMasterSchedule ON MouldingSampleMakingMasterSchedule.MouldingStyleCode = chen_12个月_A版本BOM销售额.MouldingStyleCode \n" +
					"WHERE\n" +
					"\tMouldingSampleMakingMasterSchedule.ProductionLine = '"+ProductionLine+"' \n" +
					"ORDER BY\n" +
					"\t折算人民币价税合计 DESC";
			Statement stmt = conn.createStatement();//

			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				// 截面积(mm2)
				HashMap<String,String> map = new HashMap<>();
				map.put("MouldingStyleCode",rs.getString("MouldingStyleCode"));
				map.put("CNY",rs.getString("CNY"));
				map.put("Area",rs.getString("Area"));
				hashMapList.add(map);

			}
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();

		}
		return hashMapList;
	}



//除了 画框 婚纱框 相框 镜框

	public static List<HashMap<String,String>> getBestByProductionLine_Jiancai( ) {
		List<HashMap<String,String>> hashMapList = new ArrayList<>();
		try {
			Connection conn = getSQLConnection();
			String sql = "SELECT DISTINCT TOP\n" +
					"\t20 chen_12个月_A版本BOM销售额.MouldingStyleCode,\n" +
					"\t[折算人民币价税合计] AS CNY,\n" +
					"\tMouldingSampleMakingMasterSchedule.ProductionLine ,\n" +
					"\t(SELECT TOP\n" +
					"\t1 SHCountry \n" +
					"FROM\n" +
					"\tMouldingDataStatistics \n" +
					"WHERE MouldingCode = reverse(substring(reverse(chen_12个月_A版本BOM销售额.MouldingStyleCode),charindex('-',reverse(chen_12个月_A版本BOM销售额.MouldingStyleCode)) +1,500)) \n" +
					"GROUP BY SHCountry\n" +
					"ORDER BY sum(SHMouldingSaleLength) ) as Area,\n" +
					"\t(SELECT TOP\n" +
					"\t1 SDCountry \n" +
					"FROM\n" +
					"\tMouldingDataStatistics \n" +
					"WHERE MouldingCode = reverse(substring(reverse(chen_12个月_A版本BOM销售额.MouldingStyleCode),charindex('-',reverse(chen_12个月_A版本BOM销售额.MouldingStyleCode)) +1,500)) \n" +
					"GROUP BY SDCountry\n" +
					"ORDER BY sum(SDMouldingSaleLength) ) as Area2\n" +
					"FROM\n" +
					"\t[chen_12个月_A版本BOM销售额]\n" +
					"\tLEFT JOIN MouldingSampleMakingMasterSchedule ON MouldingSampleMakingMasterSchedule.MouldingStyleCode = chen_12个月_A版本BOM销售额.MouldingStyleCode \n" +
					"WHERE\n" +
					"\tSUBSTRING(chen_12个月_A版本BOM销售额.MouldingStyleCode,1,1) = 'J'\n" +
					"\n" +
					"\n" +
					"ORDER BY\n" +
					"\t折算人民币价税合计 DESC";
			Statement stmt = conn.createStatement();//

			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				// 截面积(mm2)
				HashMap<String,String> map = new HashMap<>();
				map.put("MouldingStyleCode",rs.getString("MouldingStyleCode"));
				map.put("CNY",rs.getString("CNY"));
				map.put("Area",rs.getString("Area"));
				hashMapList.add(map);

			}
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();

		}
		return hashMapList;
	}





}

