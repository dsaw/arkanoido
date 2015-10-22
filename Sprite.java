

package arkanoid_1;


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
    return loc.getX();
  }

  public int getY()
  {
   return loc.getY();
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
