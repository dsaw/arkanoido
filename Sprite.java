

package arkanoid_1;

import java.awt.Point;
import java.awt.Shape;
// Use java.awt.Point class

class Sprite {
  int x,y,width,height;
  Point loc;
  
  Sprite( int X, int Y ,int w, int h )
  {
     loc = new Point(X,Y);
     width = w;
     height = h;

  }



  public int getX()
  {
    return loc.x;
  }

  public int getY()
  {
   return loc.y;
  }

  public int getWidth()
  {
    return width;
   }

  public int getHeight()
{

    return height;
   }
  
  
  boolean checkCollision( Sprite spr )

  {
    // simple bounding box collision
    
    
    


    return true;

  }

}
