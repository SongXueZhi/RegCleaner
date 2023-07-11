package start.compareCC;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author lsn
 * @date 2023/4/18 7:21 PM
 */
public class FileLineRangeMap {
    private Map<String, LineRangeList> fileLineRangeMap;

    private int hunkNum ;

    public int getHunkNum() {
        return hunkNum;
    }

    public void setHunkNum(int hunkNum) {
        this.hunkNum = hunkNum;
    }

    public FileLineRangeMap() {
        fileLineRangeMap = new HashMap<>();
        hunkNum = 0;
    }

    public void addLineRange(String fileName, int start, int end) {
        LineRange range = new LineRange(start, end);
        if (fileLineRangeMap.containsKey(fileName)) {
            LineRangeList rangeList = fileLineRangeMap.get(fileName);
            rangeList.addLineRange(range);
        } else {
            LineRangeList rangeList = new LineRangeList();
            rangeList.addLineRange(range);
            fileLineRangeMap.put(fileName, rangeList);
        }
    }

    public int getFileCount() {
        return fileLineRangeMap.size();
    }

    public int getFileLineCount(String fileName) {
        if (fileLineRangeMap.containsKey(fileName)) {
            LineRangeList lineRanges = fileLineRangeMap.get(fileName);
            int totalLineCount = 0;
            for (LineRange lineRange : lineRanges.getLineRanges()) {
                totalLineCount += lineRange.getEndLine() - lineRange.getStartLine() + 1;
            }
            return totalLineCount;
        }
        return 0;
    }

    public int getTotalLineCount() {
        int thisTotalLineCount = 0;
        for (String fileName : fileLineRangeMap.keySet()) {
            thisTotalLineCount += getFileLineCount(fileName);
        }
        return thisTotalLineCount;
    }

    public boolean contains(FileLineRangeMap other) {
        // 对比两个 FileLineRangeMap 中的每个文件名
        for (String fileName : other.fileLineRangeMap.keySet()) {
            // 如果当前 FileLineRangeMap 不包含 other 中的文件名，则直接返回 false
            if (!fileLineRangeMap.containsKey(fileName)) {
                return false;
            }

            // 获取当前 FileLineRangeMap 中的 LineRangeList 和 other 中的 LineRangeList
            LineRangeList thisLineRangeList = fileLineRangeMap.get(fileName);
            LineRangeList otherLineRangeList = other.fileLineRangeMap.get(fileName);

            // 对比 LineRangeList 是否相等
            if (!thisLineRangeList.equals(otherLineRangeList)) {
                return false;
            }
        }

        // 如果所有的文件名和 LineRangeList 都相等，则说明两个 FileLineRangeMap 是包含关系
        return true;
    }

    public int compareSize(FileLineRangeMap other) {
        return Integer.compare(getTotalLineCount(), other.getTotalLineCount());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (String fileName : fileLineRangeMap.keySet()) {
            LineRangeList rangeList = fileLineRangeMap.get(fileName);
            sb.append(fileName).append(": ").append(rangeList.toString()).append("\n");
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof FileLineRangeMap)) {
            return false;
        }

        FileLineRangeMap other = (FileLineRangeMap) obj;

        if (fileLineRangeMap.size() != other.fileLineRangeMap.size()) {
            return false;
        }

        for (String fileName : fileLineRangeMap.keySet()) {
            if (!other.fileLineRangeMap.containsKey(fileName)) {
                return false;
            }

            LineRangeList thisLineRangeList = fileLineRangeMap.get(fileName);
            LineRangeList otherLineRangeList = other.fileLineRangeMap.get(fileName);

            if (!thisLineRangeList.equals(otherLineRangeList)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(fileLineRangeMap);
    }

    public static void main(String[] args) {
        String input = "*** src/main/java/com/fasterxml/jackson/core/json/UTF8StreamJsonParser.java\n" +
                "  [1*] HUNK (7) DEL IfStatement [305:8-307:8 src/main/java/com/fasterxml/jackson/core/json/UTF8StreamJsonParser.java]-[297:4-306:4 src/main/java/com/fasterxml/jackson/core/json/UTF8StreamJsonParser.java]\n" +
                "*** src/main/java/com/fasterxml/jackson/core/base/ParserMinimalBase.java\n" +
                "  [7*] HUNK (7) DEL IfStatement [403:8-405:8 src/main/java/com/fasterxml/jackson/core/base/ParserMinimalBase.java]-[396:75-403:4 src/main/java/com/fasterxml/jackson/core/base/ParserMinimalBase.java]";

        FileLineRangeMap fileLineRangeMap = new FileLineRangeMap();

        String[] lines = input.split("\n");
        for (String line : lines) {
            if(line.contains("*] HUNK")){
                String[] parts = line.trim().split("\\[|\\]|\\s|\\-|:|\\ ");
                String fileName = parts[12];
                int start = Integer.parseInt(parts[8]);
                int end = Integer.parseInt(parts[10]);
                fileLineRangeMap.addLineRange(fileName, start, end);
            }

        }

        System.out.println(fileLineRangeMap.toString());
    }

}
