package start.DDJ;


import start.MysqlManager;
import start.Regression;
import start.SourceManager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lsn
 * @date 2023/3/22 10:46 AM
 */
public class DDJThreadForNew extends Thread {
    private String tool;
    private String version;
    private boolean isDecomposed;
    private int startId;
    private int endId;
    private String model;

    public DDJThreadForNew(String tool, String version, boolean isDecomposed, int startId, int endId, String model) {
        this.tool = tool;
        this.version = version;
        this.isDecomposed = isDecomposed;
        this.startId = startId;
        this.endId = endId;
        this.model = model;
    }

    public void run() {
        String sql = "select * from regressions_all where is_clean=1 and is_dirty=0 and id >= " + startId + " and id <= " + endId;
        List<Regression> regressions  = MysqlManager.selectRegressionstoList(sql);
        System.out.println("regression size: " + regressions.size());
        List<String> uuid = getRegressions();
        regressions.removeIf(regression -> uuid.contains(regression.getId()));
        System.out.println("run regression size: " + regressions.size());
        for (int i = 0; i < regressions.size(); i++) {
            Regression regression = regressions.get(i);
            String projectName = regression.getProject_full_name();
            System.out.println(projectName + " " + tool +  " " + version + " " + isDecomposed + " " + model);
            File projectDir = SourceManager.getProjectDir(projectName);
            System.out.println(regression.getId());
            try {
                DDJForNew ddj = new DDJForNew(projectDir, regression, tool, version, isDecomposed, projectName, model);
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
            if(split.length == (isDecomposed ? 5: 6) && split[1].equals(version) && split[3].equals(tool) && split[5].equals(model)){
                uuid.add(split[0]);
            }
        }
        return uuid;
    }

}