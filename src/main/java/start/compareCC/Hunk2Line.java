package start.compareCC;

import com.opencsv.CSVWriter;

import java.io.*;

/**
 * @author lsn
 * @date 2023/6/19 8:19 PM
 */
public class Hunk2Line {
    static String dataPath = "/Users/lsn/ddj_space/data_save";

    public static void main(String[] args) throws IOException {

        CSVWriter writer = new CSVWriter(new FileWriter("data_bic_old.csv"));
        String[] header = {"ID", "version", "tool", "is_decomposed", "diffFile", "diffHunk", "diffLine"};
        writer.writeNext(header);

        File directory = new File(dataPath);
        File[] files = directory.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    if(!file.getName().contains("799_bic_ddj_prodd") ){
                        continue;
                    }
//                    if(file.getName().contains(".DS_Store") || file.getName().contains("reldd") ){
//                        continue;
//                    }
                    try {
                        //System.out.println(file.getName());
                        String id = file.getName().split("_")[0];
                        String version = file.getName().split("_")[1];
                        String tool = file.getName().split("_")[3].substring(0,5);
                        String is_decomposed = file.getName().split("_").length > 4 ? "false" : "true";

                        FileLineRangeMap lineRangeMap = getDiffHunk(dataPath + File.separator + file.getName());

                        writer.writeNext(new String[]{id, version, tool, is_decomposed, String.valueOf(lineRangeMap.getFileCount()),
                                String.valueOf(lineRangeMap.getHunkNum()), String.valueOf(lineRangeMap.getTotalLineCount()) });

                    }catch (Exception e){
                        System.out.println("error: " + file.getName());
                        e.printStackTrace();
                    }
                }
            }
        }
        writer.close();
    }

    public static FileLineRangeMap getCcHunk(String dataFilePath) {
        FileLineRangeMap fileLineRangeMap = new FileLineRangeMap();
        try {
            // 创建 FileReader 对象读取文件
            FileReader fileReader = new FileReader(dataFilePath);
            // 创建 BufferedReader 对象以便按行读取文件
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            // 按行读取文件内容
            String fileName = "";
            while ((line = bufferedReader.readLine()) != null) {
                if(line.contains("***")){
                    fileName = line.replace("***", "").trim();
                }
                if(line.contains("*] HUNK")){
                    int[] lineRange = splitLineRange(line.split("HUNK ")[1]);
                    if(lineRange == null){
                        continue;
                    }
                    fileLineRangeMap.setHunkNum(fileLineRangeMap.getHunkNum() + 1);
                    fileLineRangeMap.addLineRange(fileName, lineRange[0], lineRange[1]);
                }
            }
            bufferedReader.close(); // 关闭 BufferedReader
        }
        catch (FileNotFoundException e) {
            System.out.println("文件不存在：" + dataFilePath); // 输出文件不存在提示
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return fileLineRangeMap;
    }


    public static FileLineRangeMap getDiffHunk(String diffFilePath) {
        FileLineRangeMap fileLineRangeMap = new FileLineRangeMap();
        try {
            // 创建 FileReader 对象读取文件
            FileReader fileReader = new FileReader(diffFilePath);
            // 创建 BufferedReader 对象以便按行读取文件
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            // 按行读取文件内容
            while ((line = bufferedReader.readLine()) != null) {
                if(line.startsWith(" HUNK")){
                    String fileName = splitFileName(line.split("HUNK ")[1]);
                    int[] lineRange = splitLineRange(line.split("HUNK ")[1]);
                    if(fileName == null || fileName.isEmpty() || fileName.equals("") || lineRange == null){
                        continue;
                    }
                    fileLineRangeMap.setHunkNum(fileLineRangeMap.getHunkNum() + 1);
                    fileLineRangeMap.addLineRange(fileName, lineRange[0], lineRange[1]);
                }
            }
            bufferedReader.close(); // 关闭 BufferedReader
        }
        catch (FileNotFoundException e) {
            System.out.println("文件不存在：" + diffFilePath); // 输出文件不存在提示
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return fileLineRangeMap;
    }

    public static int[] splitLineRange(String line){
        // (7) DEL IfStatement [305:8-307:8 src/main/java/com/fasterxml/jackson/core/json/UTF8StreamJsonParser.java]-[297:4-306:4 src/main/java/com/fasterxml/jackson/core/json/UTF8StreamJsonParser.java]
        String[] lineRangeArray = line.trim().split(" ");
        String lineRange = "";
        if(lineRangeArray[2].contains("Auxfile")){
            return null;
        }
        if(lineRangeArray[1].equals("DEL")){
            //(36) DEL File [src/main/java/org/apache/commons/codec/binary/CharSequenceUtils.java]-[None]
            if(lineRangeArray[2].equals("File")){
                //None
                return new int[]{0, 99999};
            }
            lineRange = lineRangeArray[3].replace("[","");
        }
        else if(lineRangeArray[1].equals("INS")){
            // (0) INS File [None]-[src/main/java/org/dbtools/query/shared/QueryCompareType.java]
            if(lineRangeArray[2].equals("File")){
                //None
                return new int[]{0, 99999};
            }
            lineRange = lineRangeArray[4].split("\\[")[1];
        }
        else if( lineRangeArray[1].equals("MOV")){
            if(lineRangeArray[2].equals("File")){
                //None
                return new int[]{0, 99999};
            }
            lineRange = lineRangeArray[4].split("\\[")[1];
        }
        else if(lineRangeArray[1].equals("REL")) {
            //297:4-306:4
            lineRange = lineRangeArray[4].split("\\[")[1];
        }
        String start = lineRange.split("-")[0].split(":")[0];
        String end = lineRange.split("-")[1].split(":")[0];
        return new int[]{Integer.parseInt(start), Integer.parseInt(end)};
    }

    public static String splitFileName(String line){
        // (7) DEL IfStatement [305:8-307:8 src/main/java/com/fasterxml/jackson/core/json/UTF8StreamJsonParser.java]-[297:4-306:4 src/main/java/com/fasterxml/jackson/core/json/UTF8StreamJsonParser.java]
        String[] lineRangeArray = line.trim().split(" ");
        String fileName = "";
        if(lineRangeArray[2].contains("Auxfile")){
            return null;
        }
        if(lineRangeArray[1].equals("DEL")){
            //(36) DEL File [src/main/java/org/apache/commons/codec/binary/CharSequenceUtils.java]-[None]
            if(lineRangeArray[2].equals("File")){
                fileName = lineRangeArray[3].split("-")[0].replace("[", "").replace("]", "");
            }else{
                //305:8-307:8
                fileName = lineRangeArray[4].split("\\]")[0];
            }
        }else if(lineRangeArray[1].equals("MOV") && lineRangeArray[2].equals("File")){
            fileName = lineRangeArray[3].split("\\]")[0].replace("[", "");
        }else if(lineRangeArray[1].equals("INS") && lineRangeArray[2].equals("File")){
            fileName = lineRangeArray[3].split("\\]")[1].replace("[", "").replace("-", "");
        } else if(lineRangeArray[1].equals("INS")  ) {
            //297:4-306:4
            fileName = lineRangeArray[5].split("\\]")[0];
        }else if(lineRangeArray[1].equals("REL")){
            fileName = lineRangeArray[5].split("\\]")[0];
        }else if(lineRangeArray[1].equals("MOV") ){
            fileName = lineRangeArray[5].split("\\]")[0];
        }
        return fileName;
    }
}
