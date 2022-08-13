package net.xzh;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import net.xzh.repository.HBaseRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HBaseUtilsTest {

    @Autowired
    private HBaseRepository hBaseRepository;

    /**
     * 创建命名空间
     */
    
//    @Test
    public void createNamespace() {
        hBaseRepository.createNamespace("xzh");
    }
    
    /**
     * 创建多表
     */
    
//    @Test
    public void createTable() {
        Map<String, List<String>> tableMap = new HashMap<>();
        tableMap.put("xzh:actionFlow", Arrays.asList("info", "logs"));
        tableMap.put("xzh:recording", Arrays.asList("info", "logs"));
        tableMap.put("xzh:syncLog", Arrays.asList("info", "logs"));
        tableMap.put("xzh:uploadVersion", Arrays.asList("info", "logs"));
        hBaseRepository.createManyTable(tableMap);
        hBaseRepository.getAllTableNames();
    }
    /**
     * 创建单表
     */
//     @Test
    public void createOneTable() {
        // 在指定命名空间创建表
        hBaseRepository.createOneTable("xzh:order", "info", "log");
        hBaseRepository.getAllTableNames();
    }

    /**
     * 删除表
     */
//    @Test
    public void deleteTable() {
        hBaseRepository.deleteTable("xzh:actionFlow");
        hBaseRepository.deleteTable("xzh:recording");
        hBaseRepository.deleteTable("xzh:syncLog");
        hBaseRepository.deleteTable("xzh:uploadVersion");
        hBaseRepository.getAllTableNames();
    }

    /**
     * 插入指定列族下具体列的值
     */
//     @Test
    public void insertManyColumnRecords() {
        String tableName = "xzh:order";
        String rowNumber = "100000";
        String columnFamily = "info";
        List<String> columns = Arrays.asList("id", "title", "poform", "dept", "level", "createUser");
        List<String> values = Arrays.asList("100000", "我们都是好孩子", "Mobile", "开发部", "高级", "张三");
        hBaseRepository.insertManyColumnRecords(tableName, rowNumber, columnFamily, columns, values);
    }
    /**
     * 指定rowkey指定列族下所有字段内容
     */
//     @Test
    public void selectTableByRowNumberAndColumnFamily() {
        String tableName = "xzh:order";
        String rowNumber = "100000";
        String columnFamily = "info";
        hBaseRepository.selectTableByRowNumberAndColumnFamily(tableName, rowNumber, columnFamily);
    }

    /**
     * 获取所有表
     */
    // @Test
    public void getAllTableNames() {
        hBaseRepository.getAllTableNames();
    }

    /**
     * 添加指定列族下具体列的值
     */
//     @Test
    public void insertOneColumnRecords() {
        String tableName = "xzh:order";
        String rowNumber = "100000";
        String columnFamily = "info";
        hBaseRepository.insertOneColumnRecords(tableName, rowNumber, columnFamily, "delete", "否");
    }

    /**
     * 删除指定列族下具体列的值
     */
//     @Test
    public void deleteDataBycolumn() {
        String tableName = "xzh:order";
        String rowNumber = "100000";
        String columnFamily = "info";
        String column = "delete";
        hBaseRepository.deleteDataByColumn(tableName, rowNumber, columnFamily, column);
    }
    /**
     * 获取指定列族下记录条数
     */
//     @Test
    public void getTableDataCount() {
        String tableName = "xzh:order";
        hBaseRepository.getTableDataCount(tableName, "info");
    }

    /**
     * 删除指定rowkey的所有数据
     */
//     @Test
    public void deleteDataByRowNumber() {
        String tableName = "xzh:order";
        String rowNumber = "100000";
        hBaseRepository.deleteDataByRowNumber(tableName, rowNumber);
    }

    /**
     * 查询所有数据
     */
//     @Test
    public void selectTableAllDataMap() {
        String tableName = "xzh:order";
        hBaseRepository.selectTableAllDataMap(tableName);
    }
    /**
     * 根据表名和列簇查询所有数据
     */
    
    // @Test
    public void selectTableAllDataMapColumnFamily() {
        String tableName = "xzh:order";
        String columnFamily = "log";
        hBaseRepository.selectTableAllDataMap(tableName, columnFamily);
    }

//     @Test
    public void selectOneRowDataMap() {
        String tableName = "xzh:order";
        String rowNumber = "100000";
        hBaseRepository.selectOneRowDataMap(tableName, rowNumber);
    }

//     @Test
    public void selectColumnValue() {
        String tableName = "xzh:order";
        String rowNumber = "100000";
        String columnFamily = "info";
        String column = "title";
        hBaseRepository.selectColumnValue(tableName, rowNumber, columnFamily, column);
    }

//     @Test
    public void deleteDataByRowNumberAndColumnFamily() {
        String tableName = "xzh:order";
        String columnFamily = "log";
        hBaseRepository.deleteDataByColumnFamily(tableName, columnFamily);
    }

//     @Test
    public void selectTableDataByFilter() {
        String tableName = "xzh:order";
        List<String> queryParam = Arrays.asList("id,100000", "dept,开发部");//完全匹配
        hBaseRepository.selectTableDataByFilter(tableName, "info", queryParam, ",", true);
    }

//     @Test
    public void selectColumnValueDataByFilter() {
        String tableName = "xzh:order";
        List<String> queryParam = Arrays.asList("topicFileId,100000");
        hBaseRepository.selectColumnValueDataByFilter(tableName, "info", queryParam, ",", "id", true);
    }

    /**
     * 分页方式一: 全表扫描并返回经过分页后的数据
     */
//     @Test
    public void selectTableAllDataMapAllPage() {
        String tableName = "xzh:order";
        int pageSize = 3;
        String key = null;
        int dataCount = pageSize;
        while (dataCount == pageSize) {
            String mobileKey = null;
            if (key != null) {
                mobileKey = key;
            }
            List<Map<String, Object>> result =
                hBaseRepository.selectTableAllDataMapPage(tableName, pageSize, mobileKey);
            if (result != null && result.size() > 0) {
                dataCount = result.size();
                key = result.get(dataCount - 1).get("rowKey").toString();
                System.out.println(key);
            } else {
                break;
            }
        }
    }

    /**
     * 分页方式二： 返回每页数据，下一页需要入参【上一页最后一位的rowKey】
     */
    // @Test
    public void selectTableAllDataMapPage() {
        String tableName = "xzh:order";
        int pageSize = 3;
        List<Map<String, Object>> result =
            hBaseRepository.selectTableAllDataMapPage(tableName, pageSize, "32956703305764864");
        System.out.println(result);
    }

}