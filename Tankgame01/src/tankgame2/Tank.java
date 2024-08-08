package tankgame2;

public class Tank {
    //定义一个父类坦克
    private int x;//接受x坐标
    private int y;//接受y坐标
    boolean isLive = true;
    private int direct;//坦克方向

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    private int speed = 3;//速度

    public int getDirect() {
        return direct;
    }

    public void setDirect(int direct) {
        this.direct = direct;
    }


    public Tank(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    //将坦克移动的行为封装为方法
    public void moveUp(){
        y -= speed;
    }
    public void moveDown(){
        y += speed;
    }
    public void moveRight(){
        x += speed;
    }
    public void moveLeft(){
        x -= speed;
    }
}
