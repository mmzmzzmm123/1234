package com.ruoyi.db.mssql;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DB_Ajax_DashBoard_48102 {

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



	public static List<HashMap<String,String>> DoGetCurrentTimeGroup() {
		List<HashMap<String,String>> hashMapList = new ArrayList<>();
		try {
			Connection conn = getSQLConnection();
			String sql = "SELECT * FROM [dbo].[V_chen_Dashboard_当前]  order by " +
					"CAST (" +
					"REPLACE( line, '#', '' ) AS INT)";

			Statement stmt = conn.createStatement();//

			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				// 截面积(mm2)

				HashMap<String,String> map = new HashMap<>();
				map.put("line",rs.getString("line"));
				map.put("Moulds",rs.getString("Moulds"));
				map.put("Person",rs.getString("Person"));
				map.put("NormalCapacity",rs.getString("NormalCapacity"));
				map.put("currentspeed",rs.getString("currentspeed"));
				map.put("FinishedQuantity",rs.getString("FinishedQuantity"));
				map.put("GroupTime",rs.getString("GroupTime"));
				map.put("GroupChangeMouldTime",rs.getString("GroupChangeMouldTime"));
				map.put("CurrentYield",rs.getString("CurrentYield"));
				map.put("CapacityStandardObtainedRate",rs.getString("CapacityStandardObtainedRate"));
				map.put("AvgDensity",rs.getString("AvgDensity"));
				map.put("MouldAndChangeMouldTime",rs.getString("MouldAndChangeMouldTime"));
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


	public static String DoGetCurrent() {
		StringBuilder jsonpath = new StringBuilder();
		try {
			Connection conn = getSQLConnection();
			String sql = "SELECT\n" +
					"\t( SELECT MAX ( MouldingDisplayBoardOperation.OperateTime ) FROM MouldingDisplayBoardOperation WHERE MouldingDisplayBoardOperation.DisplayBoardId = V_MouldingDisplayBoard.Id ) AS LastUpdateDate,\n" +
					"ChangeMould,\n" +
					"\tdbo.V_MouldingDisplayBoard.Id,\n" +
					"\tdbo.V_MouldingDisplayBoard.Line,\n" +
					"\tdbo.V_MouldingDisplayBoard.CustomerName,\n" +
					"\tdbo.V_MouldingDisplayBoard.NormalDensity,\n" +
					"\tdbo.V_MouldingDisplayBoard.Length,\n" +
					"\tdbo.V_MouldingDisplayBoard.NormalCapacity,\n" +
					"\tdbo.V_MouldingDisplayBoard.SoCode,\n" +
					"\tdbo.V_MouldingDisplayBoard.NormalWeight,\n" +
					"\tdbo.V_MouldingDisplayBoard.Quantity,\n" +
					"\tdbo.V_MouldingDisplayBoard.FactCapacity,\n" +
					"\tdbo.V_MouldingDisplayBoard.MouldingStyleCode,\n" +
					"\tdbo.V_MouldingDisplayBoard.CurrentFinishedQuantity\n" +
					"\t\n" +
					"\t,\n" +
					"CASE\n" +
					"\t\t\n" +
					"\t\tWHEN ( CurrentYield IS NULL ) THEN\n" +
					"\t\t0 ELSE CurrentYield \n" +
					"\tEND AS CurrentYield,\n" +
					"CASE\n" +
					"\t\t\n" +
					"\t\tWHEN ( CapacityStandardObtainedRate IS NULL ) THEN\n" +
					"\t\t0 ELSE CapacityStandardObtainedRate \n" +
					"\tEND AS CapacityStandardObtainedRate,\n" +
					"\tdbo.V_MouldingDisplayBoard.SectionalErea,\n" +
					"\tdbo.V_MouldingDisplayBoard.MouldYield,\n" +
					"\tdbo.V_MouldingDisplayBoard.FinishedQuantity,\n" +
					"\tdbo.V_MouldingDisplayBoard.TotalCapacity,\n" +
					"\tdbo.V_MouldingDisplayBoard.ChangeMouldTime,\n" +
					"\tdbo.V_MouldingDisplayBoard.OperatePersonCode,\n" +
					"\tdbo.V_MouldingDisplayBoard.OperatePersonName,\n" +
					"\tdbo.V_MouldingDisplayBoard.Speed,\n" +
					"\tdbo.V_MouldingDisplayBoard.CurrentFinishLength \n" +
					"FROM\n" +
					"\tdbo.V_MouldingDisplayBoard \n" +
					"ORDER BY\n" +
					"\tCAST (\n" +
					"\tREPLACE( dbo.V_MouldingDisplayBoard.Line, '#', '' ) AS INT) FOR JSON PATH";
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




	public static List<HashMap<String,String>> DoGetLastDay() {
		List<HashMap<String,String>> hashMapList = new ArrayList<>();
		try {
			Connection conn = getSQLConnection();
			String sql = "SELECT * FROM V_chen_Dashboard_当天 ORDER BY " +
					"CAST (" +
					"REPLACE( line, '#', '' ) AS INT)";

			Statement stmt = conn.createStatement();//

			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				// 截面积(mm2)



				HashMap<String,String> map = new HashMap<>();
				map.put("line",rs.getString("line"));
				map.put("Moulds",rs.getString("Moulds"));
				map.put("sumbox",rs.getString("sumbox"));
				map.put("sum_box_meter",rs.getString("sum_box_meter"));
				map.put("sum_device_meter",rs.getString("sum_device_meter"));
				map.put("avg_density",rs.getString("avg_density"));
				map.put("avg_currentcapacity",rs.getString("avg_currentcapacity"));
				map.put("avg_normalcapacity",rs.getString("avg_normalcapacity"));
				map.put("lastchangemouldtime",rs.getString("lastchangemouldtime"));
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

	public static List<HashMap<String,String>> DoGet30Day() {
		List<HashMap<String,String>> hashMapList = new ArrayList<>();
		try {
			Connection conn = getSQLConnection();
			String sql = "SELECT * FROM V_chen_Dashboard_当月 ORDER BY "+
					" CAST (" +
					"REPLACE( line, '#', '' ) AS INT)";

			Statement stmt = conn.createStatement();//

			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				// 截面积(mm2)



				HashMap<String,String> map = new HashMap<>();
				map.put("line",rs.getString("line"));
				map.put("Moulds",rs.getString("Moulds"));
				map.put("sumbox",rs.getString("sumbox"));
				map.put("sum_box_meter",rs.getString("sum_box_meter"));
				map.put("sum_device_meter",rs.getString("sum_device_meter"));
				map.put("avg_density",rs.getString("avg_density"));
				map.put("avg_currentcapacity",rs.getString("avg_currentcapacity"));
				map.put("avg_normalcapacity",rs.getString("avg_normalcapacity"));
				map.put("lastchangemouldtime",rs.getString("lastchangemouldtime"));
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


	public static List<HashMap<String,String>> DoGetDateRange(String fstarttime, String fendtime) {
		List<HashMap<String,String>> hashMapList = new ArrayList<>();
		try {
			Connection conn = getSQLConnection();
			String sql = "DECLARE @FStartTime DATETIME\r\n" +
					"DECLARE @FEndTime DATETIME\r\n" +
					"\r\n" +
					"set  @FStartTime='" + fstarttime + "'\r\n" +
					"set  @FEndTime='" + fendtime + "'\r\n" +
					"\r\n" +
					"\r\n" +
					"\r\n" +
					"\r\n" +
					"SELECT\r\n" +
					"	db1.line,\r\n" +
					"	AVG ( avg_density ) AS avg_density,\r\n" +
					"	SUM ( sumbox ) AS sumbox,\r\n" +
					"	MAX ( db2.sum1 ) AS sum_box_meter,\r\n" +
					"	MAX ( db2.sum0 ) AS sum_device_meter,\r\n" +
					"	dbo.chen_cal_line_time_mould ( MIN ( db1.StartTime ), MAX ( db1.EndTime ), db1.Line ) AS Moulds,\r\n" +
					"	SUM (	currentcap * ( boxmeter / db2.sum1 )) AS avg_currentcapacity,\r\n" +
					"	SUM (	db1.NormalCapacity* ( boxmeter / db2.sum1 )) AS avg_normalcapacity,\r\n" +
					"	SUM ( boxmeter / db2.sum1 ) AS 校验字段 ,\r\n" +
					"	avg(db4.ChangeMouldTime) as lastchangemouldtime\r\n" +
					"FROM \r\n" +
					"	(SELECT\r\n" +
					"	dbo.chen_cal_line_difftime( db3.StartTime, db3.EndTime, db3.line, db3.EndTime) as difftime,\r\n" +
					"	db3.Quantity as Quantity,\r\n" +
					"	db3.StartTime as StartTime,\r\n" +
					"	db3.EndTime as EndTime,\r\n" +
					"	db3.line as line,\r\n" +
					"  db3.Length as Length ,\r\n" +
					"	db3.NumberOfEveryKit as NumberOfEveryKit,\r\n" +
					"	db3.MouldingStyleCode as MouldingStyleCode,\r\n" +
					"	db3.SoCode as SoCode,\r\n" +
					"	dbo.chen_cal_line_time_mould ( db3.StartTime, db3.EndTime, Line ) AS Moulds,\r\n" +
					"	dbo.chen_cal_line_time_density ( db3.StartTime, db3.EndTime, Line ) AS avg_density,\r\n" +
					"	\r\n" +
					"	dbo.chen_cal_line_meter ( db3.StartTime, db3.EndTime, Line, @FEndTime ) / dbo.chen_cal_line_difftime( db3.StartTime, db3.EndTime, db3.line, db3.EndTime) * 60 * 60 * 12 AS currentcap,\r\n" +
					"	NormalCapacity,\r\n" +
					"	db3.sumbox AS sumbox,\r\n" +
					"	dbo.chen_cal_line_meter ( db3.StartTime, db3.EndTime, Line, @FEndTime ) AS meter,\r\n" +
					"	(db3.Length * db3.NumberOfEveryKit * db3.sumbox) AS boxmeter\r\n" +
					"	\r\n" +
					"FROM\r\n" +
					"	(\r\n" +
					"	SELECT\r\n" +
					"		db2.id,\r\n" +
					"		db2.line,\r\n" +
					"		SUM ( db1.Quantity ) AS sumbox,\r\n" +
					"		MIN ( db1.OperateTime ) AS minop,\r\n" +
					"		MAX ( db1.OperateTime ) AS maxop,\r\n" +
					"		MAX (db2.Quantity) as Quantity,		\r\n" +
					"		MAX (db2.Length) as Length,\r\n" +
					"		MAX (db2.NumberOfEveryKit) as NumberOfEveryKit,\r\n" +
					"		MAX (db2.MouldingStyleCode) as MouldingStyleCode,\r\n" +
					"		MAX (db2.SoCode) as SoCode,		MAX (db2.NormalCapacity) as NormalCapacity,\r\n" +
					"	( CASE WHEN MIN ( db2.StartTime ) < @FStartTime THEN @FStartTime ELSE MIN ( db2.StartTime ) END ) AS StartTime,\r\n" +
					"	( CASE WHEN MIN ( db2.EndTime ) IS  NULL THEN (dbo.chen_cal_line_op_lasttime(MIN ( db2.EndTime ),db2.line,@FEndTime)) ELSE ( CASE WHEN MIN ( db2.EndTime ) > @FEndTime THEN @FEndTime ELSE MIN ( db2.EndTime ) END ) END ) AS EndTime	FROM\r\n" +
					"		[dbo].[MouldingDisplayBoardOperation] AS db1\r\n" +
					"		INNER JOIN MouldingDisplayBoard AS db2 ON db2.id = db1.DisplayBoardId \r\n" +
					"	WHERE\r\n" +
					"		db1.OperateTime >= @FStartTime \r\n" +
					"		AND db1.OperateTime <= @FEndTime \r\n" +
					"	GROUP BY\r\n" +
					"		db2.id,\r\n" +
					"	db2.line \r\n" +
					"	) AS db3) AS db1\r\n" +
					"	INNER JOIN ( SELECT line, SUM ( meter ) AS sum0, SUM ( boxmeter ) AS sum1 FROM (SELECT\r\n" +
					"	dbo.chen_cal_line_difftime( db3.StartTime, db3.EndTime, db3.line, db3.EndTime) as difftime,\r\n" +
					"	db3.Quantity as Quantity,\r\n" +
					"	db3.StartTime as StartTime,\r\n" +
					"	db3.EndTime as EndTime,\r\n" +
					"	db3.line as line,\r\n" +
					"  db3.Length as Length ,\r\n" +
					"	db3.NumberOfEveryKit as NumberOfEveryKit,\r\n" +
					"	db3.MouldingStyleCode as MouldingStyleCode,\r\n" +
					"	db3.SoCode as SoCode,\r\n" +
					"	dbo.chen_cal_line_time_mould ( db3.StartTime, db3.EndTime, Line ) AS Moulds,\r\n" +
					"	dbo.chen_cal_line_time_density ( db3.StartTime, db3.EndTime, Line ) AS avg_density,\r\n" +
					"	\r\n" +
					"	dbo.chen_cal_line_meter ( db3.StartTime, db3.EndTime, Line, @FEndTime ) / dbo.chen_cal_line_difftime( db3.StartTime, db3.EndTime, db3.line, db3.EndTime) * 60 * 60 * 12 AS currentcap,\r\n" +
					"	NormalCapacity,\r\n" +
					"	db3.sumbox AS sumbox,\r\n" +
					"	dbo.chen_cal_line_meter ( db3.StartTime, db3.EndTime, Line, @FEndTime ) AS meter,\r\n" +
					"	(db3.Length * db3.NumberOfEveryKit * db3.sumbox) AS boxmeter\r\n" +
					"	\r\n" +
					"FROM\r\n" +
					"	(\r\n" +
					"	SELECT\r\n" +
					"		db2.id,\r\n" +
					"		db2.line,\r\n" +
					"		SUM ( db1.Quantity ) AS sumbox,\r\n" +
					"		MIN ( db1.OperateTime ) AS minop,\r\n" +
					"		MAX ( db1.OperateTime ) AS maxop,\r\n" +
					"		MAX (db2.Quantity) as Quantity,		\r\n" +
					"		MAX (db2.Length) as Length,\r\n" +
					"		MAX (db2.NumberOfEveryKit) as NumberOfEveryKit,\r\n" +
					"		MAX (db2.MouldingStyleCode) as MouldingStyleCode,\r\n" +
					"		MAX (db2.SoCode) as SoCode,		MAX (db2.NormalCapacity) as NormalCapacity,\r\n" +
					"	( CASE WHEN MIN ( db2.StartTime ) < @FStartTime THEN @FStartTime ELSE MIN ( db2.StartTime ) END ) AS StartTime,\r\n" +
					"	( CASE WHEN MIN ( db2.EndTime ) IS  NULL THEN (dbo.chen_cal_line_op_lasttime(MIN ( db2.EndTime ),db2.line,@FEndTime)) ELSE ( CASE WHEN MIN ( db2.EndTime ) > @FEndTime THEN @FEndTime ELSE MIN ( db2.EndTime ) END ) END ) AS EndTime	FROM\r\n" +
					"		[dbo].[MouldingDisplayBoardOperation] AS db1\r\n" +
					"		INNER JOIN MouldingDisplayBoard AS db2 ON db2.id = db1.DisplayBoardId \r\n" +
					"	WHERE\r\n" +
					"		db1.OperateTime >= @FStartTime \r\n" +
					"		AND db1.OperateTime <= @FEndTime \r\n" +
					"	GROUP BY\r\n" +
					"		db2.id,\r\n" +
					"	db2.line \r\n" +
					"	) AS db3) AS db3 GROUP BY line ) AS db2 ON db2.line = db1.line\r\n" +
					"	LEFT JOIN (SELECT Line,id,DATEDIFF(MINUTE,StartTime,EndTime) as ChangeMouldTime FROM MouldingDisplayBoard WHERE id IN (SELECT MAX (id) AS id FROM MouldingDisplayBoard WHERE ChangeMould=1 GROUP BY Line)) as db4 on db4.line = db1.line\r\n" +
					"\r\n" +
					"GROUP BY" +
					"	db1.line " +
					"ORDER BY " +
					"CAST (" +
					" REPLACE( db1.line, '#', '' ) AS INT)";
			Statement stmt = conn.createStatement();//

			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				// 截面积(mm2)



				HashMap<String,String> map = new HashMap<>();
				map.put("line",rs.getString("line"));
				map.put("Moulds",rs.getString("Moulds"));
				map.put("sumbox",rs.getString("sumbox"));
				map.put("sum_box_meter",rs.getString("sum_box_meter"));
				map.put("sum_device_meter",rs.getString("sum_device_meter"));
				map.put("avg_density",rs.getString("avg_density"));
				map.put("avg_currentcapacity",rs.getString("avg_currentcapacity"));
				map.put("avg_normalcapacity",rs.getString("avg_normalcapacity"));
				map.put("lastchangemouldtime",rs.getString("lastchangemouldtime"));
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
