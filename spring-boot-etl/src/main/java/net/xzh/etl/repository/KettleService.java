package net.xzh.etl.repository;

import java.io.File;
import java.util.Map;

import org.pentaho.di.core.KettleEnvironment;
import org.pentaho.di.job.Job;
import org.pentaho.di.job.JobMeta;
import org.pentaho.di.trans.Trans;
import org.pentaho.di.trans.TransMeta;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

/**
 * @author NoDeal
 */
@Slf4j
@Service
public class KettleService {
	//ktr、kjb执行文件所在路径
    @Value("${kettle.script.path}")
    private String dirPath;

    /**
     * 执行ktr文件
     * @param filename 文件名
     * @param params 命名参数
     * @param variables 全局变量
     * @return
     */
    public String runKtr(String filename, Map<String, String> params, Map<String, String> variables) {
        try {
            log.info("开始执行[{}]文件，参数如下：", filename);
            KettleEnvironment.init();
            TransMeta tm = new TransMeta(dirPath + File.separator + filename);
            Trans trans = new Trans(tm);
            if (params != null) {
                for(String key: params.keySet()){
                    trans.setParameterValue(key,params.get(key));
                    log.info("参数名：{}, 参数值： {}", key, params.get(key));
                }
            }
            if (variables != null) {
                for(String key: variables.keySet()){
                    trans.setVariable(key,variables.get(key));
                    log.info("变量名：{}, 变量值： {}", key, variables.get(key));
                }
            }
            trans.execute(null);
            trans.waitUntilFinished();
        } catch (Exception e) {
            log.error("执行[{}]报错，错误原因：{}", filename, e.getMessage(), e);
        }
        return "ok";
    }

    /**
     * 执行kjb文件
     * @param filename 文件名
     * @param params 命名参数
     * @param variables 变量
     * @return
     */
    public String runKjb(String filename, Map<String, String> params, Map<String, String> variables) {
        try {
            KettleEnvironment.init();
            JobMeta jm = new JobMeta(dirPath + File.separator + filename, null);
            
            Job job = new Job(null, jm);
            if (params != null) {
                for(String key: params.keySet()){
                    jm.setParameterValue(key, params.get(key));//ktr有参数，但是到了kjb无参，需要手动添加进去，否则无法对应上参数值
                    job.setParameterValue(key,params.get(key));
                    log.info("参数名：{}, 参数值： {}", key, params.get(key));
                }
            }
            if (variables != null) {
                for(String key: variables.keySet()){
                    job.setVariable(key,variables.get(key));
                    log.info("变量名：{}, 变量值： {}", key, variables.get(key));
                }
            }
            
            job.start();
            job.waitUntilFinished();
        } catch (Exception e) {
            log.error("执行[{}]报错，错误原因：{}", filename, e.getMessage(), e);
        }
        return "ok";
    }
}
