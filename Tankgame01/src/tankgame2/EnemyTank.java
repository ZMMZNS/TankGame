package tankgame2;

import java.util.Vector;

public class EnemyTank extends Tank implements Runnable{
    //在敌人坦克类中创建一个 Vector类型的shots集合可以保存多个shot（既可以发射多个子弹）
    Vector<Shot> shots = new Vector<>();
    //
    boolean isLive = true;
    public EnemyTank(int x, int y) {
        super(x, y);
    }

    @Override
    public void run() {
        while(true){

            if (isLive && shots.size()<20){
                Shot s = null;
                switch (getDirect()){//创建Shot对象
                    case 0:
                        s = new Shot(getX()+20,getY(),0);
                        break;
                    case 1:
                        s = new Shot(getX()+60,getY()+20,1);
                        break;
                    case 2:
                        s = new Shot(getX()+20,getY()+60,2);
                        break;
                    case 3:
                        s = new Shot(getX(),getY()+20,3);
                        break;

                }
                shots.add(s);
                new Thread(s).start();
            }
            //根据坦克当前方向进行移动
            switch (getDirect()){
                case 0:
                    //敌人每走30步转动方向，每次都休眠有移动动态效果，不休眠就刷的一下移动
                    for (int i = 0; i < 30; i++) {
                        if (getY()>0){
                            moveUp();
                        }
                        ;
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    break;

                case 1:
                    for (int i = 0; i < 30; i++) {
                        if (getX() +60 <1000){
                            moveRight();
                        }

                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    break;
                case 2:
                    for (int i = 0; i < 30; i++) {
                        if (getY()+60<750){
                        moveDown();
                        }
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    break;
                case 3:
                    for (int i = 0; i < 30; i++) {
                        if (getX()>0){
                        moveLeft();
                        }
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                    break;

            }

            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            //随机改变坦克方向
            setDirect((int)(Math.random() * 4));

            //结束线程的条件
            if (!isLive){
                break;
            }


        }
    }
}
