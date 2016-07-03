package cn.lynx.automan.resources.migrate;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.ws.rs.Path;

@Path("/migrateuser")
public class UserMigrateResource {
	Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
	Class.forName("com.mysql.jdbc.Driver");
	
	String mssqlUrl = "jdbc:sqlserver://localhost:1433;DatabaseName=automan";
	String mssqlUsr = "sa";
	String mssqlPwd = "Passw0rd";
	Connection dbConn = DriverManager.getConnection(mssqlUrl, mssqlUsr, mssqlPwd);
	Statement stmt = dbConn.createStatement();
	ResultSet mssqlrs = stmt.executeQuery("select * from [User]");
	
	String mysqlUrl = "jdbc:mysql://localhost:3306/automan?useUnicode=true&characterEncoding=utf8&autoReconnect=true&rewriteBatchedStatements=TRUE";
	String mysqlUsr = "root";
	String mysqlPwd = "passw0rd";
	Connection mysqlConn = DriverManager.getConnection(mysqlUrl, mysqlUsr, mysqlPwd);
	
	mysqlConn.createStatement().executeUpdate("set names utf8mb4;");
	
	PreparedStatement ps1 = mysqlConn.prepareStatement("insert into AM_User_State " + 
	"(`Login Times`, `Publish Times`, `Last Login Time`, `Last IP`, `Status`, `Role`, `Exp Level`, `Currency`) values (?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
	
	PreparedStatement ps2 = mysqlConn.prepareStatement("insert into AM_User " +
	"(`Username`, `Password`, `Real Name`, `Gender`, `Birthday`, `Mobile`, `Email`, `Grade`, `Signature`, `User State Id`) values (?,?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
	
	while (mssqlrs.next()) {
		ps1.setInt(1, mssqlrs.getInt("c_times"));
		ps1.setInt(2, mssqlrs.getInt("c_public"));
		ps1.setDate(3, mssqlrs.getDate("LastLogin"));
		ps1.setString(4, mssqlrs.getString("LastIP"));
		ps1.setInt(5, mssqlrs.getInt("Status"));
		ps1.setInt(6, 0);
		ps1.setInt(7, mssqlrs.getInt("Level"));
		ps1.setInt(8, mssqlrs.getInt("Currency"));
		
		ps1.execute();
		
		ResultSet lastidrs = ps1.getGeneratedKeys();
		
		int lastid = 0;
		if (lastidrs.next()) {
			lastid = lastidrs.getInt(1);
			//lastid ++;
			
			ps2.setString(1, mssqlrs.getString("username"));
			ps2.setString(2, mssqlrs.getString("password"));
			ps2.setString(3, mssqlrs.getString("r_name"));
			ps2.setInt(4, mssqlrs.getInt("r_sex"));
			ps2.setDate(5, mssqlrs.getDate("r_birth"));
			ps2.setString(6, mssqlrs.getString("r_telephone"));
			ps2.setString(7, mssqlrs.getString("r_email"));
			ps2.setString(8, mssqlrs.getString("r_grade"));
			ps2.setString(9, mssqlrs.getString("r_comment"));
			ps2.setInt(10, lastid);
			
			ps2.execute();
			
			System.out.println("insert into the user:" + mssqlrs.getString("username"));
		}
	}
	ps1.close();
	ps2.close();
	
	mssqlrs.close();
	stmt.close();
}
