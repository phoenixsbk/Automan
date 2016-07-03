package cn.lynx.automan.resources.migrate;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class ThreadMigrateResource {
	Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
	Class.forName("com.mysql.jdbc.Driver");
	
	String mssqlUrl = "jdbc:sqlserver://localhost:1433;DatabaseName=automan";
	String mssqlUsr = "sa";
	String mssqlPwd = "Passw0rd";
	Connection dbConn = DriverManager.getConnection(mssqlUrl, mssqlUsr, mssqlPwd);
	Statement stmt = dbConn.createStatement();
	ResultSet mssqlrs = stmt.executeQuery("select * from [Bbs] where [bbs_reply]=0");
	
	String mysqlUrl = "jdbc:mysql://localhost:3306/automan?useUnicode=true&characterEncoding=utf8&autoReconnect=true&rewriteBatchedStatements=TRUE";
	String mysqlUsr = "root";
	String mysqlPwd = "passw0rd";
	Connection mysqlConn = DriverManager.getConnection(mysqlUrl, mysqlUsr, mysqlPwd);
	
	mysqlConn.createStatement().executeUpdate("set names utf8mb4;");
	
	PreparedStatement ps1 = mysqlConn.prepareStatement("insert into `AM_Subject` (`Title`, `Publish Date`, `Update Date`, `Read Time`, `On Top`) values (?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
	
	PreparedStatement ps2 = mysqlConn.prepareStatement("insert into `AM_Thread` (`Subject Id`, `Content`, `Publish Date`, `Author Id`, `Author Hidden`," + 
	"`Publish IP`, `Editted`, `Edit Time`, `Status`) values (?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
	
	ResultSet rs1 = mysqlConn.createStatement().executeQuery("select `id`,`Username` from `AM_User`");
	Map<String, Integer> user2id = new HashMap<>();
	while (rs1.next()) {
		user2id.put(rs1.getString("Username"), rs1.getInt("id"));
	}
	rs1.close();
	
	while (mssqlrs.next()) {
		ps1.setString(1, mssqlrs.getString("bbs_title"));
		ps1.setDate(2, mssqlrs.getDate("bbs_date"));
		ps1.setDate(3, mssqlrs.getDate("bbs_newviewdate"));
		ps1.setInt(4, mssqlrs.getInt("bbs_viewpeople"));
		ps1.setBoolean(5, mssqlrs.getBoolean("bbs_top"));
		
		ps1.execute();
		
		ResultSet lastidrs = ps1.getGeneratedKeys();
		
		int lastid = 0;
		if (lastidrs.next()) {
			lastid = lastidrs.getInt(1);
			//lastid ++;
			ps2.setInt(1, lastid);
			ps2.setString(2, mssqlrs.getString("bbs_content"));
			ps2.setDate(3, mssqlrs.getDate("bbs_date"));
			ps2.setInt(4, user2id.get(mssqlrs.getString("bbs_author")));
			ps2.setBoolean(5, mssqlrs.getBoolean("bbs_hide"));
			String ip = mssqlrs.getString("bbs_ip");
			if (ip == null) ip = "";
			ps2.setString(6, ip);
			ps2.setBoolean(7, mssqlrs.getBoolean("Editted"));
			ps2.setDate(8, mssqlrs.getDate("EditTime"));
			ps2.setInt(9, mssqlrs.getInt("enable"));
			
			ps2.addBatch();
			
			ResultSet allsub = dbConn.createStatement().executeQuery("select * from [Bbs] where [bbs_reply]=" + mssqlrs.getInt("ID"));
			while (allsub.next()) {
				ps2.setInt(1, lastid);
				ps2.setString(2, allsub.getString("bbs_content"));
				ps2.setDate(3, allsub.getDate("bbs_date"));
				ps2.setInt(4, user2id.get(allsub.getString("bbs_author")));
				ps2.setBoolean(5, allsub.getBoolean("bbs_hide"));
				String subip = allsub.getString("bbs_ip");
				if (subip == null) subip = "";
				ps2.setString(6, subip);
				ps2.setBoolean(7, allsub.getBoolean("Editted"));
				ps2.setDate(8, allsub.getDate("EditTime"));
				ps2.setInt(9, allsub.getInt("enable"));
				
				ps2.addBatch();
				System.out.println("add batch for subject=" + lastid + ", and bbs=" + allsub.getInt("ID"));
			}
			
			ps2.executeBatch();
			allsub.close();
		}
	}
	ps1.close();
	ps2.close();
	
	mssqlrs.close();
	stmt.close();
}
