package start;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author lsn
 * @date 2023/3/9 8:15 PM
 */
public class TestDDJ {

    static Reducer reducer = new Reducer();
    static Migrator migrator = new Migrator();
    static Executor executor = new Executor();

    public static void main(String[] args) {
        //String sql = "select * from regressions_all where id = 33";
        String sql = "select * from regressions_all where is_clean=1 and is_dirty=0";
        //步骤1： 处理数据，从数据库里拿数据，数据按照项目名组合
        Map<String,List<Regression>>  regsMap  = MysqlManager.selectCleanRegressions(sql);
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
                // 步骤1 checkout bug的四个版本。
                Revision bfc = new Revision(regression.getBfc(),"bfc");
                bfc.setLocalCodeDir(SourceManager.checkout(regression.getId(), bfc, projectDir, projectName));
                regression.setBfcRev(bfc);

                Revision buggy = new Revision(regression.getBfc()+"~1","buggy");
                buggy.setLocalCodeDir(SourceManager.checkout(regression.getId(),buggy, projectDir, projectName));
                regression.setBuggyRev(buggy);

                Revision bic = new Revision(regression.getBic(),"bic");
                bic.setLocalCodeDir(SourceManager.checkout(regression.getId(),bic, projectDir, projectName));
                regression.setBicRev(bic);

                Revision work = new Revision(regression.getWork(),"work");
                work.setLocalCodeDir(SourceManager.checkout(regression.getId(),work, projectDir, projectName));
                regression.setWorkRev(work);

                //步骤2 迁移测试用例
                List<Revision> needToTestMigrateRevisionList = Arrays.asList(new Revision[]{buggy, bic, work});
                migrateTestAndDependency(bfc, needToTestMigrateRevisionList, regression.getTestCaseString());

                SourceManager.createShell(regression.getId(), projectName, bic, regression.getTestCaseString());
                SourceManager.createShell(regression.getId(), projectName, work, regression.getTestCaseString());
                SourceManager.createShell(regression.getId(), projectName, bfc, regression.getTestCaseString());
                SourceManager.createShell(regression.getId(), projectName, buggy, regression.getTestCaseString());

                try {
                    executor.setDirectory(new File(Main.workSpacePath));
                    ddj(regression.getId(), new File(SourceManager.getProjectCacheDir(projectName)));
                }
                catch (Exception e){
                    e.printStackTrace();
                }

            }
        }
    }
    static void migrateTestAndDependency(Revision rfc, List<Revision> needToTestMigrateRevisionList,
                                         String testCaseString) {
        migrator.equipRfcWithChangeInfo(rfc);
        reducer.reduceTestCases(rfc, testCaseString);
        needToTestMigrateRevisionList.forEach(revision -> {
            migrator.migrateTestFromTo_0(rfc, revision);
        });
    }

    static void ddj(String regressionID, File projectDir) throws Exception{
        //AST + ddmin
        runCCA(projectDir, regressionID, "ddmin", "bic",true);
        Thread.sleep(3000);
        //AST + prodd
        runCCA(projectDir, regressionID, "prodd", "bic",true);
        Thread.sleep(3000);
        //AST + ddmin - decomposed
        runCCA(projectDir, regressionID, "ddmin", "bic",false);
        Thread.sleep(3000);
        //AST + prodd - decomposed
        runCCA(projectDir, regressionID, "prodd", "bic",false);
        Thread.sleep(3000);

        SourceManager.cleanCache();
        Thread.sleep(3000);

    }

    public static void runCCA(File projectDir, String regressionID, String tool, String version, boolean isDecomposed) throws Exception {
        cleanCacheCCA(projectDir);
        String projectName = projectDir.getName();
        String godName = "";
        String badName = "";
        String command;
        if(version.equals("bic")){
            godName = regressionID + "_bic";
            badName = regressionID + "_work";
        }else if(version.equals("bfc")){
            godName = regressionID + "_bfc";
            badName = regressionID + "_buggy";
        }
        System.out.println("start " +version + " ddj " + tool);
        if(isDecomposed){
            command = "./cca.py ddjava --include src/main/java cache_projects" + File.separator + projectName.replace("/", "_") + " "
                    + godName +" "+ badName + " -a " + tool;
        }else {
            command = "./cca.py ddjava --include src/main/java cache_projects" + File.separator + projectName.replace("/", "_") + " "
                    + godName + " " + badName + " -a " + tool + " --noresolve --noref --nochg";
        }
        System.out.println(command);
        executor.exec(command);
        String ddjResult = getDDJResult(projectDir, regressionID + "_" + version + "_ddj_" + tool + (isDecomposed ? "" : "_nodecomposed"));
        //backUP(projectDir, regressionID + "_" + version + "_ddj_" + tool + (isDecomposed ? "" : "_nodecomposed");
    }

    //将data文件放到新的位置
    public static String getDDJResult(File projectDir, String newName) throws Exception {
        String result = "";
        File[] child = projectDir.listFiles();
        for (File file : child) {
            if (file.getName().contains("__CCA__")) {
                File logFile = new File(file, "data.log");
                if (logFile.exists()) {
                    System.out.println("ddj result file :" + logFile);
                    SourceManager.saveData(logFile, newName);
                }
            }
        }
        return result;
    }


    //删除生成的CCA文件夹
    public static void cleanCacheCCA(File projectDir) throws IOException {
        File[] child = projectDir.listFiles();
        for (File file : child) {
            if (file.getName().contains("__CCA__")) {
                FileUtils.forceDelete(file);
                if (file.exists()){
                    FileUtils.deleteDirectory(file);
                }
            }
        }
    }

}
