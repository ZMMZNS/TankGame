package tankgame2;


import java.util.Vector;

public class Hero extends Tank {
    //定义一个Shot对象，表示一个线程
    Shot shot = null;
    //Vector<Shot> shots = new Vector<>();


    public Hero(int x, int y) {
        super(x, y);
    }

    //定义射击方法
    public void shotEnemyTank(){
        //创建Shot对象
        //饰面板上有5课子弹
//        if (shots.size() == 5){
//            return;
//        }
        //上右下左
        switch (getDirect()){//创建Shot对象
            case 0:
                shot = new Shot(getX()+20,getY(),0);
                break;
            case 1:
                shot = new Shot(getX()+60,getY()+20,1);
                break;
            case 2:
                shot = new Shot(getX()+20,getY()+60,2);
                break;
            case 3:
                shot = new Shot(getX(),getY()+20,3);
                break;

        }

        //启动线程
        //shots.add(shot);
        new Thread(shot).start();
    }




}
