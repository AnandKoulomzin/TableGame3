import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class BasicGameApp implements Runnable {

    final int WIDTH = 1000;
    final int HEIGHT = 700;

    public JFrame frame;
    public Canvas canvas;
    public JPanel panel;

    public BufferStrategy bufferStrategy;

    public Image deskPic;
    public Image CircleTablePic;
    public Image backgroundPic;
    public Image pingPongTablePic;

    public Table desk;
    public Table CircleTable;
    public Table pingPongTable;


    public static void main(String[] args) {
        BasicGameApp ex = new BasicGameApp();
        new Thread(ex).start();
    }

    public BasicGameApp() {

        setUpGraphics();

        deskPic = Toolkit.getDefaultToolkit().getImage("Table.png");
        desk = new Table("table",800,400);

        CircleTablePic = Toolkit.getDefaultToolkit().getImage("CircleTable.png");
        CircleTable = new Table("CircleTable",400,600);

        pingPongTablePic = Toolkit.getDefaultToolkit().getImage("pingPong.png");
        pingPongTable = new Table("pingPong",400,600);

        backgroundPic = Toolkit.getDefaultToolkit().getImage("emptyRoom5.jpeg");

    }

    public void run() {

        while (true) {
            moveThings();
            crash();
            render();
            pause(20);
        }
    }

    public void moveThings() {
        desk.bounce();
        CircleTable.wrap();
        pingPongTable.bounce();
    }

    public void crash() {
        if (desk.hitBox.intersects(CircleTable.hitBox) && desk.didCrash == false) {
            desk.didCrash=true;
            System.out.println("circleTableCRASH");
            desk.blink=true;
        }

        if (desk.hitBox.intersects(pingPongTable.hitBox) && desk.didCrash == false) {
            desk.didCrash=true;
            System.out.println("pingPongTableCRASH");
            desk.blink=true;
        }
        System.out.println(desk.blinkTimer);

        if (desk.blink==true){
            desk.blinkTimer++;
        }
        if (desk.blinkTimer >= 100) {
            desk.blink = false;
            desk.blinkTimer = 0;
        }

    }



    public void pause(int time ) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
        }
    }

    private void setUpGraphics() {
        frame = new JFrame("Application Template");   //Create the program window or frame.  Names it.
        panel = (JPanel) frame.getContentPane();  //sets up a JPanel which is what goes in the frame
        panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));  //sizes the JPanel
        panel.setLayout(null);   //set the layout

        // creates a canvas which is a blank rectangular area of the screen onto which the application can draw
        // and trap input events (Mouse and Keyboard events)
        canvas = new Canvas();
        canvas.setBounds(0, 0, WIDTH, HEIGHT);
        canvas.setIgnoreRepaint(true);

        panel.add(canvas);  // adds the canvas to the panel.

        // frame operations
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  //makes the frame close and exit nicely
        frame.pack();  //adjusts the frame and its contents so the sizes are at their default or larger
        frame.setResizable(false);   //makes it so the frame cannot be resized
        frame.setVisible(true);      //IMPORTANT!!!  if the frame is not set to visible it will not appear on the screen!

        // sets up things so the screen displays images nicely.
        canvas.createBufferStrategy(2);
        bufferStrategy = canvas.getBufferStrategy();
        canvas.requestFocus();
        System.out.println("DONE graphic setup");
    }

    private void render() {
        Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();
        g.clearRect(0, 0, WIDTH, HEIGHT);

        g.drawImage(backgroundPic, 0,0, WIDTH, HEIGHT,null);

        if (CircleTable.isAlive==true){
            g.drawImage(CircleTablePic, CircleTable.xpos, CircleTable.ypos, CircleTable.width, CircleTable.height, null);
//            g.drawRect(CircleTable.hitBox.x,CircleTable.hitBox.y,CircleTable.hitBox.width,CircleTable.hitBox.height);
        }

        if (pingPongTable.isAlive==true){
            g.drawImage(pingPongTablePic, pingPongTable.xpos, pingPongTable.ypos, pingPongTable.width, pingPongTable.height, null);
//            g.drawRect(pingPongTable.hitBox.x,pingPongTable.hitBox.y,pingPongTable.hitBox.width,pingPongTable.hitBox.height);
        }

        if(desk.blink == true) {
            if((desk.blinkTimer<5) || (10<desk.blinkTimer && desk.blinkTimer<15) || (20<desk.blinkTimer && desk.blinkTimer<25) || (30<desk.blinkTimer && desk.blinkTimer<35) || (40<desk.blinkTimer && desk.blinkTimer<45) || (50<desk.blinkTimer && desk.blinkTimer<55) || (60<desk.blinkTimer && desk.blinkTimer<65) || (70<desk.blinkTimer && desk.blinkTimer<75) || (80<desk.blinkTimer && desk.blinkTimer<85) || (90<desk.blinkTimer && desk.blinkTimer<95)){
                g.drawImage(deskPic, desk.xpos, desk.ypos, desk.width, desk.height, null);
//                g.drawRect(desk.hitBox.x,desk.hitBox.y,desk.hitBox.width,desk.hitBox.height);
                desk.didCrash=false;
            }
        }

        if(desk.blink ==false) {
            g.drawImage(deskPic, desk.xpos, desk.ypos, desk.width, desk.height, null);
//            g.drawRect(desk.hitBox.x,desk.hitBox.y,desk.hitBox.width,desk.hitBox.height);
        }

        g.dispose();
        bufferStrategy.show();
    }
}
