package tankgame2;
/*
* 将子弹射击作为一个线程
* 定义子弹的属性，行为
* 以及销毁的方法
* 触碰边界或达到敌人
* */
public class Shot implements Runnable{
    int x;
    int y;
    int direct = 0;
    int speed = 15;
    boolean isLive = true;

    public Shot(int x, int y, int direct) {
        this.x = x;
        this.y = y;
        this.direct = direct;
    }

    @Override
    public void run() {//

        //while循环
        while (true){

            //休眠线程达到动态移动效果
            try {
                Thread.sleep(500);//这里会抛出一个异常，要捕获异常
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            //根据方向改变xy坐标
            switch (direct){
                case 0:
                    y -= speed;
                    break;
                case 1:
                    x += speed;
                    break;
                case 2:
                    y += speed;
                    break;
                case 3:
                    x -= speed;
                    break;
            }
            //当子弹移动的边界销毁子弹即结束线程
            //当子弹碰到敌人坦克时结束线程
            if (!(x>=0 && x<= 1000 && y>=1 && y<=750 && isLive)){
                isLive = false;
                break;
            }



        }

    }
}
