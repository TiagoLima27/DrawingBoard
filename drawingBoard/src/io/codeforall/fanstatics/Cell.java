package io.codeforall.fanstatics;

import org.academiadecodigo.simplegraphics.graphics.Color;
import org.academiadecodigo.simplegraphics.graphics.Rectangle;



public class Cell extends Rectangle{



    int v1;

    int v2;

    int v3;

    int v4;

    Rectangle rectangle;

    boolean isPainted;


    public Cell(int v1, int v2, int v3,int v4) {
        this.v1=v1;
        this.v2=v2;
        this.v3=v3;
        this.v4=v4;
        rectangle = new Rectangle(v1,v2,v3,v4);
        this.rectangle.draw();

    }

    public boolean isPainted() {
        return isPainted = !isPainted;
    }
    public void paint() {
        rectangle.fill();
    }

    public void delete() {
        rectangle.draw();
    }





}
