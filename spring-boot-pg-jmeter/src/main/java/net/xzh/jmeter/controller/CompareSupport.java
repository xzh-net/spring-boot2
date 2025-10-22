package net.xzh.jmeter.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * PostgreSQL数据库对比工具
 * @author xzh
 *
 */
public class CompareSupport {

	private static final String SQL_GET_TABLES = "SELECT * FROM pg_tables WHERE schemaname = 'public'";
	private static final String POSTGRESQL_DRIVER = "org.postgresql.Driver";

	// 数据库配置1
	private static final String DB1_IP = "172.17.17.57:5432";
	private static final String DB1_NAME = "vjsp_base";
	private static final String DB1_USER = "vjsp_base";
	private static final String DB1_PASSWORD = "Ps6RB4oxjKdauxol";
	// 数据库配置2
	private static final String DB2_IP = "172.17.17.57:5432";
	private static final String DB2_NAME = "vjsp_iot_platform";
	private static final String DB2_USER = "vjsp_iot_platform";
	private static final String DB2_PASSWORD = "Gc8KQK4gqQDSrXRl";

	public static void main(String[] args) {
		Connection connection1 = null;
		Connection connection2 = null;

		try {
			// 获取数据库连接
			connection1 = createDatabaseConnection(DB1_IP, DB1_NAME, DB1_USER, DB1_PASSWORD);
			connection2 = createDatabaseConnection(DB2_IP, DB2_NAME, DB2_USER, DB2_PASSWORD);

			if (connection1 == null || connection2 == null) {
				System.err.println("数据库连接失败，无法进行比较");
				return;
			}

			// 查询表信息
			List<Map<String, Object>> database1Tables = getTableInformation(connection1, SQL_GET_TABLES);
			List<Map<String, Object>> database2Tables = getTableInformation(connection2, SQL_GET_TABLES);

			System.out.println("开始比较数据库: " + DB1_NAME + " (" + DB1_IP + ") 与 " + DB2_NAME + " (" + DB2_IP + ")");
			System.out.println("===============================================");

			// 执行数据库比较
			performDatabaseComparison(database1Tables, database2Tables, connection1, connection2);

		} catch (Exception e) {
			System.err.println("比较过程中发生错误: " + e.getMessage());
			e.printStackTrace();
		} finally {
			// 关闭数据库连接
			closeConnection(connection1);
			closeConnection(connection2);
		}
	}

	/**
	 * 创建数据库连接
	 */
	private static Connection createDatabaseConnection(String ip, String databaseName, String username,
			String password) {
		String connectionUrl = "jdbc:postgresql://" + ip + "/" + databaseName;

		try {
			Class.forName(POSTGRESQL_DRIVER);
			return DriverManager.getConnection(connectionUrl, username, password);
		} catch (ClassNotFoundException e) {
			System.err.println("PostgreSQL驱动加载失败: " + e.getMessage());
			return null;
		} catch (SQLException e) {
			System.err.println("数据库连接失败 [" + databaseName + "]: " + e.getMessage());
			return null;
		}
	}

	/**
	 * 执行完整的数据库比较
	 */
	private static void performDatabaseComparison(List<Map<String, Object>> database1Tables,
			List<Map<String, Object>> database2Tables, Connection connection1, Connection connection2) {

		// 提取表名集合
		Set<String> database1TableNames = extractTableNames(database1Tables);
		Set<String> database2TableNames = extractTableNames(database2Tables);

		System.out.println("数据库 " + DB1_NAME + " 共有表: " + database1TableNames.size() + " 个");
		System.out.println("数据库 " + DB2_NAME + " 共有表: " + database2TableNames.size() + " 个");
		System.out.println();

		// 比较表差异
		compareTableDifferences(database1TableNames, database2TableNames);

		// 比较共有表的字段差异
		compareColumnDifferences(database1TableNames, database2TableNames, connection1, connection2);
	}

	/**
	 * 从表信息中提取表名
	 */
	private static Set<String> extractTableNames(List<Map<String, Object>> tableInformation) {
		Set<String> tableNames = new HashSet<>();
		for (Map<String, Object> tableInfo : tableInformation) {
			String tableName = (String) tableInfo.get("tablename");
			if (tableName != null && !tableName.trim().isEmpty()) {
				tableNames.add(tableName);
			}
		}
		return tableNames;
	}

	/**
	 * 比较表差异
	 */
	private static void compareTableDifferences(Set<String> database1Tables, Set<String> database2Tables) {
		// 数据库2中缺少的表
		Set<String> missingInDatabase2 = new HashSet<>(database1Tables);
		missingInDatabase2.removeAll(database2Tables);

		// 数据库1中缺少的表
		Set<String> missingInDatabase1 = new HashSet<>(database2Tables);
		missingInDatabase1.removeAll(database1Tables);

		int totalTableDifferences = missingInDatabase1.size() + missingInDatabase2.size();

		// 输出结果
		if (!missingInDatabase2.isEmpty()) {
			System.out.println("❌ 数据库 " + DB2_NAME + " 缺少以下表 (" + missingInDatabase2.size() + " 个):");
			for (String tableName : missingInDatabase2) {
				System.out.println("   - " + tableName);
			}
			System.out.println();
		}

		if (!missingInDatabase1.isEmpty()) {
			System.out.println("❌ 数据库 " + DB1_NAME + " 缺少以下表 (" + missingInDatabase1.size() + " 个):");
			for (String tableName : missingInDatabase1) {
				System.out.println("   - " + tableName);
			}
			System.out.println();
		}

		if (totalTableDifferences == 0) {
			System.out.println("✅ 两个数据库的表结构完全一致");
		} else {
			System.out.println("📊 表差异统计: " + totalTableDifferences + " 个表不相同");
		}
		System.out.println();
	}

	/**
	 * 比较字段差异
	 */
	private static void compareColumnDifferences(Set<String> database1Tables, Set<String> database2Tables,
			Connection connection1, Connection connection2) {

		// 获取共有表
		Set<String> commonTables = new HashSet<>(database1Tables);
		commonTables.retainAll(database2Tables);

		if (commonTables.isEmpty()) {
			System.out.println("⚠️  没有共有的表，跳过字段比较");
			return;
		}

		System.out.println("开始比较 " + commonTables.size() + " 个共有表的字段结构...");
		System.out.println();

		int totalColumnDifferences = 0;
		int tablesWithDifferences = 0;

		for (String tableName : commonTables) {
			Set<String> database1Columns = getTableColumns(connection1, tableName);
			Set<String> database2Columns = getTableColumns(connection2, tableName);

			int columnDifferences = compareTableColumns(tableName, database1Columns, database2Columns);
			totalColumnDifferences += columnDifferences;

			if (columnDifferences > 0) {
				tablesWithDifferences++;
			}
		}

		// 输出字段比较总结
		System.out.println("===============================================");
		if (totalColumnDifferences == 0) {
			System.out.println("✅ 所有共有表的字段结构完全一致");
		} else {
			System.out.println("📊 字段差异统计:");
			System.out.println("   - " + tablesWithDifferences + " 个表存在字段差异");
			System.out.println("   - 总共 " + totalColumnDifferences + " 个字段不相同");
		}
	}

	/**
	 * 获取表的字段列表
	 */
	private static Set<String> getTableColumns(Connection connection, String tableName) {
		String columnQuery = "SELECT A.attname " + "FROM pg_catalog.pg_attribute A "
				+ "WHERE A.attrelid = (SELECT oid FROM pg_class WHERE relname = ?) "
				+ "AND A.attnum > 0 AND NOT A.attisdropped " + "ORDER BY A.attnum";

		Set<String> columns = new HashSet<>();
		try (PreparedStatement preparedStatement = connection.prepareStatement(columnQuery)) {
			preparedStatement.setString(1, tableName);
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					String columnName = resultSet.getString("attname");
					if (columnName != null) {
						columns.add(columnName);
					}
				}
			}
		} catch (SQLException e) {
			System.err.println("查询表 " + tableName + " 的字段信息失败: " + e.getMessage());
		}
		return columns;
	}

	/**
	 * 比较单个表的字段差异
	 */
	private static int compareTableColumns(String tableName, Set<String> database1Columns,
			Set<String> database2Columns) {
		// 数据库2中缺少的字段
		Set<String> missingInDatabase2 = new HashSet<>(database1Columns);
		missingInDatabase2.removeAll(database2Columns);

		// 数据库1中缺少的字段
		Set<String> missingInDatabase1 = new HashSet<>(database2Columns);
		missingInDatabase1.removeAll(database1Columns);

		int totalDifferences = missingInDatabase1.size() + missingInDatabase2.size();

		if (totalDifferences > 0) {
			System.out.println("📋 表 '" + tableName + "' 字段差异:");

			if (!missingInDatabase2.isEmpty()) {
				System.out.println("   → 数据库 " + DB2_NAME + " 缺少字段 (" + missingInDatabase2.size() + " 个):");
				for (String column : missingInDatabase2) {
					System.out.println("     - " + column);
				}
			}

			if (!missingInDatabase1.isEmpty()) {
				System.out.println("   → 数据库 " + DB1_NAME + " 缺少字段 (" + missingInDatabase1.size() + " 个):");
				for (String column : missingInDatabase1) {
					System.out.println("     - " + column);
				}
			}
			System.out.println();
		}

		return totalDifferences;
	}

	/**
	 * 查询数据库信息（通用方法）
	 */
	private static List<Map<String, Object>> getTableInformation(Connection connection, String sql) {
		List<Map<String, Object>> resultList = new ArrayList<>();

		try (Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(sql)) {

			ResultSetMetaData metaData = resultSet.getMetaData();
			int columnCount = metaData.getColumnCount();

			while (resultSet.next()) {
				Map<String, Object> row = new HashMap<>();
				for (int i = 1; i <= columnCount; i++) {
					String columnName = metaData.getColumnName(i);
					Object columnValue = resultSet.getObject(i);
					row.put(columnName, columnValue);
				}
				resultList.add(row);
			}

		} catch (SQLException e) {
			System.err.println("执行查询失败: " + e.getMessage());
			e.printStackTrace();
		}

		return resultList;
	}

	/**
	 * 安全关闭数据库连接
	 */
	private static void closeConnection(Connection connection) {
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				System.err.println("关闭数据库连接时发生错误: " + e.getMessage());
			}
		}
	}
}