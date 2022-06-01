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

    @Test
    public void initTable() {
        hBaseRepository.initRepository();
        Map<String, List<String>> tableMap = new HashMap<>();
        tableMap.put("david_topic:actionFlow", Arrays.asList("info", "logs"));
        tableMap.put("david_topic:recording", Arrays.asList("info", "logs"));
        tableMap.put("david_topic:syncLog", Arrays.asList("info", "logs"));
        tableMap.put("david_topic:uploadVersion", Arrays.asList("info", "logs"));
        // hBaseRepository.createManyTable(tableMap);
        hBaseRepository.getAllTableNames();
    }

    // @Test
    public void createOneTable() {
        hBaseRepository.initRepository();
        // 在指定命名空间创建表
        hBaseRepository.createOneTable("david_topic:topictest", "info", "log");
    }

    // @Test
    public void deleteTable() {
        hBaseRepository.deleteTable("david_topic:topictest");
    }

    // @Test
    public void insertManyColumnRecords() {
        String tableName = "david_topic:topictest";
        String rowNumber = "105155";
        String columnFamily = "info";
        List<String> columns = Arrays.asList("id", "title", "poform", "dept", "level", "createUser");
        List<String> values = Arrays.asList("105155", "瑞风S4品牌临时页面", "Mobile", "营销中心", "普通", "王丽");
        hBaseRepository.insertManyColumnRecords(tableName, rowNumber, columnFamily, columns, values);
    }

    // @Test
    public void selectTableByRowNumberAndColumnFamily() {
        String tableName = "david_topic:topictest";
        String rowNumber = "105155";
        String columnFamily = "info";
        hBaseRepository.selectTableByRowNumberAndColumnFamily(tableName, rowNumber, columnFamily);
    }

    // @Test
    public void getAllTableNames() {
        hBaseRepository.getAllTableNames();
    }

    // @Test
    public void insertOneColumnRecords() {
        String tableName = "david_topic:topictest";
        String rowNumber = "105155";
        String columnFamily = "info";
        hBaseRepository.insertOneColumnRecords(tableName, rowNumber, columnFamily, "delete", "否");
    }

    // @Test
    public void deleteDataBycolumn() {
        String tableName = "david_topic:topictest";
        String rowNumber = "105155";
        String columnFamily = "info";
        String column = "delete";
        hBaseRepository.deleteDataByColumn(tableName, rowNumber, columnFamily, column);
    }

    // @Test
    public void getTableDataCount() {
        String tableName = "david_topic:topictest";
        hBaseRepository.getTableDataCount(tableName, "log");
    }

    // @Test
    public void deleteDataByRowNumber() {
        String tableName = "david_topic:topictest";
        String rowNumber = "105155";
        hBaseRepository.deleteDataByRowNumber(tableName, rowNumber);
    }

    // @Test
    public void selectTableAllDataMap() {
        String tableName = "david_topic:topictest";
        hBaseRepository.selectTableAllDataMap(tableName);
    }

    // @Test
    public void selectTableAllDataMapColumnFamily() {
        String tableName = "david_topic:topictest";
        String columnFamily = "log";
        hBaseRepository.selectTableAllDataMap(tableName, columnFamily);
    }

    // @Test
    public void selectOneRowDataMap() {
        String tableName = "david_topic:topictest";
        String rowNumber = "105155";
        hBaseRepository.selectOneRowDataMap(tableName, rowNumber);
    }

    // @Test
    public void selectColumnValue() {
        String tableName = "david_topic:topictest";
        String rowNumber = "105155";
        String columnFamily = "info";
        String column = "title";
        hBaseRepository.selectColumnValue(tableName, rowNumber, columnFamily, column);
    }

    // @Test
    public void deleteDataByRowNumberAndColumnFamily() {
        String tableName = "david_topic:topictest";
        String columnFamily = "info";
        hBaseRepository.deleteDataByColumnFamily(tableName, columnFamily);
    }

    // @Test
    public void selectTableDataByFilter() {
        String tableName = "david_topic:topictest";
        List<String> queryParam = Arrays.asList("id,105155", "dept,营销中心");
        hBaseRepository.selectTableDataByFilter(tableName, "info", queryParam, ",", true);
    }

    // @Test
    public void selectColumnValueDataByFilter() {
        String tableName = "david_topic:topictest";
        List<String> queryParam = Arrays.asList("topicFileId,6282");
        hBaseRepository.selectColumnValueDataByFilter(tableName, "info", queryParam, ",", "id", true);
    }

    /**
     * 分页方式一: 全表扫描并返回经过分页后的数据
     */
    // @Test
    public void selectTableAllDataMapAllPage() {
        String tableName = "david_topic:topictest";
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
        String tableName = "david_topic:topictest";
        int pageSize = 3;
        List<Map<String, Object>> result =
            hBaseRepository.selectTableAllDataMapPage(tableName, pageSize, "32956703305764864");
        System.out.println(result);
    }

}