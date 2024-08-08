package tankgame2;

import tankgame2.MyPanel;

import javax.swing.*;

import static java.lang.System.exit;

public class HspTankGame02 extends JFrame {
    //定义面板
    MyPanel mp = null;

    public static void main(String[] args) {
        HspTankGame02 hspTankGame02 = new HspTankGame02();

    }
    public HspTankGame02()
        mp = new MyPanel();

        //启动MyPanel线程
        Thread thread = new Thread(mp);
        thread.start();

        this.add(mp);//把面板加入

        this.setSize(1000,750);//设置窗口大小
        this.addKeyListener(mp);//让JFrame，监听mp的键盘事件
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);//窗口显示
    }
}
