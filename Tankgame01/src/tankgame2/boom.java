package tankgame2;

public class boom {
    int x,y;
    //生命周期
    int life = 60;
    boolean isLive = true;

    public boom(int x, int y) {
        this.x = x;
        this.y = y;
    }
    //通过减少炸弹的生命周期，来显示不同的图片，显示出一种爆炸的效果
    public void lifeDown(){
        if (life > 0){
            life--;
        }else {
            isLive = false;
        }
    }

}
