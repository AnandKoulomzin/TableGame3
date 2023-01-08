import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.event.*;

public class BasicGameApp implements Runnable, KeyListener {

// this is the variable definition section. this section declares the variables in the program

    final int WIDTH = 1000;
    final int HEIGHT = 700;

    public JFrame frame;
    public Canvas canvas;
    public JPanel panel;

    public BufferStrategy bufferStrategy;

    public Image deskPic;
    public Image circleTablePic;
    public Image backgroundPic;
    public Image pingPongTablePic;

    // this declares the tables used in the program
    public Table desk;
    public Table circleTable;
    public Table pingPongTable;

//method definition; the part that actually runs the code
    public static void main(String[] args) {
        BasicGameApp ex = new BasicGameApp();
        new Thread(ex).start();
    }

    // the set-up section initializes the variables and constructs the tables
    public BasicGameApp() {

        setUpGraphics();

        canvas.addKeyListener(this);

        deskPic = Toolkit.getDefaultToolkit().getImage("Table.png");
        desk = new Table("table",800,400);

        circleTablePic = Toolkit.getDefaultToolkit().getImage("circleTable.png");
        circleTable = new Table("circleTable",400,600);

        pingPongTablePic = Toolkit.getDefaultToolkit().getImage("pingPong.png");
        pingPongTable = new Table("pingPong",400,200);

        backgroundPic = Toolkit.getDefaultToolkit().getImage("emptyRoom5.jpeg");

    }

    // the code that runs once the game starts
    public void run() {

        while (true) {
            moveThings();
            crash();
            render();
            pause(20);
        }
    }

    //
    public void moveThings() {
        desk.bounce();
        circleTable.move();
        if (pingPongTable.wrapping==false) {
            pingPongTable.bounce();
        }
        if (pingPongTable.wrapping==true) {
            pingPongTable.wrap();
        }
    }
// this is the crash method; whenever a table crashes this method is used to determine what happens to the tables
    public void crash() {

        if(!desk.hitBox.intersects(circleTable.hitBox)){
            desk.isCrashing=false;
        }

        if (desk.hitBox.intersects(circleTable.hitBox) && desk.isCrashing==false) {
            desk.blink=true;
            desk.isCrashing=true;
            System.out.println("circleTableCRASH");
        }

        if(!desk.hitBox.intersects(circleTable.hitBox)){
            desk.isCrashing=false;
        }

        if(!desk.hitBox.intersects(pingPongTable.hitBox)){
            desk.isCrashing=false;
        }

        if (desk.hitBox.intersects(pingPongTable.hitBox) && desk.isCrashing==false && pingPongTable.isCrashing==false) {
            desk.blink = true;
            desk.isCrashing=true;
            System.out.println("pingPongTableCRASH");
        }

        if(!desk.hitBox.intersects(pingPongTable.hitBox)){
            desk.isCrashing=false;
        }

        if(!pingPongTable.hitBox.intersects(circleTable.hitBox)){
            pingPongTable.isCrashing=false;
        }

        if (pingPongTable.hitBox.intersects(circleTable.hitBox) && pingPongTable.wrapping==false && pingPongTable.isCrashing==false) {
            pingPongTable.wrapping=true;
            pingPongTable.isCrashing=true;
            System.out.println("wrapping true");
        }

        if(!pingPongTable.hitBox.intersects(circleTable.hitBox)){
            pingPongTable.isCrashing=false;
        }


        if (pingPongTable.hitBox.intersects(circleTable.hitBox) && pingPongTable.wrapping==true && pingPongTable.isCrashing==false) {
            pingPongTable.wrapping = false;
            pingPongTable.isCrashing = true;
            System.out.println("wrapping false");
        }

        if(!pingPongTable.hitBox.intersects(circleTable.hitBox)){
            pingPongTable.isCrashing=false;
            circleTable.isCrashing=false;
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

    // the time that the code pauses or "sleeps" (ms)
    public void pause(int time ) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
        }
    }

    //this sets up the graphics

    //paints the actual images on the screen with bufferStrategy
    private void render() {
        Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();
        g.clearRect(0, 0, WIDTH, HEIGHT);

        g.drawImage(backgroundPic, 0,0, WIDTH, HEIGHT,null);

        if (circleTable.isAlive){
            g.drawImage(circleTablePic, circleTable.xpos, circleTable.ypos, circleTable.width, circleTable.height, null);
//            g.drawRect(CircleTable.hitBox.x,CircleTable.hitBox.y,CircleTable.hitBox.width,CircleTable.hitBox.height);
        }

        if (pingPongTable.isAlive){
            g.drawImage(pingPongTablePic, pingPongTable.xpos, pingPongTable.ypos, pingPongTable.width, pingPongTable.height, null);
//            System.out.println("xpos:" + pingPongTable.xpos + "ypos:" + pingPongTable.ypos);
//            g.drawRect(pingPongTable.hitBox.x,pingPongTable.hitBox.y,pingPongTable.hitBox.width,pingPongTable.hitBox.height);
        }

        if(desk.blink==true) {
            if((desk.blinkTimer<5) || (10<desk.blinkTimer && desk.blinkTimer<15) || (20<desk.blinkTimer && desk.blinkTimer<25) || (30<desk.blinkTimer && desk.blinkTimer<35) || (40<desk.blinkTimer && desk.blinkTimer<45) || (50<desk.blinkTimer && desk.blinkTimer<55) || (60<desk.blinkTimer && desk.blinkTimer<65) || (70<desk.blinkTimer && desk.blinkTimer<75) || (80<desk.blinkTimer && desk.blinkTimer<85) || (90<desk.blinkTimer && desk.blinkTimer<95)){
                g.drawImage(deskPic, desk.xpos, desk.ypos, desk.width, desk.height, null);
//                g.drawRect(desk.hitBox.x,desk.hitBox.y,desk.hitBox.width,desk.hitBox.height);
            }
        }

        if(desk.blink==false) {
            g.drawImage(deskPic, desk.xpos, desk.ypos, desk.width, desk.height, null);
//            g.drawRect(desk.hitBox.x,desk.hitBox.y,desk.hitBox.width,desk.hitBox.height);
        }

        g.dispose();
        bufferStrategy.show();
    }

    public void keyPressed(KeyEvent event) {
        //This method will do something whenever any key is pressed down.
        //Put if( ) statements here
        char key = event.getKeyChar();     //gets the character of the key pressed
        int keyCode = event.getKeyCode();  //gets the keyCode (an integer) of the key pressed
        System.out.println("Key Pressed: " + key + "  Code: " + keyCode);

        if (keyCode == 68) { // d
            circleTable.right = true;
        }
        if (keyCode == 83) { // s
            circleTable.down = true;
        }
        if (keyCode == 65) {
            circleTable.left = true; // a
        }
        if(keyCode == 87){
            circleTable.up = true; // w
        }
    }//keyPressed()

    public void keyReleased(KeyEvent event) {
        char key = event.getKeyChar();
        int keyCode = event.getKeyCode();
        //This method will do something when a key is released
        if (keyCode == 68) {
            circleTable.right = false;
        }
        if (keyCode == 83) {
            circleTable.down = false;
        }
        if (keyCode == 65) {
            circleTable.left = false; // a
        }
        if(keyCode == 87){
            circleTable.up = false; // w
        }

    }//keyReleased()

    public void keyTyped(KeyEvent event) {
        // handles a press of a character key (any key that can be printed but not keys like SHIFT)
        // we won't be using this method, but it still needs to be in your program
    }//keyTyped()


    //Graphics setup method
    public void setUpGraphics() {
        frame = new JFrame("CheeseWorld");   //Create the program window or frame.  Names it.

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
}
