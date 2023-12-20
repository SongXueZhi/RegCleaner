package start.DDJ;

import start.DDJ.DDJThread;

/**
 * @author lsn
 * @date 2023/3/17 10:33 AM
 */
public class TestDDJ {

    public static void main(String[] args) throws InterruptedException {

//        DDJThreadForSelect ddjThreadForSelect = new DDJThreadForSelect("reldd", "bic", true, "matrix");
//        ddjThreadForSelect.run();
//        DDJThreadForDefects4jNew ddjThreadForDefects4jNew = new DDJThreadForDefects4jNew("reldd", "defects4j", true, "matrix");
//        ddjThreadForDefects4jNew.start();
//        DDJThreadForNew ddjThread1 = new DDJThreadForNew("reldd1", "bic", true, 1,650, "log+matrix");
//        DDJThreadForNew ddjThread2 = new DDJThreadForNew("reldd1", "bic", true,1,650, "log");
//        DDJThreadForNew ddjThread3 = new DDJThreadForNew("reldd1", "bic", true,1,650, "matrix");
//        DDJThreadForNew ddjThread4 = new DDJThreadForNew("reldd2", "bic", true, 651,1700, "log+matrix");
//        DDJThreadForNew ddjThread5 = new DDJThreadForNew("reldd2", "bic", true,651,1700, "log");
//        DDJThreadForNew ddjThread6 = new DDJThreadForNew("reldd2", "bic", true,651,1700, "matrix");
//        DDJThreadForNew ddjThread7 = new DDJThreadForNew("reldd3", "bic", true, 1701,1800, "log+matrix");
//        DDJThreadForNew ddjThread8 = new DDJThreadForNew("reldd3", "bic", true,1701,1800,"log");
//        DDJThreadForNew ddjThread9 = new DDJThreadForNew("reldd3", "bic", true,1701,1800, "matrix");
//        DDJThreadForNew ddjThread1 = new DDJThreadForNew("reldd1", "bic", true, 1,550, "noconsider");
//        DDJThreadForNew ddjThread3 = new DDJThreadForNew("reldd1", "bic", true,1,550, "nosamplex");
//        DDJThreadForNew ddjThread4 = new DDJThreadForNew("reldd2", "bic", true, 551,600, "noconsider");
//        DDJThreadForNew ddjThread6 = new DDJThreadForNew("reldd2", "bic", true,551,600, "nosamplex");
//        DDJThreadForNew ddjThread7 = new DDJThreadForNew("reldd3", "bic", true, 601,1800, "noconsider");
//        DDJThreadForNew ddjThread9 = new DDJThreadForNew("reldd3", "bic", true,601,1800, "nosamplex");


//        DDJThread ddjThread1 = new DDJThread("prodd1", "bic", true, 1,300);
//        DDJThread ddjThread2 = new DDJThread("prodd2", "bic", true,301,650);
//        DDJThread ddjThread3 = new DDJThread("prodd3", "bic", true,651,1300);
//        DDJThread ddjThread4 = new DDJThread("prodd4", "bic", true,1301,1800);

//        DDJThread ddjThread2 = new DDJThread("prodd", "bic", true);

        DDJThreadForDefects4j ddjThread1 = new DDJThreadForDefects4j("ddmin", "defects4j", true);
        DDJThreadForDefects4j ddjThread2 = new DDJThreadForDefects4j("prodd", "defects4j", true);
        DDJThreadForDefects4jNew ddjThread3 = new DDJThreadForDefects4jNew("reldd1", "defects4j", true, "log+matrix");
        DDJThreadForDefects4jNew ddjThread4 = new DDJThreadForDefects4jNew("reldd1", "defects4j", true, "log");
        DDJThreadForDefects4jNew ddjThread5 = new DDJThreadForDefects4jNew("reldd1", "defects4j", true,"matrix");
        DDJThreadForDefects4jNew ddjThread6 = new DDJThreadForDefects4jNew("reldd1", "defects4j", true,"noconsider");
        DDJThreadForDefects4jNew ddjThread7 = new DDJThreadForDefects4jNew("reldd1", "defects4j", true,"nostart");
        DDJThreadForDefects4jNew ddjThread8 = new DDJThreadForDefects4jNew("reldd1", "defects4j", true,"nosamplex");
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
        Thread.sleep(10000); // Wait for 10 second
        ddjThread8.start();
        Thread.sleep(10000); // Wait for 10 second
//        ddjThread9.start();
//
//        DDJThreadForDiff ddjThread1 = new DDJThreadForDiff("ddmin", "diff", true);
//        ddjThread1.setName("diff_ddmin_true");
//        ddjThread1.start();
    }


}
