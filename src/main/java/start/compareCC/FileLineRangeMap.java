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

    private int groupNum ;

    public int getHunkNum() {
        return hunkNum;
    }

    public void setHunkNum(int hunkNum) {
        this.hunkNum = hunkNum;
    }

    public Map<String, LineRangeList> getFileLineRangeMap() {
        return fileLineRangeMap;
    }

    public int getGroupNum() {
        return groupNum;
    }

    public void setGroupNum(int groupNum) {
        this.groupNum = groupNum;
    }

    public FileLineRangeMap() {
        fileLineRangeMap = new HashMap<>();
        hunkNum = 0;
        groupNum = 0;
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
        String input = "HUNK (0) REL Parameter [144:41-144:51 src/main/java/org/springframework/hateoas/TemplateVariable.java]-[121:41-121:55 src/main/java/org/springframework/hateoas/TemplateVariable.java]\n" +
                " HUNK (0) REL Name [145:30-145:33 src/main/java/org/springframework/hateoas/TemplateVariable.java]-[122:30-122:37 src/main/java/org/springframework/hateoas/TemplateVariable.java]\n" +
                " HUNK (0) REL Parameters [502:14-502:60 src/main/java/org/springframework/hateoas/TemplateVariable.java]-[253:14-253:43 src/main/java/org/springframework/hateoas/TemplateVariable.java]\n" +
                " HUNK (1) DEL Parameter [502:27-502:41 src/main/java/org/springframework/hateoas/TemplateVariable.java]-[253:14-253:43 src/main/java/org/springframework/hateoas/TemplateVariable.java]\n" +
                " HUNK (0) REL ConstructorDeclaration [502:2-507:2 src/main/java/org/springframework/hateoas/TemplateVariable.java]-[253:2-257:2 src/main/java/org/springframework/hateoas/TemplateVariable.java]\n" +
                " HUNK (0) REL ConstructorBody [502:62-507:2 src/main/java/org/springframework/hateoas/TemplateVariable.java]-[253:45-257:2 src/main/java/org/springframework/hateoas/TemplateVariable.java]\n" +
                "[1]\n" +
                " HUNK (14) DEL MethodDeclaration [148:1-150:1 src/main/java/org/springframework/hateoas/TemplateVariable.java]-[34:60-295:0 src/main/java/org/springframework/hateoas/TemplateVariable.java]\n" +
                "[2]\n" +
                " HUNK (0) DEL MarkerAnnotation [160:1-160:11 src/main/java/org/springframework/hateoas/TemplateVariable.java]-[132:1-132:13 src/main/java/org/springframework/hateoas/TemplateVariable.java]\n" +
                "[3]\n" +
                " HUNK (17) DEL MethodDeclaration [171:1-173:1 src/main/java/org/springframework/hateoas/TemplateVariable.java]-[34:60-295:0 src/main/java/org/springframework/hateoas/TemplateVariable.java]\n" +
                "[4]\n" +
                " HUNK (17) DEL MethodDeclaration [181:1-183:1 src/main/java/org/springframework/hateoas/TemplateVariable.java]-[34:60-295:0 src/main/java/org/springframework/hateoas/TemplateVariable.java]\n" +
                "[5]\n" +
                " HUNK (16) DEL MethodDeclaration [185:1-187:1 src/main/java/org/springframework/hateoas/TemplateVariable.java]-[34:60-295:0 src/main/java/org/springframework/hateoas/TemplateVariable.java]\n" +
                "[6]\n" +
                " HUNK (9) DEL MethodDeclaration [195:1-197:1 src/main/java/org/springframework/hateoas/TemplateVariable.java]-[34:60-295:0 src/main/java/org/springframework/hateoas/TemplateVariable.java]\n" +
                "[7]\n" +
                " HUNK (9) DEL MethodDeclaration [205:1-207:1 src/main/java/org/springframework/hateoas/TemplateVariable.java]-[34:60-295:0 src/main/java/org/springframework/hateoas/TemplateVariable.java]\n" +
                "[8]\n" +
                " HUNK (7) DEL MethodDeclaration [209:1-211:1 src/main/java/org/springframework/hateoas/TemplateVariable.java]-[34:60-295:0 src/main/java/org/springframework/hateoas/TemplateVariable.java]\n" +
                "[9]\n" +
                " HUNK (0) DEL SingleTypeImportDeclaration [21:0-21:40 src/main/java/org/springframework/hateoas/TemplateVariable.java]-[18:0-26:43 src/main/java/org/springframework/hateoas/TemplateVariable.java]\n" +
                "[10]\n" +
                " HUNK (1) DEL Modifiers [229:1-229:11 src/main/java/org/springframework/hateoas/TemplateVariable.java]-[151:1-153:1 src/main/java/org/springframework/hateoas/TemplateVariable.java]\n" +
                "[11]\n" +
                " HUNK (0) DEL SingleTypeImportDeclaration [23:0-23:27 src/main/java/org/springframework/hateoas/TemplateVariable.java]-[18:0-26:43 src/main/java/org/springframework/hateoas/TemplateVariable.java]\n" +
                "[12]\n" +
                " HUNK (0) DEL SingleTypeImportDeclaration [25:0-25:20 src/main/java/org/springframework/hateoas/TemplateVariable.java]-[18:0-26:43 src/main/java/org/springframework/hateoas/TemplateVariable.java]\n" +
                "[13]\n" +
                " HUNK (14) DEL MethodDeclaration [273:1-275:1 src/main/java/org/springframework/hateoas/TemplateVariable.java]-[34:60-295:0 src/main/java/org/springframework/hateoas/TemplateVariable.java]\n" +
                "[14]\n" +
                " HUNK (0) DEL SingleTypeImportDeclaration [27:0-27:34 src/main/java/org/springframework/hateoas/TemplateVariable.java]-[18:0-26:43 src/main/java/org/springframework/hateoas/TemplateVariable.java]\n" +
                "[15]\n" +
                " HUNK (5) INS TypeMethodInvocation [282:26-284:1 src/main/java/org/springframework/hateoas/TemplateVariable.java]-[201:16-201:61 src/main/java/org/springframework/hateoas/TemplateVariable.java]\n" +
                " HUNK (2) INS LocalVariableDeclarationStatement [282:26-284:1 src/main/java/org/springframework/hateoas/TemplateVariable.java]-[201:2-201:62 src/main/java/org/springframework/hateoas/TemplateVariable.java]\n" +
                " HUNK (0) INS Argument|FieldAccess [282:26-284:1 src/main/java/org/springframework/hateoas/TemplateVariable.java]-[201:57-201:60 src/main/java/org/springframework/hateoas/TemplateVariable.java]\n" +
                "[16]\n" +
                " HUNK (0) DEL Arguments [283:104-283:105 src/main/java/org/springframework/hateoas/TemplateVariable.java]-[202:90-202:93 src/main/java/org/springframework/hateoas/TemplateVariable.java]\n" +
                " HUNK (0) REL Name [283:96-283:105 src/main/java/org/springframework/hateoas/TemplateVariable.java]-[202:90-202:93 src/main/java/org/springframework/hateoas/TemplateVariable.java]\n" +
                "[17]\n" +
                " HUNK (0) REL Argument|Name [283:69-283:78 src/main/java/org/springframework/hateoas/TemplateVariable.java]-[202:69-202:72 src/main/java/org/springframework/hateoas/TemplateVariable.java]\n" +
                " HUNK (0) DEL Arguments [283:77-283:78 src/main/java/org/springframework/hateoas/TemplateVariable.java]-[202:69-202:72 src/main/java/org/springframework/hateoas/TemplateVariable.java]\n" +
                "[18]\n" +
                " HUNK (6) DEL MethodDeclaration [286:1-288:1 src/main/java/org/springframework/hateoas/TemplateVariable.java]-[34:60-295:0 src/main/java/org/springframework/hateoas/TemplateVariable.java]\n" +
                " HUNK (1) DEL SimpleMethodInvocation [287:50-287:58 src/main/java/org/springframework/hateoas/TemplateVariable.java]-[34:60-295:0 src/main/java/org/springframework/hateoas/TemplateVariable.java]\n" +
                " HUNK (5) DEL TypeMethodInvocation [287:9-287:59 src/main/java/org/springframework/hateoas/TemplateVariable.java]-[34:60-295:0 src/main/java/org/springframework/hateoas/TemplateVariable.java]\n" +
                "[19]\n" +
                " HUNK (0) DEL SingleTypeImportDeclaration [28:0-28:37 src/main/java/org/springframework/hateoas/TemplateVariable.java]-[18:0-26:43 src/main/java/org/springframework/hateoas/TemplateVariable.java]\n" +
                "[20]\n" +
                " HUNK (24) DEL MethodDeclaration [290:1-295:1 src/main/java/org/springframework/hateoas/TemplateVariable.java]-[34:60-295:0 src/main/java/org/springframework/hateoas/TemplateVariable.java]\n" +
                "[21]\n" +
                " HUNK (0) DEL SingleTypeImportDeclaration [30:0-30:40 src/main/java/org/springframework/hateoas/TemplateVariable.java]-[18:0-26:43 src/main/java/org/springframework/hateoas/TemplateVariable.java]\n" +
                "[22]\n" +
                " HUNK (30) DEL MethodDeclaration [318:1-330:1 src/main/java/org/springframework/hateoas/TemplateVariable.java]-[34:60-295:0 src/main/java/org/springframework/hateoas/TemplateVariable.java]\n" +
                " HUNK (0) DEL ReferenceType [41:61-41:82 src/main/java/org/springframework/hateoas/TemplateVariable.java]-[34:36-34:58 src/main/java/org/springframework/hateoas/TemplateVariable.java]\n" +
                "[23]\n" +
                " HUNK (17) DEL MethodDeclaration [332:1-335:1 src/main/java/org/springframework/hateoas/TemplateVariable.java]-[34:60-295:0 src/main/java/org/springframework/hateoas/TemplateVariable.java]\n" +
                "[24]\n" +
                " HUNK (132) DEL MethodDeclaration [337:1-370:1 src/main/java/org/springframework/hateoas/TemplateVariable.java]-[34:60-295:0 src/main/java/org/springframework/hateoas/TemplateVariable.java]\n" +
                "[25]\n" +
                " HUNK (0) DEL SingleTypeImportDeclaration [33:0-33:44 src/main/java/org/springframework/hateoas/TemplateVariable.java]-[18:0-26:43 src/main/java/org/springframework/hateoas/TemplateVariable.java]\n" +
                "[26]\n" +
                " HUNK (52) DEL MethodDeclaration [372:1-389:1 src/main/java/org/springframework/hateoas/TemplateVariable.java]-[34:60-295:0 src/main/java/org/springframework/hateoas/TemplateVariable.java]\n" +
                "[27]\n" +
                " HUNK (47) DEL MethodDeclaration [391:1-405:1 src/main/java/org/springframework/hateoas/TemplateVariable.java]-[34:60-295:0 src/main/java/org/springframework/hateoas/TemplateVariable.java]\n" +
                "[28]\n" +
                " HUNK (65) DEL MethodDeclaration [407:1-437:1 src/main/java/org/springframework/hateoas/TemplateVariable.java]-[34:60-295:0 src/main/java/org/springframework/hateoas/TemplateVariable.java]\n" +
                "[29]\n" +
                " HUNK (1) INS ConstructorDeclaration [41:84-586:0 src/main/java/org/springframework/hateoas/TemplateVariable.java]-[59:1-68:1 src/main/java/org/springframework/hateoas/TemplateVariable.java]\n" +
                " HUNK (5) DEL FieldDeclaration [48:1-48:38 src/main/java/org/springframework/hateoas/TemplateVariable.java]-[34:60-295:0 src/main/java/org/springframework/hateoas/TemplateVariable.java]\n" +
                " HUNK (5) DEL FieldDeclaration [49:1-49:24 src/main/java/org/springframework/hateoas/TemplateVariable.java]-[34:60-295:0 src/main/java/org/springframework/hateoas/TemplateVariable.java]\n" +
                " HUNK (1) DEL ConstructorDeclaration [61:1-63:1 src/main/java/org/springframework/hateoas/TemplateVariable.java]-[34:60-295:0 src/main/java/org/springframework/hateoas/TemplateVariable.java]\n" +
                " HUNK (7) DEL ThisInvocation [62:2-62:57 src/main/java/org/springframework/hateoas/TemplateVariable.java]-[34:60-295:0 src/main/java/org/springframework/hateoas/TemplateVariable.java]\n" +
                " HUNK (24) DEL ConstructorDeclaration [78:1-91:1 src/main/java/org/springframework/hateoas/TemplateVariable.java]-[34:60-295:0 src/main/java/org/springframework/hateoas/TemplateVariable.java]\n" +
                "[30]\n" +
                " HUNK (1) DEL Modifiers [440:23-440:31 src/main/java/org/springframework/hateoas/TemplateVariable.java]-[218:23-218:30 src/main/java/org/springframework/hateoas/TemplateVariable.java]\n" +
                "[31]\n" +
                " HUNK (0) DEL Block [442:17-444:2 src/main/java/org/springframework/hateoas/TemplateVariable.java]-[220:2-221:14 src/main/java/org/springframework/hateoas/TemplateVariable.java]\n" +
                "[32]\n" +
                " HUNK (0) DEL Block [445:47-447:2 src/main/java/org/springframework/hateoas/TemplateVariable.java]-[222:2-223:15 src/main/java/org/springframework/hateoas/TemplateVariable.java]\n" +
                "[33]\n" +
                " HUNK (6) DEL And [450:9-452:30 src/main/java/org/springframework/hateoas/TemplateVariable.java]-[225:9-225:70 src/main/java/org/springframework/hateoas/TemplateVariable.java]\n" +
                " HUNK (4) DEL Eq [453:7-453:42 src/main/java/org/springframework/hateoas/TemplateVariable.java]-[225:9-225:70 src/main/java/org/springframework/hateoas/TemplateVariable.java]\n" +
                "[34]\n" +
                " HUNK (4) INS FieldDeclaration [467:26-573:1 src/main/java/org/springframework/hateoas/TemplateVariable.java]-[250:2-250:26 src/main/java/org/springframework/hateoas/TemplateVariable.java]\n" +
                " HUNK (5) DEL FieldDeclaration [499:2-499:36 src/main/java/org/springframework/hateoas/TemplateVariable.java]-[239:26-294:1 src/main/java/org/springframework/hateoas/TemplateVariable.java]\n" +
                " HUNK (3) DEL AssignStatement [505:3-505:27 src/main/java/org/springframework/hateoas/TemplateVariable.java]-[253:45-257:2 src/main/java/org/springframework/hateoas/TemplateVariable.java]\n" +
                "[35]\n" +
                " HUNK (4) DEL EnumConstant [469:2-469:23 src/main/java/org/springframework/hateoas/TemplateVariable.java]-[239:26-294:1 src/main/java/org/springframework/hateoas/TemplateVariable.java]\n" +
                "[36]\n" +
                " HUNK (1) DEL Annotations [474:2-474:12 src/main/java/org/springframework/hateoas/TemplateVariable.java]-[241:2-241:25 src/main/java/org/springframework/hateoas/TemplateVariable.java]\n" +
                "[37]\n" +
                " HUNK (0) REL Argument|False [475:20-475:22 src/main/java/org/springframework/hateoas/TemplateVariable.java]-[241:20-241:24 src/main/java/org/springframework/hateoas/TemplateVariable.java]\n" +
                "[38]\n" +
                " HUNK (0) DEL Argument|True [475:25-475:28 src/main/java/org/springframework/hateoas/TemplateVariable.java]-[241:15-241:25 src/main/java/org/springframework/hateoas/TemplateVariable.java]\n" +
                "[39]\n" +
                " HUNK (4) DEL EnumConstant [476:2-476:32 src/main/java/org/springframework/hateoas/TemplateVariable.java]-[239:26-294:1 src/main/java/org/springframework/hateoas/TemplateVariable.java]\n" +
                "[40]\n" +
                " HUNK (4) DEL EnumConstant [477:2-477:20 src/main/java/org/springframework/hateoas/TemplateVariable.java]-[239:26-294:1 src/main/java/org/springframework/hateoas/TemplateVariable.java]\n" +
                "[41]\n" +
                " HUNK (0) DEL Argument|StringLiteral [478:21-478:23 src/main/java/org/springframework/hateoas/TemplateVariable.java]-[242:15-242:25 src/main/java/org/springframework/hateoas/TemplateVariable.java]\n" +
                "[42]\n" +
                " HUNK (0) DEL Argument|StringLiteral [479:26-479:28 src/main/java/org/springframework/hateoas/TemplateVariable.java]-[243:25-243:35 src/main/java/org/springframework/hateoas/TemplateVariable.java]\n" +
                "[43]\n" +
                " HUNK (4) DEL EnumConstant [481:2-481:29 src/main/java/org/springframework/hateoas/TemplateVariable.java]-[239:26-294:1 src/main/java/org/springframework/hateoas/TemplateVariable.java]\n" +
                "[44]\n" +
                " HUNK (1) DEL Annotations [486:2-486:12 src/main/java/org/springframework/hateoas/TemplateVariable.java]-[244:2-244:19 src/main/java/org/springframework/hateoas/TemplateVariable.java]\n" +
                "[45]\n" +
                " HUNK (0) DEL Argument|StringLiteral [487:10-487:12 src/main/java/org/springframework/hateoas/TemplateVariable.java]-[244:9-244:19 src/main/java/org/springframework/hateoas/TemplateVariable.java]\n" +
                "[46]\n" +
                " HUNK (4) DEL EnumConstant [488:2-488:37 src/main/java/org/springframework/hateoas/TemplateVariable.java]-[239:26-294:1 src/main/java/org/springframework/hateoas/TemplateVariable.java]\n" +
                "[47]\n" +
                " HUNK (0) DEL Argument|StringLiteral [489:16-489:18 src/main/java/org/springframework/hateoas/TemplateVariable.java]-[245:10-245:20 src/main/java/org/springframework/hateoas/TemplateVariable.java]\n" +
                "[48]\n" +
                " HUNK (0) DEL Argument|StringLiteral [494:23-494:24 src/main/java/org/springframework/hateoas/TemplateVariable.java]-[246:17-246:27 src/main/java/org/springframework/hateoas/TemplateVariable.java]\n" +
                "[49]\n" +
                " HUNK (6) DEL FieldDeclaration [497:2-497:45 src/main/java/org/springframework/hateoas/TemplateVariable.java]-[239:26-294:1 src/main/java/org/springframework/hateoas/TemplateVariable.java]\n" +
                "[50]\n" +
                " HUNK (39) DEL MethodDeclaration [509:2-524:2 src/main/java/org/springframework/hateoas/TemplateVariable.java]-[239:26-294:1 src/main/java/org/springframework/hateoas/TemplateVariable.java]\n" +
                "[51]\n" +
                " HUNK (35) DEL MethodDeclaration [535:2-545:2 src/main/java/org/springframework/hateoas/TemplateVariable.java]-[239:26-294:1 src/main/java/org/springframework/hateoas/TemplateVariable.java]\n" +
                "[52]\n" +
                " HUNK (1) INS Modifiers [547:2-549:2 src/main/java/org/springframework/hateoas/TemplateVariable.java]-[268:2-268:7 src/main/java/org/springframework/hateoas/TemplateVariable.java]\n" +
                "[53]\n" +
                " HUNK (6) DEL EnumDeclaration [583:1-585:1 src/main/java/org/springframework/hateoas/TemplateVariable.java]-[34:60-295:0 src/main/java/org/springframework/hateoas/TemplateVariable.java]\n" +
                "[54]\n" +
                " HUNK (18) DEL ConstructorDeclaration [65:1-68:1 src/main/java/org/springframework/hateoas/TemplateVariable.java]-[34:60-295:0 src/main/java/org/springframework/hateoas/TemplateVariable.java]\n" +
                "[55]\n" +
                " HUNK (11) DEL MethodDeclaration [130:1-132:1 src/main/java/org/springframework/hateoas/TemplateVariables.java]-[38:89-189:0 src/main/java/org/springframework/hateoas/TemplateVariables.java]\n" +
                "[56]\n" +
                " HUNK (0) REL Argument|PrimaryMethodInvocation [187:18-187:35 src/main/java/org/springframework/hateoas/TemplateVariables.java]-[168:18-168:35 src/main/java/org/springframework/hateoas/TemplateVariables.java]\n" +
                " HUNK (0) REL Arguments [187:34-187:35 src/main/java/org/springframework/hateoas/TemplateVariables.java]-[168:34-168:35 src/main/java/org/springframework/hateoas/TemplateVariables.java]\n" +
                "[57]\n" +
                " HUNK (0) DEL Block [196:17-198:2 src/main/java/org/springframework/hateoas/TemplateVariables.java]-[177:2-178:14 src/main/java/org/springframework/hateoas/TemplateVariables.java]\n" +
                "[58]\n" +
                " HUNK (0) DEL Block [199:47-201:2 src/main/java/org/springframework/hateoas/TemplateVariables.java]-[179:2-180:15 src/main/java/org/springframework/hateoas/TemplateVariables.java]\n" +
                "[59]\n" +
                " HUNK (0) DEL SingleTypeImportDeclaration [29:0-29:30 src/main/java/org/springframework/hateoas/TemplateVariables.java]-[18:0-31:38 src/main/java/org/springframework/hateoas/TemplateVariables.java]\n" +
                "[60]\n" +
                " HUNK (3) DEL LocalVariableDeclarationStatement [65:2-65:39 src/main/java/org/springframework/hateoas/TemplateVariables.java]-[59:60-65:1 src/main/java/org/springframework/hateoas/TemplateVariables.java]\n" +
                "[61]\n" +
                " HUNK (11) DEL LocalVariableDeclarationStatement [66:2-66:70 src/main/java/org/springframework/hateoas/TemplateVariables.java]-[59:60-65:1 src/main/java/org/springframework/hateoas/TemplateVariables.java]\n" +
                "[62]\n" +
                " HUNK (26) DEL EnhancedForStatement [68:2-77:2 src/main/java/org/springframework/hateoas/TemplateVariables.java]-[59:60-65:1 src/main/java/org/springframework/hateoas/TemplateVariables.java]\n" +
                "[63]\n" +
                " HUNK (0) REL Name [79:48-79:56 src/main/java/org/springframework/hateoas/TemplateVariables.java]-[64:48-64:56 src/main/java/org/springframework/hateoas/TemplateVariables.java]\n" +
                "[64]\n" +
                " HUNK (3) DEL PrimaryMethodInvocationStatement [101:6-101:33 src/main/java/org/springframework/hateoas/UriTemplate.java]-[83:29-98:3 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (2) INS PrimaryMethodInvocation [81:11-81:37 src/main/java/org/springframework/hateoas/UriTemplate.java]-[78:15-78:30 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (0) REL VariableDeclarator [81:11-81:37 src/main/java/org/springframework/hateoas/UriTemplate.java]-[78:7-78:30 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (2) DEL PrimaryMethodInvocation [81:22-81:37 src/main/java/org/springframework/hateoas/UriTemplate.java]-[78:7-78:30 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (0) REL LocalVariableDeclarationStatement [81:4-81:38 src/main/java/org/springframework/hateoas/UriTemplate.java]-[78:3-78:31 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (0) REL IntType [81:4-81:9 src/main/java/org/springframework/hateoas/UriTemplate.java]-[78:3-78:5 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (2) INS LocalVariableDeclarationStatement [86:36-104:4 src/main/java/org/springframework/hateoas/UriTemplate.java]-[85:4-85:29 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (15) INS IfStatement [86:36-104:4 src/main/java/org/springframework/hateoas/UriTemplate.java]-[87:4-91:4 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (0) INS ReferenceType [86:36-104:4 src/main/java/org/springframework/hateoas/UriTemplate.java]-[88:20-88:35 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (2) INS Block [86:36-104:4 src/main/java/org/springframework/hateoas/UriTemplate.java]-[89:11-91:4 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (11) INS IfStatement [86:36-104:4 src/main/java/org/springframework/hateoas/UriTemplate.java]-[93:4-95:4 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (6) DEL LocalVariableDeclarationStatement [88:5-88:51 src/main/java/org/springframework/hateoas/UriTemplate.java]-[83:29-98:3 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (4) DEL Block [90:26-103:5 src/main/java/org/springframework/hateoas/UriTemplate.java]-[83:29-98:3 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (37) DEL WhileStatement [90:5-103:5 src/main/java/org/springframework/hateoas/UriTemplate.java]-[83:29-98:3 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (0) REL Name [92:20-92:24 src/main/java/org/springframework/hateoas/UriTemplate.java]-[87:22-87:49 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (0) REL PrimaryMethodInvocation [92:20-92:33 src/main/java/org/springframework/hateoas/UriTemplate.java]-[87:22-87:60 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (0) REL Arguments [92:31-92:33 src/main/java/org/springframework/hateoas/UriTemplate.java]-[87:59-87:60 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (0) DEL Argument|IntegerLiteral [92:32-92:32 src/main/java/org/springframework/hateoas/UriTemplate.java]-[87:59-87:60 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (2) DEL LocalVariableDeclarationStatement [92:6-92:34 src/main/java/org/springframework/hateoas/UriTemplate.java]-[83:29-98:3 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (6) DEL LocalVariableDeclarationStatement [93:6-93:35 src/main/java/org/springframework/hateoas/UriTemplate.java]-[83:29-98:3 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (0) REL Name [94:25-94:29 src/main/java/org/springframework/hateoas/UriTemplate.java]-[88:55-88:58 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (0) REL PrimaryMethodInvocation [94:25-94:38 src/main/java/org/springframework/hateoas/UriTemplate.java]-[88:55-88:67 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (0) REL Arguments [94:36-94:38 src/main/java/org/springframework/hateoas/UriTemplate.java]-[88:66-88:67 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (0) DEL Argument|IntegerLiteral [94:37-94:37 src/main/java/org/springframework/hateoas/UriTemplate.java]-[88:66-88:67 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (2) DEL LocalVariableDeclarationStatement [94:6-94:39 src/main/java/org/springframework/hateoas/UriTemplate.java]-[83:29-98:3 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (2) DEL LocalVariableDeclarationStatement [96:6-96:66 src/main/java/org/springframework/hateoas/UriTemplate.java]-[83:29-98:3 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (7) DEL Conditional [98:17-98:80 src/main/java/org/springframework/hateoas/UriTemplate.java]-[83:29-98:3 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (14) DEL AssignStatement [99:6-99:108 src/main/java/org/springframework/hateoas/UriTemplate.java]-[83:29-98:3 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                "[65]\n" +
                " HUNK (6) DEL PrimaryMethodInvocationStatement [106:4-106:52 src/main/java/org/springframework/hateoas/UriTemplate.java]-[76:25-99:2 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                "[66]\n" +
                " HUNK (0) INS Name [111:16-111:45 src/main/java/org/springframework/hateoas/UriTemplate.java]-[102:17-102:24 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (0) REL PrimaryMethodInvocation [111:16-111:45 src/main/java/org/springframework/hateoas/UriTemplate.java]-[102:17-102:54 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (0) DEL ReferenceType [111:20-111:31 src/main/java/org/springframework/hateoas/UriTemplate.java]-[102:17-102:54 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (0) REL FieldAccess [111:2-111:12 src/main/java/org/springframework/hateoas/UriTemplate.java]-[102:2-102:13 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (0) REL Arguments [111:32-111:45 src/main/java/org/springframework/hateoas/UriTemplate.java]-[102:35-102:54 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (0) INS Argument|IntegerLiteral [111:32-111:45 src/main/java/org/springframework/hateoas/UriTemplate.java]-[102:36-102:36 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (0) REL Name [111:33-111:44 src/main/java/org/springframework/hateoas/UriTemplate.java]-[102:39-102:53 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (0) DEL Name [112:17-112:23 src/main/java/org/springframework/hateoas/UriTemplate.java]-[103:2-103:39 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (0) REL FieldAccess [112:2-112:13 src/main/java/org/springframework/hateoas/UriTemplate.java]-[103:2-103:13 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (2) INS SimpleMethodInvocation [112:2-112:24 src/main/java/org/springframework/hateoas/UriTemplate.java]-[103:17-103:38 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (3) DEL AssignStatement [113:2-113:26 src/main/java/org/springframework/hateoas/UriTemplate.java]-[68:38-104:1 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (0) REL ConstructorBody [122:104-131:1 src/main/java/org/springframework/hateoas/UriTemplate.java]-[113:93-122:1 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (0) REL Modifiers [122:1-122:7 src/main/java/org/springframework/hateoas/UriTemplate.java]-[113:1-113:7 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (0) REL ConstructorDeclaration [122:1-131:1 src/main/java/org/springframework/hateoas/UriTemplate.java]-[113:1-122:1 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (0) REL Parameters [122:20-122:102 src/main/java/org/springframework/hateoas/UriTemplate.java]-[113:20-113:91 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (1) DEL Parameter [122:37-122:51 src/main/java/org/springframework/hateoas/UriTemplate.java]-[113:20-113:91 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (0) REL Parameter [122:83-122:101 src/main/java/org/springframework/hateoas/UriTemplate.java]-[113:66-113:90 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (0) REL ReferenceType [122:83-122:94 src/main/java/org/springframework/hateoas/UriTemplate.java]-[113:66-113:82 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (0) REL Name [129:16-129:21 src/main/java/org/springframework/hateoas/UriTemplate.java]-[121:17-121:23 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (0) REL FieldAccess [129:2-129:12 src/main/java/org/springframework/hateoas/UriTemplate.java]-[121:2-121:13 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (3) DEL AssignStatement [130:2-130:26 src/main/java/org/springframework/hateoas/UriTemplate.java]-[113:93-122:1 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (4) DEL LocalVariableDeclarationStatement [176:2-176:35 src/main/java/org/springframework/hateoas/UriTemplate.java]-[156:54-184:1 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (0) DEL Argument|Name [205:34-205:44 src/main/java/org/springframework/hateoas/UriTemplate.java]-[183:24-183:77 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (0) INS This [205:78-205:83 src/main/java/org/springframework/hateoas/UriTemplate.java]-[183:65-183:68 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (0) REL Argument|FieldAccess [205:78-205:83 src/main/java/org/springframework/hateoas/UriTemplate.java]-[183:65-183:76 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (0) REL StandardInstanceCreation [205:9-205:84 src/main/java/org/springframework/hateoas/UriTemplate.java]-[183:9-183:77 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (4) INS EnhancedForStatement [274:41-292:1 src/main/java/org/springframework/hateoas/UriTemplate.java]-[266:2-270:2 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (15) INS Block [274:41-292:1 src/main/java/org/springframework/hateoas/UriTemplate.java]-[266:59-270:2 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (0) INS Argument|Name [274:41-292:1 src/main/java/org/springframework/hateoas/UriTemplate.java]-[269:28-269:35 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (2) INS PrimaryMethodInvocation [285:13-289:4 src/main/java/org/springframework/hateoas/UriTemplate.java]-[264:19-264:33 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (15) DEL Block [285:19-289:4 src/main/java/org/springframework/hateoas/UriTemplate.java]-[264:13-264:33 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (0) REL Arguments [288:12-288:22 src/main/java/org/springframework/hateoas/UriTemplate.java]-[269:18-269:43 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (0) REL Argument|Name [288:13-288:14 src/main/java/org/springframework/hateoas/UriTemplate.java]-[269:19-269:25 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (0) REL SimpleMethodInvocationStatement [288:5-288:23 src/main/java/org/springframework/hateoas/UriTemplate.java]-[269:3-269:44 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (0) DEL Name [288:5-288:7 src/main/java/org/springframework/hateoas/UriTemplate.java]-[264:13-264:33 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (0) REL FieldAccess [308:18-308:25 src/main/java/org/springframework/hateoas/UriTemplate.java]-[289:23-289:29 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (0) REL LocalVariableDeclarationStatement [308:2-308:26 src/main/java/org/springframework/hateoas/UriTemplate.java]-[289:2-289:49 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (0) REL ReferenceType [308:2-308:7 src/main/java/org/springframework/hateoas/UriTemplate.java]-[289:2-289:11 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (0) REL VariableDeclarator [308:9-308:25 src/main/java/org/springframework/hateoas/UriTemplate.java]-[289:13-289:48 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (2) INS PrimaryMethodInvocation [308:9-308:25 src/main/java/org/springframework/hateoas/UriTemplate.java]-[289:23-289:48 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (0) DEL FieldAccess [310:27-310:32 src/main/java/org/springframework/hateoas/UriTemplate.java]-[291:35-291:56 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (0) REL SimpleMethodInvocation [310:27-310:42 src/main/java/org/springframework/hateoas/UriTemplate.java]-[291:35-291:56 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (0) INS Arguments [310:27-310:42 src/main/java/org/springframework/hateoas/UriTemplate.java]-[291:55-291:56 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (7) DEL PrimaryMethodInvocation [311:12-311:69 src/main/java/org/springframework/hateoas/UriTemplate.java]-[292:3-292:73 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (0) REL SimpleMethodInvocationStatement [311:3-311:70 src/main/java/org/springframework/hateoas/UriTemplate.java]-[292:3-292:73 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (0) DEL Name [311:3-311:8 src/main/java/org/springframework/hateoas/UriTemplate.java]-[292:3-292:73 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (0) REL Arguments [311:57-311:68 src/main/java/org/springframework/hateoas/UriTemplate.java]-[292:18-292:72 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (0) INS Argument|Name [311:57-311:68 src/main/java/org/springframework/hateoas/UriTemplate.java]-[292:19-292:25 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (0) INS Argument|Name [311:57-311:68 src/main/java/org/springframework/hateoas/UriTemplate.java]-[292:28-292:35 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (4) INS PrimaryMethodInvocation [311:57-311:68 src/main/java/org/springframework/hateoas/UriTemplate.java]-[292:38-292:71 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (0) REL Arguments [314:19-314:26 src/main/java/org/springframework/hateoas/UriTemplate.java]-[295:22-295:33 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (0) REL Name [314:20-314:25 src/main/java/org/springframework/hateoas/UriTemplate.java]-[295:23-295:32 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (0) INS Name [314:9-314:26 src/main/java/org/springframework/hateoas/UriTemplate.java]-[295:9-295:15 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (0) REL PrimaryMethodInvocation [314:9-314:26 src/main/java/org/springframework/hateoas/UriTemplate.java]-[295:9-295:33 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (25) INS IfStatement [339:26-341:1 src/main/java/org/springframework/hateoas/UriTemplate.java]-[314:2-320:2 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (3) INS VariableDeclarator [339:26-341:1 src/main/java/org/springframework/hateoas/UriTemplate.java]-[316:17-316:80 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (2) INS TypeMethodInvocation [339:26-341:1 src/main/java/org/springframework/hateoas/UriTemplate.java]-[316:30-316:72 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (3) INS PrimaryMethodInvocation [339:26-341:1 src/main/java/org/springframework/hateoas/UriTemplate.java]-[316:30-316:80 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (0) REL FieldAccess [340:9-340:16 src/main/java/org/springframework/hateoas/UriTemplate.java]-[322:9-322:16 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (0) REL ReferenceType [343:16-343:21 src/main/java/org/springframework/hateoas/UriTemplate.java]-[339:16-339:32 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (0) REL Modifiers [343:1-343:14 src/main/java/org/springframework/hateoas/UriTemplate.java]-[339:1-339:14 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (0) REL MethodDeclaration [343:1-361:1 src/main/java/org/springframework/hateoas/UriTemplate.java]-[339:1-349:1 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (0) REL Parameters [343:38-343:65 src/main/java/org/springframework/hateoas/UriTemplate.java]-[339:47-339:62 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (0) REL Parameter [343:39-343:53 src/main/java/org/springframework/hateoas/UriTemplate.java]-[339:48-339:61 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (1) DEL Parameter [343:56-343:64 src/main/java/org/springframework/hateoas/UriTemplate.java]-[339:47-339:62 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (0) REL MethodBody [343:67-361:1 src/main/java/org/springframework/hateoas/UriTemplate.java]-[339:64-349:1 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (3) INS PrimaryMethodInvocationStatement [343:67-361:1 src/main/java/org/springframework/hateoas/UriTemplate.java]-[346:2-346:31 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (0) REL ReferenceType [345:2-345:7 src/main/java/org/springframework/hateoas/UriTemplate.java]-[341:2-341:13 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (0) REL LocalVariableDeclarationStatement [345:2-345:76 src/main/java/org/springframework/hateoas/UriTemplate.java]-[341:2-343:38 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (0) REL Name [345:43-345:50 src/main/java/org/springframework/hateoas/UriTemplate.java]-[341:38-341:44 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (0) REL VariableDeclarator [345:9-345:75 src/main/java/org/springframework/hateoas/UriTemplate.java]-[341:15-343:37 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (7) INS Conditional [345:9-345:75 src/main/java/org/springframework/hateoas/UriTemplate.java]-[341:22-343:37 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (0) INS Name [345:9-345:75 src/main/java/org/springframework/hateoas/UriTemplate.java]-[341:82-341:88 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (10) DEL IfStatement [347:2-349:2 src/main/java/org/springframework/hateoas/UriTemplate.java]-[339:64-349:1 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (2) DEL TypeMethodInvocation [353:23-353:62 src/main/java/org/springframework/hateoas/UriTemplate.java]-[345:37-345:66 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (1) DEL PrimaryMethodInvocation [353:23-354:12 src/main/java/org/springframework/hateoas/UriTemplate.java]-[345:37-345:66 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (3) DEL PrimaryMethodInvocation [353:23-355:11 src/main/java/org/springframework/hateoas/UriTemplate.java]-[345:37-345:66 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (0) REL StandardInstanceCreation [353:23-356:17 src/main/java/org/springframework/hateoas/UriTemplate.java]-[345:37-345:66 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (0) INS ReferenceType [353:23-356:17 src/main/java/org/springframework/hateoas/UriTemplate.java]-[345:41-345:64 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (0) DEL ReferenceType [353:2-353:7 src/main/java/org/springframework/hateoas/UriTemplate.java]-[345:2-345:67 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (0) INS VariableDeclarator [353:2-356:18 src/main/java/org/springframework/hateoas/UriTemplate.java]-[345:27-345:66 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (0) INS ReferenceType [353:2-356:18 src/main/java/org/springframework/hateoas/UriTemplate.java]-[345:2-345:25 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (0) REL LocalVariableDeclarationStatement [353:2-356:18 src/main/java/org/springframework/hateoas/UriTemplate.java]-[345:2-345:67 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (0) REL Argument|FieldAccess [353:58-353:61 src/main/java/org/springframework/hateoas/UriTemplate.java]-[316:65-316:71 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (0) REL VariableDeclarator [353:9-356:17 src/main/java/org/springframework/hateoas/UriTemplate.java]-[316:17-316:80 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (0) DEL VariableDeclarator [353:9-356:17 src/main/java/org/springframework/hateoas/UriTemplate.java]-[345:2-345:67 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (0) REL Arguments [356:16-356:17 src/main/java/org/springframework/hateoas/UriTemplate.java]-[345:65-345:66 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (1) DEL AssignStatement [358:2-358:66 src/main/java/org/springframework/hateoas/UriTemplate.java]-[339:64-349:1 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (0) DEL Name [358:32-358:35 src/main/java/org/springframework/hateoas/UriTemplate.java]-[339:64-349:1 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (0) REL Name [358:48-358:58 src/main/java/org/springframework/hateoas/UriTemplate.java]-[342:6-342:29 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (0) REL Name [358:62-358:65 src/main/java/org/springframework/hateoas/UriTemplate.java]-[343:6-343:37 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (0) DEL Name [358:9-358:19 src/main/java/org/springframework/hateoas/UriTemplate.java]-[339:64-349:1 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (0) REL Lt [358:9-358:44 src/main/java/org/springframework/hateoas/UriTemplate.java]-[341:22-341:97 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (7) DEL Conditional [358:9-358:65 src/main/java/org/springframework/hateoas/UriTemplate.java]-[339:64-349:1 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (7) MOV Conditional [358:9-358:65 src/main/java/org/springframework/hateoas/UriTemplate.java]-[341:22-343:37 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (0) DEL Name [360:16-360:19 src/main/java/org/springframework/hateoas/UriTemplate.java]-[348:9-348:15 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (0) DEL Name [360:9-360:12 src/main/java/org/springframework/hateoas/UriTemplate.java]-[348:9-348:15 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (0) REL Name [360:9-360:19 src/main/java/org/springframework/hateoas/UriTemplate.java]-[348:9-348:15 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (125) DEL ClassDeclaration [408:1-469:1 src/main/java/org/springframework/hateoas/UriTemplate.java]-[52:77-426:0 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (0) REL FieldAccess [422:15-422:23 src/main/java/org/springframework/hateoas/UriTemplate.java]-[327:9-327:17 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (2) DEL PrimaryMethodInvocation [422:15-422:32 src/main/java/org/springframework/hateoas/UriTemplate.java]-[52:77-426:0 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (0) REL Modifiers [436:2-438:7 src/main/java/org/springframework/hateoas/UriTemplate.java]-[325:1-325:7 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (0) REL MethodDeclaration [436:2-444:2 src/main/java/org/springframework/hateoas/UriTemplate.java]-[325:1-330:1 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (13) DEL MethodDeclaration [436:2-444:2 src/main/java/org/springframework/hateoas/UriTemplate.java]-[52:77-426:0 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (0) DEL MarkerAnnotation [437:2-437:10 src/main/java/org/springframework/hateoas/UriTemplate.java]-[52:77-426:0 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (1) DEL Parameters [438:22-438:48 src/main/java/org/springframework/hateoas/UriTemplate.java]-[52:77-426:0 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (2) DEL ReferenceType [438:23-438:36 src/main/java/org/springframework/hateoas/UriTemplate.java]-[52:77-426:0 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (0) REL ReferenceType [438:27-438:32 src/main/java/org/springframework/hateoas/UriTemplate.java]-[405:9-405:14 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (0) REL Private [438:2-438:7 src/main/java/org/springframework/hateoas/UriTemplate.java]-[325:1-325:7 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (0) DEL Wildcard [438:35-438:35 src/main/java/org/springframework/hateoas/UriTemplate.java]-[52:77-426:0 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (0) REL MethodBody [438:50-444:2 src/main/java/org/springframework/hateoas/UriTemplate.java]-[325:50-330:1 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (0) REL ReferenceType [438:9-438:14 src/main/java/org/springframework/hateoas/UriTemplate.java]-[325:9-325:25 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (3) DEL PrimaryMethodInvocation [440:10-443:34 src/main/java/org/springframework/hateoas/UriTemplate.java]-[52:77-426:0 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (0) DEL FieldAccess [440:20-440:28 src/main/java/org/springframework/hateoas/UriTemplate.java]-[52:77-426:0 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (1) DEL PrimaryMethodInvocation [440:20-440:37 src/main/java/org/springframework/hateoas/UriTemplate.java]-[52:77-426:0 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (0) DEL PrimaryMethodInvocation [440:20-441:43 src/main/java/org/springframework/hateoas/UriTemplate.java]-[52:77-426:0 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (6) DEL PrimaryMethodInvocation [440:20-443:33 src/main/java/org/springframework/hateoas/UriTemplate.java]-[52:77-426:0 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (0) REL Name [441:16-441:17 src/main/java/org/springframework/hateoas/UriTemplate.java]-[401:39-401:45 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (0) REL PrimaryMethodInvocation [441:16-441:42 src/main/java/org/springframework/hateoas/UriTemplate.java]-[401:39-401:66 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (0) REL Arguments [441:31-441:42 src/main/java/org/springframework/hateoas/UriTemplate.java]-[401:57-401:66 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (0) INS Argument|FieldAccess [441:31-441:42 src/main/java/org/springframework/hateoas/UriTemplate.java]-[401:64-401:65 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (0) REL Name [441:32-441:41 src/main/java/org/springframework/hateoas/UriTemplate.java]-[401:58-401:61 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (0) REL Arguments [441:9-441:43 src/main/java/org/springframework/hateoas/UriTemplate.java]-[401:32-401:67 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (0) REL InferredFormalParameter [442:13-442:14 src/main/java/org/springframework/hateoas/UriTemplate.java]-[328:12-328:19 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (0) REL FieldAccess [442:19-442:20 src/main/java/org/springframework/hateoas/UriTemplate.java]-[328:25-328:32 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (0) DEL FieldAccess [442:19-442:20 src/main/java/org/springframework/hateoas/UriTemplate.java]-[52:77-426:0 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (1) DEL NotEq [442:19-442:28 src/main/java/org/springframework/hateoas/UriTemplate.java]-[52:77-426:0 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (1) DEL Argument|TypeMethodInvocation [443:14-443:32 src/main/java/org/springframework/hateoas/UriTemplate.java]-[52:77-426:0 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (0) REL Arguments [458:32-458:70 src/main/java/org/springframework/hateoas/UriTemplate.java]-[405:40-405:100 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (0) INS Lambda [458:32-458:70 src/main/java/org/springframework/hateoas/UriTemplate.java]-[405:41-405:99 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (0) DEL Argument|StringLiteral [458:33-458:35 src/main/java/org/springframework/hateoas/UriTemplate.java]-[405:40-405:100 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (0) REL Name [458:38-458:40 src/main/java/org/springframework/hateoas/UriTemplate.java]-[405:58-405:64 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (0) REL PrimaryMethodInvocation [458:38-458:64 src/main/java/org/springframework/hateoas/UriTemplate.java]-[405:58-405:99 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (0) REL Arguments [458:48-458:64 src/main/java/org/springframework/hateoas/UriTemplate.java]-[405:76-405:99 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (0) INS Argument|FieldAccess [458:48-458:64 src/main/java/org/springframework/hateoas/UriTemplate.java]-[405:93-405:98 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (0) DEL Argument|StringLiteral [458:67-458:69 src/main/java/org/springframework/hateoas/UriTemplate.java]-[405:40-405:100 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (13) INS MethodDeclaration [49:77-470:0 src/main/java/org/springframework/hateoas/UriTemplate.java]-[325:1-330:1 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (2) INS PrimaryMethodInvocation [49:77-470:0 src/main/java/org/springframework/hateoas/UriTemplate.java]-[327:9-327:26 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (3) INS PrimaryMethodInvocation [49:77-470:0 src/main/java/org/springframework/hateoas/UriTemplate.java]-[327:9-327:35 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (8) INS PrimaryMethodInvocation [49:77-470:0 src/main/java/org/springframework/hateoas/UriTemplate.java]-[327:9-329:86 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (2) INS TypeMethodInvocation [49:77-470:0 src/main/java/org/springframework/hateoas/UriTemplate.java]-[329:13-329:85 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (1) INS Argument|TypeMethodInvocation [49:77-470:0 src/main/java/org/springframework/hateoas/UriTemplate.java]-[329:42-329:60 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (82) INS MethodDeclaration [49:77-470:0 src/main/java/org/springframework/hateoas/UriTemplate.java]-[358:1-386:1 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (2) INS PrimaryMethodInvocation [49:77-470:0 src/main/java/org/springframework/hateoas/UriTemplate.java]-[370:10-370:27 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (40) INS MethodDeclaration [49:77-470:0 src/main/java/org/springframework/hateoas/UriTemplate.java]-[396:1-411:1 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (0) INS ReferenceType [49:77-470:0 src/main/java/org/springframework/hateoas/UriTemplate.java]-[403:30-403:32 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (0) INS ReferenceType [49:77-470:0 src/main/java/org/springframework/hateoas/UriTemplate.java]-[405:17-405:22 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (2) INS ReferenceType [49:77-470:0 src/main/java/org/springframework/hateoas/UriTemplate.java]-[405:5-405:23 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (19) INS MethodDeclaration [49:77-470:0 src/main/java/org/springframework/hateoas/UriTemplate.java]-[420:1-425:1 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (0) INS VariableDeclarator [49:77-470:0 src/main/java/org/springframework/hateoas/UriTemplate.java]-[58:16-58:22 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (3) INS FieldDeclaration [49:77-470:0 src/main/java/org/springframework/hateoas/UriTemplate.java]-[58:1-58:23 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (0) REL ReferenceType [56:15-56:26 src/main/java/org/springframework/hateoas/UriTemplate.java]-[59:19-59:35 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (0) REL Modifiers [56:1-56:13 src/main/java/org/springframework/hateoas/UriTemplate.java]-[59:1-59:17 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (0) REL FieldDeclaration [56:1-56:34 src/main/java/org/springframework/hateoas/UriTemplate.java]-[59:1-59:44 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (0) REL VariableDeclarator [56:28-56:33 src/main/java/org/springframework/hateoas/UriTemplate.java]-[59:37-59:43 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (0) REL Transient [56:9-56:13 src/main/java/org/springframework/hateoas/UriTemplate.java]-[59:9-59:17 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (0) REL Modifiers [57:1-57:13 src/main/java/org/springframework/hateoas/UriTemplate.java]-[61:1-61:7 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (0) REL FieldDeclaration [57:1-57:39 src/main/java/org/springframework/hateoas/UriTemplate.java]-[61:1-61:24 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (0) DEL VariableDeclarator [57:22-57:28 src/main/java/org/springframework/hateoas/UriTemplate.java]-[61:1-61:24 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (0) REL VariableDeclarator [57:31-57:38 src/main/java/org/springframework/hateoas/UriTemplate.java]-[61:16-61:23 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (0) REL PrimaryMethodInvocation [68:29-68:49 src/main/java/org/springframework/hateoas/UriTemplate.java]-[73:24-73:40 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (0) REL LocalVariableDeclarationStatement [68:2-68:50 src/main/java/org/springframework/hateoas/UriTemplate.java]-[73:2-73:41 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (0) REL Arguments [68:45-68:49 src/main/java/org/springframework/hateoas/UriTemplate.java]-[73:39-73:40 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (0) DEL Argument|CharacterLiteral [68:46-68:48 src/main/java/org/springframework/hateoas/UriTemplate.java]-[73:39-73:40 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (0) REL VariableDeclarator [68:6-68:49 src/main/java/org/springframework/hateoas/UriTemplate.java]-[73:6-73:40 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (5) DEL AssignStatement [69:2-69:60 src/main/java/org/springframework/hateoas/UriTemplate.java]-[68:38-104:1 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                "[67]\n" +
                " HUNK (3) INS TypeMethodInvocationStatement [122:104-131:1 src/main/java/org/springframework/hateoas/UriTemplate.java]-[117:2-117:64 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                "[68]\n" +
                " HUNK (3) DEL LocalVariableDeclarationStatement [175:2-175:31 src/main/java/org/springframework/hateoas/UriTemplate.java]-[156:54-184:1 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                "[69]\n" +
                " HUNK (0) INS SingleTypeImportDeclaration [18:0-39:44 src/main/java/org/springframework/hateoas/UriTemplate.java]-[18:0-18:26 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                "[70]\n" +
                " HUNK (0) INS SingleTypeImportDeclaration [18:0-39:44 src/main/java/org/springframework/hateoas/UriTemplate.java]-[19:0-19:32 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                "[71]\n" +
                " HUNK (0) INS SingleTypeImportDeclaration [18:0-39:44 src/main/java/org/springframework/hateoas/UriTemplate.java]-[36:0-36:60 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                "[72]\n" +
                " HUNK (0) INS SingleTypeImportDeclaration [18:0-39:44 src/main/java/org/springframework/hateoas/UriTemplate.java]-[37:0-37:73 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                "[73]\n" +
                " HUNK (0) INS SingleTypeImportDeclaration [18:0-39:44 src/main/java/org/springframework/hateoas/UriTemplate.java]-[38:0-38:46 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                "[74]\n" +
                " HUNK (5) DEL LocalVariableDeclarationStatement [191:3-191:78 src/main/java/org/springframework/hateoas/UriTemplate.java]-[167:46-181:2 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (2) DEL PrimaryMethodInvocation [191:59-191:76 src/main/java/org/springframework/hateoas/UriTemplate.java]-[167:46-181:2 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                "[75]\n" +
                " HUNK (8) DEL LocalVariableDeclarationStatement [192:3-192:75 src/main/java/org/springframework/hateoas/UriTemplate.java]-[167:46-181:2 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                "[76]\n" +
                " HUNK (30) DEL IfStatement [194:3-199:3 src/main/java/org/springframework/hateoas/UriTemplate.java]-[167:46-181:2 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                "[77]\n" +
                " HUNK (5) DEL AssignStatement [201:3-201:38 src/main/java/org/springframework/hateoas/UriTemplate.java]-[167:46-181:2 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                "[78]\n" +
                " HUNK (0) DEL SingleTypeImportDeclaration [23:0-23:28 src/main/java/org/springframework/hateoas/UriTemplate.java]-[18:0-42:44 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                "[79]\n" +
                " HUNK (0) DEL SingleTypeImportDeclaration [24:0-24:24 src/main/java/org/springframework/hateoas/UriTemplate.java]-[18:0-42:44 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                "[80]\n" +
                " HUNK (6) INS LocalVariableDeclarationStatement [274:41-292:1 src/main/java/org/springframework/hateoas/UriTemplate.java]-[258:2-258:49 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                "[81]\n" +
                " HUNK (9) DEL LocalVariableDeclarationStatement [281:2-281:43 src/main/java/org/springframework/hateoas/UriTemplate.java]-[252:41-273:1 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                "[82]\n" +
                " HUNK (1) INS PrimaryMethodInvocation [283:2-283:19 src/main/java/org/springframework/hateoas/UriTemplate.java]-[261:2-261:19 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (0) REL PrimaryMethodInvocation [283:2-284:34 src/main/java/org/springframework/hateoas/UriTemplate.java]-[261:2-262:40 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (5) INS PrimaryMethodInvocation [283:2-289:6 src/main/java/org/springframework/hateoas/UriTemplate.java]-[261:2-263:36 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (0) REL Arguments [284:8-284:34 src/main/java/org/springframework/hateoas/UriTemplate.java]-[262:11-262:40 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (0) REL Argument|NameMethodReference [284:9-284:33 src/main/java/org/springframework/hateoas/UriTemplate.java]-[262:12-262:39 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                "[83]\n" +
                " HUNK (0) REL InferredFormalParameter [285:13-285:14 src/main/java/org/springframework/hateoas/UriTemplate.java]-[264:13-264:14 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                "[84]\n" +
                " HUNK (15) MOV Block [285:19-289:4 src/main/java/org/springframework/hateoas/UriTemplate.java]-[266:59-270:2 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                "[85]\n" +
                " HUNK (0) REL Block [285:19-289:4 src/main/java/org/springframework/hateoas/UriTemplate.java]-[266:59-270:2 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                "[86]\n" +
                " HUNK (0) REL Arguments [291:15-291:19 src/main/java/org/springframework/hateoas/UriTemplate.java]-[272:22-272:33 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (0) REL Name [291:16-291:18 src/main/java/org/springframework/hateoas/UriTemplate.java]-[272:23-272:32 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (0) INS Name [291:9-291:19 src/main/java/org/springframework/hateoas/UriTemplate.java]-[272:9-272:15 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (0) REL PrimaryMethodInvocation [291:9-291:19 src/main/java/org/springframework/hateoas/UriTemplate.java]-[272:9-272:33 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                "[87]\n" +
                " HUNK (0) REL ReferenceType [310:7-310:17 src/main/java/org/springframework/hateoas/UriTemplate.java]-[291:7-291:22 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (0) REL Parameter [310:7-310:23 src/main/java/org/springframework/hateoas/UriTemplate.java]-[291:7-291:31 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                "[88]\n" +
                " HUNK (11) DEL InterfaceDeclaration [317:1-323:1 src/main/java/org/springframework/hateoas/UriTemplate.java]-[52:77-426:0 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (0) DEL ReferenceType [320:16-320:29 src/main/java/org/springframework/hateoas/UriTemplate.java]-[52:77-426:0 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (2) DEL TypeArguments [320:19-320:29 src/main/java/org/springframework/hateoas/UriTemplate.java]-[52:77-426:0 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                "[89]\n" +
                " HUNK (13) DEL LocalVariableDeclarationStatement [351:2-351:69 src/main/java/org/springframework/hateoas/UriTemplate.java]-[339:64-349:1 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                "[90]\n" +
                " HUNK (12) DEL LocalVariableDeclarationStatement [352:2-352:60 src/main/java/org/springframework/hateoas/UriTemplate.java]-[339:64-349:1 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                "[91]\n" +
                " HUNK (0) MOV VariableDeclarator [353:9-356:17 src/main/java/org/springframework/hateoas/UriTemplate.java]-[316:17-316:80 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                "[92]\n" +
                " HUNK (123) DEL ClassDeclaration [363:1-406:1 src/main/java/org/springframework/hateoas/UriTemplate.java]-[52:77-426:0 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                "[93]\n" +
                " HUNK (5) MOV MethodDeclaration [436:2-444:2 src/main/java/org/springframework/hateoas/UriTemplate.java]-[325:1-330:1 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                "[94]\n" +
                " HUNK (0) MOV FieldAccess [442:19-442:20 src/main/java/org/springframework/hateoas/UriTemplate.java]-[328:25-328:32 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                "[95]\n" +
                " HUNK (0) REL FieldAccess [458:49-458:52 src/main/java/org/springframework/hateoas/UriTemplate.java]-[405:77-405:79 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                "[96]\n" +
                " HUNK (2) INS Negation [49:77-470:0 src/main/java/org/springframework/hateoas/UriTemplate.java]-[328:24-328:45 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (0) INS FieldAccess [49:77-470:0 src/main/java/org/springframework/hateoas/UriTemplate.java]-[328:25-328:32 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                "[97]\n" +
                " HUNK (0) REL Argument|StringLiteral [51:63-51:115 src/main/java/org/springframework/hateoas/UriTemplate.java]-[54:63-54:96 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                "[98]\n" +
                " HUNK (9) DEL FieldDeclaration [52:1-52:92 src/main/java/org/springframework/hateoas/UriTemplate.java]-[52:77-426:0 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                "[99]\n" +
                " HUNK (0) DEL Final [57:9-57:13 src/main/java/org/springframework/hateoas/UriTemplate.java]-[61:1-61:7 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                "[100]\n" +
                " HUNK (6) INS LocalVariableDeclarationStatement [64:38-114:1 src/main/java/org/springframework/hateoas/UriTemplate.java]-[72:2-72:52 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (6) DEL LocalVariableDeclarationStatement [77:3-77:53 src/main/java/org/springframework/hateoas/UriTemplate.java]-[68:38-104:1 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                "[101]\n" +
                " HUNK (3) DEL LocalVariableDeclarationStatement [70:2-70:27 src/main/java/org/springframework/hateoas/UriTemplate.java]-[68:38-104:1 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                "[102]\n" +
                " HUNK (8) DEL LocalVariableDeclarationStatement [73:2-73:52 src/main/java/org/springframework/hateoas/UriTemplate.java]-[68:38-104:1 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                "[103]\n" +
                " HUNK (5) DEL IfStatement [75:2-108:2 src/main/java/org/springframework/hateoas/UriTemplate.java]-[68:38-104:1 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                "[104]\n" +
                " HUNK (6) INS LocalVariableDeclarationStatement [79:26-107:3 src/main/java/org/springframework/hateoas/UriTemplate.java]-[80:3-80:58 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (0) INS Name [79:26-107:3 src/main/java/org/springframework/hateoas/UriTemplate.java]-[80:41-80:47 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (2) INS PrimaryMethodInvocation [79:26-107:3 src/main/java/org/springframework/hateoas/UriTemplate.java]-[80:41-80:56 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (0) DEL Argument|Name [83:42-83:49 src/main/java/org/springframework/hateoas/UriTemplate.java]-[76:25-99:2 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (4) DEL LocalVariableDeclarationStatement [83:4-83:51 src/main/java/org/springframework/hateoas/UriTemplate.java]-[76:25-99:2 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                "[105]\n" +
                " HUNK (0) REL VariableDeclarator [82:13-82:50 src/main/java/org/springframework/hateoas/UriTemplate.java]-[81:12-81:46 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (0) REL LocalVariableDeclarationStatement [82:4-82:51 src/main/java/org/springframework/hateoas/UriTemplate.java]-[81:3-81:47 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (0) REL Name [86:26-86:33 src/main/java/org/springframework/hateoas/UriTemplate.java]-[83:22-83:26 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                "[106]\n" +
                " HUNK (7) DEL LocalVariableDeclarationStatement [84:4-84:60 src/main/java/org/springframework/hateoas/UriTemplate.java]-[76:25-99:2 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                " HUNK (0) DEL ReferenceType [84:9-84:24 src/main/java/org/springframework/hateoas/UriTemplate.java]-[76:25-99:2 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                "[107]\n" +
                " HUNK (0) REL Parameter [86:9-86:22 src/main/java/org/springframework/hateoas/UriTemplate.java]-[83:8-83:18 src/main/java/org/springframework/hateoas/UriTemplate.java]\n" +
                "[108]\n" +
                " HUNK (7) INS LocalVariableDeclarationStatement [467:50-482:2 src/main/java/org/springframework/hateoas/server/core/WebHandler.java]-[475:3-475:81 src/main/java/org/springframework/hateoas/server/core/WebHandler.java]\n" +
                " HUNK (7) DEL LocalVariableDeclarationStatement [479:3-479:81 src/main/java/org/springframework/hateoas/server/core/WebHandler.java]-[467:50-482:2 src/main/java/org/springframework/hateoas/server/core/WebHandler.java]" +
                "";
        FileLineRangeMap fileLineRangeMap = new FileLineRangeMap();

        String[] lines = input.split("\n");
        for (String line : lines) {
            if(line.contains(" HUNK")){
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
