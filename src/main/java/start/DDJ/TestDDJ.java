package start.DDJ;

import start.DDJ.DDJThread;

/**
 * @author lsn
 * @date 2023/3/17 10:33 AM
 */
public class TestDDJ {

    public static void main(String[] args) throws InterruptedException {

        DDJThreadForNew ddjThread1 = new DDJThreadForNew("reldd1", "bic", true, 1,500, "log+matrix");
        DDJThreadForNew ddjThread2 = new DDJThreadForNew("reldd2", "bic", true,501,950, "log+matrix");
        DDJThreadForNew ddjThread3 = new DDJThreadForNew("reldd3", "bic", true,951,1400, "log+matrix");
        DDJThreadForNew ddjThread4 = new DDJThreadForNew("reldd4", "bic", true,1401,1800, "log+matrix");

//        DDJThread ddjThread1 = new DDJThread("prodd1", "bic", true, 1,300);
//        DDJThread ddjThread2 = new DDJThread("prodd2", "bic", true,301,650);
//        DDJThread ddjThread3 = new DDJThread("prodd3", "bic", true,651,1300);
//        DDJThread ddjThread4 = new DDJThread("prodd4", "bic", true,1301,1800);

//        DDJThread ddjThread2 = new DDJThread("prodd", "bic", true);

//        DDJThreadForDefects4j ddjThread1 = new DDJThreadForDefects4j("ddmin", "defects4j", true);
//        ddjThread1.setName("defects4j_ddmin_true");
//        DDJThreadForDefects4j ddjThread2 = new DDJThreadForDefects4j("prodd", "defects4j", true);
//        ddjThread2.setName("defects4j_prodd_true");
        ddjThread1.start();
        Thread.sleep(10000); // Wait for 1 second
        ddjThread2.start();
        Thread.sleep(10000); // Wait for 1 second
        ddjThread3.start();
        Thread.sleep(10000); // Wait for 1 second
        ddjThread4.start();
//
//        DDJThreadForDiff ddjThread1 = new DDJThreadForDiff("ddmin", "diff", true);
//        ddjThread1.setName("diff_ddmin_true");
//        ddjThread1.start();
    }


}
