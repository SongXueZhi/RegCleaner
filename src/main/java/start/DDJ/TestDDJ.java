package start.DDJ;

import start.DDJ.DDJThread;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author lsn
 * @date 2023/3/17 10:33 AM
 */
public class TestDDJ {

    public static void main(String[] args) throws InterruptedException {

//        DDJThreadForSelect ddjThreadForSelect = new DDJThreadForSelect("reldd", "bic", true, "matrix");
//        ddjThreadForSelect.run();
//        DDJThreadForDefects4jNew ddjThreadForDefects4jNew1 = new DDJThreadForDefects4jNew("reldd1", "defects4j", true, "matrix",
//                new ArrayList<>(Arrays.asList("Cli", "Closure")));
//        DDJThreadForDefects4jNew ddjThreadForDefects4jNew2 = new DDJThreadForDefects4jNew("reldd2", "defects4j", true, "matrix",
//                new ArrayList<>(Arrays.asList("Codec", "Collections", "Compress", "Csv", "Gson", "JacksonCore", "JacksonXml", "JxPath")));
//        DDJThreadForDefects4jNew ddjThreadForDefects4jNew3 = new DDJThreadForDefects4jNew("reldd3", "defects4j", true, "matrix",
//                new ArrayList<>(Arrays.asList("JacksonDatabind", "Jsoup")));
//        DDJThreadForDefects4jNew ddjThreadForDefects4jNew4 = new DDJThreadForDefects4jNew("reldd4", "defects4j", true, "matrix",
//                new ArrayList<>(Arrays.asList("Lang", "Math", "Mockito", "Time")));
//        ddjThreadForDefects4jNew1.start();
//        Thread.sleep(10000); // Wait for 10 second
//        ddjThreadForDefects4jNew2.start();
//        Thread.sleep(10000); // Wait for 10 second
//        ddjThreadForDefects4jNew3.start();
//        Thread.sleep(10000); // Wait for 10 second
//        ddjThreadForDefects4jNew4.start();


        DDJThreadForNew ddjThread1 = new DDJThreadForNew("reldd1", "bic", true, 1,610, "matrix");
        DDJThreadForNew ddjThread2 = new DDJThreadForNew("reldd2", "bic", true,611,630, "matrix");
        DDJThreadForNew ddjThread3 = new DDJThreadForNew("reldd3", "bic", true,631,650, "matrix");
        DDJThreadForNew ddjThread4 = new DDJThreadForNew("reldd4", "bic", true, 651,1000, "matrix");
        DDJThreadForNew ddjThread5 = new DDJThreadForNew("reldd5", "bic", true,1001,1020, "matrix");
        DDJThreadForNew ddjThread6 = new DDJThreadForNew("reldd6", "bic", true,1021,1600, "matrix");
        DDJThreadForNew ddjThread7 = new DDJThreadForNew("reldd7", "bic", true, 1601,1800, "matrix");



//        DDJThread ddjThread1 = new DDJThread("reldd1", "bic", true, 1,300);
//        DDJThread ddjThread2 = new DDJThread("reldd1", "bic", true,301,650);
//        DDJThread ddjThread3 = new DDJThread("reldd1", "bic", true,651,1300);
//        DDJThread ddjThread4 = new DDJThread("reldd1", "bic", true,1301,1800);

//        DDJThread ddjThread2 = new DDJThread("prodd", "bic", true);

//        DDJThreadForDefects4j ddjThread1 = new DDJThreadForDefects4j("ddmin", "defects4j", true);
//        DDJThreadForDefects4j ddjThread2 = new DDJThreadForDefects4j("prodd", "defects4j", true);
//        DDJThreadForDefects4jNew ddjThread3 = new DDJThreadForDefects4jNew("reldd1", "defects4j", true, "log+matrix");
//        DDJThreadForDefects4jNew ddjThread4 = new DDJThreadForDefects4jNew("reldd1", "defects4j", true, "log");
//        DDJThreadForDefects4jNew ddjThread5 = new DDJThreadForDefects4jNew("reldd1", "defects4j", true,"matrix");
//        DDJThreadForDefects4jNew ddjThread6 = new DDJThreadForDefects4jNew("reldd1", "defects4j", true,"noconsider");
//        DDJThreadForDefects4jNew ddjThread7 = new DDJThreadForDefects4jNew("reldd1", "defects4j", true,"nostart");
//        DDJThreadForDefects4jNew ddjThread8 = new DDJThreadForDefects4jNew("reldd1", "defects4j", true,"nosamplex");
        ddjThread1.start();
        Thread.sleep(10000); // Wait for 10 second
        ddjThread2.start();
        Thread.sleep(10000); // Wait for 10 second
        ddjThread3.start();
        Thread.sleep(10000); // Wait for 10 second
        ddjThread4.start();
        Thread.sleep(10000); // Wait for 10 second
        ddjThread5.start();
        Thread.sleep(10000); // Wait for 10 second
        ddjThread6.start();
        Thread.sleep(10000); // Wait for 10 second
        ddjThread7.start();
//        Thread.sleep(10000); // Wait for 10 second
//        ddjThread8.start();
//        Thread.sleep(10000); // Wait for 10 second
//        ddjThread9.start();
//
//        DDJThreadForDiff ddjThread1 = new DDJThreadForDiff("ddmin", "diff", true);
//        ddjThread1.setName("diff_ddmin_true");
//        ddjThread1.start();
    }


}
