package start;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * @author lsn
 * @date 2023/3/17 10:33 AM
 */
public class TestDDJ {

    public static void main(String[] args) {
        String sql = "select * from regressions_all where id = 20";
        //String sql = "select * from regressions_all where is_clean=1 and is_dirty=0";
        //步骤1： 处理数据，从数据库里拿数据，数据按照项目名组合
        Map<String, List<Regression>> regsMap  = MysqlManager.selectCleanRegressions(sql);
        //步骤2： 处理每一个项目
        File projectDir;
        String projectName;
        String[] childs = null;
        for (Map.Entry<String, List<Regression>> entry : regsMap.entrySet()) {
            projectName = entry.getKey();
            System.out.println(projectName);
            projectDir = SourceManager.getProjectDir(projectName);
            //如果文件夹为空，说明处理失败
            childs = projectDir.list();
            if( childs==null || childs.length<=0){
                System.out.println(projectName + " fail");
                continue;
            }
            //步骤3： 处理项目的每一个regression
            System.out.println(entry.getValue());
            for (Regression regression: entry.getValue()){
                System.out.println(regression);

                try {
                    DDJ ddj1 = new DDJ(projectDir, regression,  "ddmin", "bic",true, projectName);
                    DDJ ddj2 = new DDJ(projectDir, regression,  "prodd", "bic",true, projectName);

                    ddj1.start();
                    ddj2.start();
                }
                catch (Exception e){
                    e.printStackTrace();
                }

            }
        }
    }


}
