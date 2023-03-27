package start;

/**
 * @author lsn
 * @date 2023/3/17 10:33 AM
 */
public class TestDDJ {

    public static void main(String[] args) {

//        DDJThread ddjThread1 = new DDJThread("ddmin", "defects4j", true);
//        DDJThread ddjThread2 = new DDJThread("prodd", "defects4j", true);
        DDJThreadForDefects4j ddjThread1 = new DDJThreadForDefects4j("ddmin", "defects4j", true);
        ddjThread1.setName("defects4j_ddmin_true");
        DDJThreadForDefects4j ddjThread2 = new DDJThreadForDefects4j("prodd", "defects4j", true);
        ddjThread2.setName("defects4j_prodd_true");
        ddjThread1.start();
        ddjThread2.start();

    }


}
