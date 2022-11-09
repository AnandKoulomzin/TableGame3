public class Table {

    public String name;               //name of the hero
    public int xpos;                  //the x position
    public int ypos;                  //the y position
    public int dx;                    //the speed of the hero in the x direction
    public int dy;                    //the speed of the hero in the y direction
    public int width;                 //the width of the hero image
    public int height;                //the height of the hero image
    public boolean isAlive;           //a boolean to denote if the hero is alive or dead


    //This is a constructor that takes 3 parameters.
    // This allows us to specify the hero's name and position when we build it.
    public Table(String pName, int pXpos, int pYpos) { // Astronaut constructor
        name = pName;
        xpos = pXpos;
        ypos = pYpos;
        dx = 10;
        dy = 0;
        width = 100;
        height = 100;
        isAlive = true;

    } // end Table constructor

    //The move method.  Everytime this is run (or "called") the hero's x position and y position change by dx and dy
    public void move() { // move
        xpos = xpos + dx;
        ypos = ypos + dy;
    } // end move

    public void bounce() {
        xpos = xpos + dx;
        ypos = ypos + dy;

        if (xpos >= 1000 - width || xpos <= 0) { //right or left wall
            dx = -dx;
        }
    }

    public void wrap() {
        xpos = xpos + dx;
        ypos = ypos + dy;

        if (xpos >= 1000 - width){
            xpos=0;
        }
    }
}