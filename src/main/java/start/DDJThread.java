package start;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lsn
 * @date 2023/3/22 10:46 AM
 */
public class DDJThread extends Thread {
    private String tool;
    private String version;
    private boolean isDecomposed;

    public DDJThread( String tool, String version, boolean isDecomposed) {
        this.tool = tool;
        this.version = version;
        this.isDecomposed = isDecomposed;
    }

    public void run() {
        //String sql = "select * from regressions_all where id = 20 or id = 33";
        String sql = "select * from regressions_all where is_clean=1 and is_dirty=0";
        List<Regression> regressions  = MysqlManager.selectRegressionstoList(sql);
        List<String> uuid = getRegressions();
        regressions.removeIf(regression -> uuid.contains(regression.getId()));
        for (int i = 0; i < regressions.size(); i++) {
            Regression regression = regressions.get(i);
            String projectName = regression.getProject_full_name();
            System.out.println(projectName + " " + tool +  " " + version + " " + isDecomposed);
            File projectDir = SourceManager.getProjectDir(projectName);
            System.out.println(regression.getId());
            try {
                DDJ ddj = new DDJ(projectDir, regression, tool, version, isDecomposed, projectName);
                ddj.checkout();
                ddj.runCCA();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //读取data目录下的文件名得到已经运行的regressions
    public List<String> getRegressions(){
        List<String> uuid = new ArrayList<>();
        List<String> dataName = SourceManager.getDataName();
        for(String name : dataName){
            String[] split = name.split("_");
            if(split.length == (isDecomposed ? 4: 5) && split[1].equals(version) && split[3].equals(tool)){
                uuid.add(split[0]);
            }
        }
        return uuid;
    }

}
