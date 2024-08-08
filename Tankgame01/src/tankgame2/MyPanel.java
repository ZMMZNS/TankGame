package tankgame2;

import tankgame2.Hero;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Vector;

import static java.lang.System.exit;

@SuppressWarnings("all")
//为了监听键盘实现接口
public class MyPanel extends JPanel implements KeyListener,Runnable {
    //定义自己的坦克
    tankgame2.Hero hero = null;
    //定义敌方坦克放入vector集合中方便日后多线程处理
    Vector<EnemyTank> enemytanks = new Vector<>();
    //定义敌人数量
    int enemytankSize = 3;
    //定义Vector，存放炸弹,当子弹击中坦克时加入炸弹
    Vector<boom> booms = new Vector<>();
    //定义三张炸弹图片显示爆炸效果
    Image image01 = null;
    Image image02 = null;
    Image image03 = null;



//构造器

    //初始化坦克位置
    public MyPanel(){
        //定义自己的坦克初始位置
        hero = new Hero(400,300);

        //定义敌方的坦克初始位置
        for (int i = 0; i < enemytankSize; i++) {
            //创建一个敌方坦克对象

            EnemyTank enemyTank = new EnemyTank((200 * (i + 1)), 0);
            //修改direct的值
            enemyTank.setDirect(2);
            //启动敌人坦克移动的线程
            new Thread(enemyTank).start();


            //给敌方坦克加入子弹，创建shot对象,加入Shot的V集合中
            Shot shot = new Shot(enemyTank.getX() + 20, enemyTank.getY() + 60, enemyTank.getDirect());
            enemyTank.shots.add(shot);
            //启动shot对象
            new Thread(shot).start();

            //将敌方坦克对象加入vector集合中
            enemytanks.add(enemyTank);
        }

        //构造器中初始化图片
        image01 = Toolkit.getDefaultToolkit().getImage("D:\\project_java\\Tankgame01\\out\\production\\" +
                "Tankgame01\\tankgame2\\1.gif");
        image02 = Toolkit.getDefaultToolkit().getImage("D:\\project_java\\Tankgame01\\out\\production\\" +
                "Tankgame01\\tankgame2\\2.gif");
        image03 = Toolkit.getDefaultToolkit().getImage("D:\\project_java\\Tankgame01\\out\\production\\" +
                "Tankgame01\\tankgame2\\3.gif");





    }

//绘画方法

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        //填充矩形，默认为黑色
        g.fillRect(0,0,1000,750);
        //调用方法画出自己的坦克
        if (hero != null && hero.isLive){
            drawTang(hero.getX(),hero.getY(),g,hero.getDirect(),0);
        }else {

//            g.drawString("GameOver",300,325);
//            g.setColor(Color.black);
//            g.setFont(new Font("宋体",Font.BOLD,100));


            try {
                g.drawString("GameOver",300,325);
                g.setColor(Color.black);
                g.setFont(new Font("宋体",Font.BOLD,100));
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            exit(0);

        }



       //画出hero子弹
        if (hero.shot != null && hero.shot.isLive == true){
                g.draw3DRect(hero.shot.x, hero.shot.y,1,1,false);
        }

        //如果我们坦克可以发射多颗子弹
//        for (int i = 0; i < hero.shots.size(); i++) {
//            Shot shot = hero.shots.get(i);
//            if (shot != null && shot.isLive == true){
//                g.draw3DRect(shot.x,shot.y,1,1,false);
//
//            }else{
//                hero.shots.remove(shot);
//            }



        //如果booms中有对象就绘制
        for (int i = 0; i < booms.size(); i++) {
            boom boom = booms.get(i);
            if (boom.life > 40){
                g.drawImage(image01, boom.x,boom.y,60,60,this);
            }else if (boom.life > 20){
                g.drawImage(image02, boom.x,boom.y,60,60,this);
            }else{
                g.drawImage(image03, boom.x,boom.y,60,60,this);
            }
            //减少炸弹的生命
            boom.lifeDown();
            //如果炸弹的生命为0
            if (boom.life == 0){
                booms.remove(boom);
            }
        }

        //画出敌人的坦克，用遍历vector集合

        for (int i = 0; i < enemytanks.size(); i++) {


            EnemyTank enemytank = enemytanks.get(i);

            //还要判断坦克是否活着才能绘制
            if (enemytank.isLive) {
                drawTang(enemytank.getX(), enemytank.getY(), g, enemytank.getDirect(), 1);


            //画出敌人的子弹
            for (int j = 0; j < enemytank.shots.size(); j++) {
                //取出子弹
                Shot shot = enemytank.shots.get(j);
                //绘制子弹
                if (shot.isLive) {
                    g.draw3DRect(shot.x, shot.y, 1, 1, false);
                } else {
                    //不存在则删除shot
                    enemytank.shots.remove(shot);
                }

            }
        }
        }




    }

//定义一个方法画出坦克
    /*
    * x,y 代表横纵坐标
    * g 画笔
    * direct 代表坦克方向
    * type 坦克类型玩家和敌人（不同颜色）
    * 0 ：向上   1 ：向右    2 ：向下    3 ：向左
    * */
    public void drawTang(int x,int y,Graphics g,int direct,int type){
        //根据类型设置颜色
        switch (type){
            case 0://0为玩家
                g.setColor(Color.cyan);
                break;
            case 1://1为敌人
                g.setColor(Color.yellow);
                break;
        }



        //根据方向绘制坦克的形状  0 ：向上   1 ：向右    2 ：向下    3 ：向左

        switch (direct){
            case 0://向上
                g.fill3DRect(x,y,10,60,false);//左边轮子
                g.fill3DRect(x + 30,y,10,60,false);//右边轮子
                g.fill3DRect(x + 10,y+10,20,40,false);//中间
                g.fillOval(x+10,y+20,20,20);//圆盖
                g.drawLine(x+20,y+30,x+20,y);//炮筒

                break;
            case 1://右
                g.fill3DRect(x,y,60,10,false);//左边轮子
                g.fill3DRect(x,y+ 30,60,10,false);//右边轮子
                g.fill3DRect(x + 10,y+10,40,20,false);//中间
                g.fillOval(x+20,y+10,20,20);//圆盖
                g.drawLine(x+30,y+20,x+60,y+20);
                break;

            case 2://向下
                g.fill3DRect(x,y,10,60,false);//左边轮子
                g.fill3DRect(x + 30,y,10,60,false);//右边轮子
                g.fill3DRect(x + 10,y+10,20,40,false);//中间
                g.fillOval(x+10,y+20,20,20);//圆盖
                g.drawLine(x+20,y+30,x+20,y+60);//炮筒

                break;

            case 3://左
                g.fill3DRect(x,y,60,10,false);//左边轮子
                g.fill3DRect(x,y+ 30,60,10,false);//右边轮子
                g.fill3DRect(x + 10,y+10,40,20,false);//中间
                g.fillOval(x+20,y+10,20,20);//圆盖
                g.drawLine(x+30,y+20,x,y+20);
                break;

            default:
                break;

        }
        
    }


    @Override
    public void keyTyped(KeyEvent e) {

    }

    //监听按键按下
    @Override
    public void keyPressed(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_W) {//通过监听键盘输入的键的code的值与w键的code对比，判断是否一致
            hero.setDirect(0);//修改方向
            //修改坐标判断是否到了边界
            if (hero.getY() > 0) {
                hero.moveUp();
            }
        } else if (e.getKeyCode() == KeyEvent.VK_D) {
            hero.setDirect(1);
            if (hero.getX() + 60 < 1000) {
                hero.moveRight();
            }
        } else if (e.getKeyCode() == KeyEvent.VK_S) {
            hero.setDirect(2);
            if (hero.getY() + 60 < 750) {
                hero.moveDown();
            }
        } else if (e.getKeyCode() == KeyEvent.VK_A) {
            hero.setDirect(3);
            if (hero.getX() > 0) {
                hero.moveLeft();
            }
        }
        //键盘监听为J键，执行射击方法
        if (e.getKeyCode() == KeyEvent.VK_J) {
            //判断子弹是否存在，同时保证只有一颗子弹
            if (hero.shot == null ||!hero.shot.isLive){
            hero.shotEnemyTank();
            }

            //hero.shotEnemyTank();
        }

        //让面板重绘
        this.repaint();


    }

     /*
     * 如果我们坦克可以发射多颗子弹
     * 此时判断子弹是否击中敌人需要将我们的子弹集合和
     * 全体敌人进行比较
     *
    public void hitEnemyTank(){
        for (int i = 0; i < hero.shots.size(); i++) {
            Shot shot = hero.shots.get(i);
            if (shot != null && shot.isLive){//自己的有子弹活着
                //遍历敌人所有的坦克
                for (int i = 0; i < enemytanks.size(); i++) {

                    EnemyTank enemytank = enemytanks.get(i);
                    hitTank(shot,enemytank);
                }
            }

        }
    }
*/






    public void hitHero(){
        //取出敌人的所有子弹
        for (int i = 0; i < enemytanks.size(); i++) {
            EnemyTank enemyTank = enemytanks.get(i);
            for (int j = 0; j < enemyTank.shots.size(); j++) {
                //取出敌人的所有子弹
                Shot shot = enemyTank.shots.get(j);
                //进行判断
                if (hero.isLive && shot.isLive){
                    hitTank(shot,hero);
                }
            }
        }
    }


//当子弹击中坦克，坦克消失
    public void hitTank(Shot s,Tank e){
        //判断子弹击中坦克
        switch (e.getDirect()){
            case 0:
            case 2:
                if (s.x > e.getX() && s.x < e.getX() +40 &&
                s.y > e.getY()&&s.y < e.getY() + 60){
                    s.isLive = false;
                    e.isLive = false;
                    //并将坦克从vector中shanc
                    enemytanks.remove(e);

                    //创建boob对象加入boobs
                    boom boom = new boom(e.getX(),e.getY());
                    booms.add(boom);
                }
                break;

            case 1:
            case 3:
                if (s.x > e.getX() && s.x < e.getX() +60 &&
                        s.y > e.getY()&&s.y < e.getY() + 40){
                    s.isLive = false;
                    e.isLive = false;
                    //并将坦克从vector中shanc
                    enemytanks.remove(e);
                    //创建boob对象加入boobs
                    boom boom = new boom(e.getX(),e.getY());
                    booms.add(boom);
                }
                break;

        }

    }

    //按键松开
    @Override
    public void keyReleased(KeyEvent e) {

    }

    //当按下J时子弹出现一次，面板不一直重绘的话子弹的动态轨迹无法显示
    //将MyPanel改为线程，在下一次按下J之前不停运行上一颗子弹的轨迹
    @Override
    public void run() {
        while (true){
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        //判断是否击中了敌人的坦克,要不停的判断，所以放在Run方法中
            if (hero.shot != null && hero.shot.isLive){//自己的有子弹活着
                //遍历敌人所有的坦克
                for (int i = 0; i < enemytanks.size(); i++) {

                    EnemyTank enemytank = enemytanks.get(i);
                    hitTank(hero.shot,enemytank);
                }
            }


            hitHero();

            this.repaint();
        }

    }
}
