package start;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

/**
 * @Author: sxz
 * @Date: 2022/12/26/20:32
 * @Description:
 */
public class SourceManager {
    private final static String metaProjectsDirPath = Main.workSpacePath+ File.separator + "meta_projects";
    private final static String cacheProjectsDirPath = Main.workSpacePath+ File.separator+ "cache_projects";

    public static File checkout(String regressionID,Revision revision, File projectFile, String projectFullName) {
        //copy source code from meta project dir
        String projectDirName = projectFullName.replace("/", "_");
        File projectCacheDir = new File(cacheProjectsDirPath + File.separator, projectDirName);
        if (projectCacheDir.exists() && !projectCacheDir.isDirectory()) {
            projectCacheDir.delete();
        }
        projectCacheDir.mkdirs();

        File revisionDir = new File(projectCacheDir,regressionID+"_"+revision.getName());
        try {
            if (revisionDir.exists()) {
                FileUtils.forceDelete(revisionDir);
            }
            FileUtils.copyDirectoryToDirectory(projectFile, projectCacheDir);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return null;
        }
        new File(projectCacheDir, projectDirName).renameTo(revisionDir);
        //git checkout
        if (GitUtils.checkout(revision.getCommitID(), revisionDir)) {
            return revisionDir;
        }
        return null;
    }

    //download source project
    public static File getProjectDir(String projectDirName) {
        String projectFullName = projectDirName.replace("_", "/");
        checkRequiredDir();
        File projectFile = new File(metaProjectsDirPath + File.separator + projectDirName);
        if (projectFile.exists()) {
            return projectFile;
        } else {
            try {
                GitUtils.clone(projectFile, "https://github.com/" + projectFullName + ".git");
                return projectFile;
            } catch (Exception exception) {
                System.out.println(exception.getMessage());
            }
        }
        return null;
    }

    private static void checkRequiredDir() {
        File metaProjectsDir = new File(metaProjectsDirPath);

        if (metaProjectsDir.exists()) {
            if (!metaProjectsDir.isDirectory()) {
                metaProjectsDir.delete();
                metaProjectsDir.mkdirs();
            }
        } else {
            metaProjectsDir.mkdirs();
        }

        File cacheProjectsDir = new File(cacheProjectsDirPath);
        if (cacheProjectsDir.exists()) {
            if (!cacheProjectsDir.isDirectory()) {
                cacheProjectsDir.delete();
                cacheProjectsDir.mkdirs();
            }
        } else {
            cacheProjectsDir.mkdirs();
        }
    }
}
