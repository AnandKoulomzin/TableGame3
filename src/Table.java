import java.awt.*;

public class Table {

    //variable declaration section, where I decide what variables to use
    public String name;
    public int xpos;
    public int ypos;
    public int dx;
    public int dy;
    public int width;
    public int height;
    public boolean isAlive;
    public boolean didCrash;
    public Rectangle hitBox;
    public boolean blink;
    public int blinkTimer;
    public boolean wrapping=true;
    public boolean isCrashing;

    //This is a constructor that decides what each variable is equal to
    public Table(String pName, int pXpos, int pYpos) {
        name = pName;
        xpos = pXpos;
        ypos = pYpos;
        dx = (int)(Math.random()*10+1);
        dy = (int)(Math.random()*10+1);
        width = 100;
        height = 100;
        isAlive = true;
        hitBox = new Rectangle (xpos,ypos,width,height);
    }

    public void move() {
        xpos = xpos + dx;
        ypos = ypos + dy;
    }

    //the bounce method is a method that allows a table to bounce off of walls
    public void bounce() {
        xpos = xpos + dx;
        ypos = ypos + dy;

        if (xpos > 1000 - width && dx>0) {
            dx = -dx;
        }

        if ( xpos <= 0) {
            dx = -dx;
        }

        if (ypos > 701 - height) {
            dy = -dy;
        }

        if (ypos <=0) {
            dy=-dy;
        }
        hitBox = new Rectangle(xpos,ypos,width,height);
    }

    //the wrap method is a method that allows tables to teleport across the screen
    public void wrap() {
        xpos = xpos + dx;
        ypos = ypos + dy;

        if (xpos > 1000 && dx > 0) {
            xpos = -width;
        }
        if (xpos < -width && dx < 0) {
            xpos = 1000;
        }
        if (ypos > 700 && dy > 0) {
            ypos = -height;
        }
        if (ypos < -height && dy < 0) {
            ypos = 700;
        }

        hitBox = new Rectangle(xpos,ypos,width,height);
    }
}

