

package arkanoid_1;


class Sprite {
  int x,y,width,height;

  Sprite( int X, int Y ,int w, int h )
  {
     x = X;
     y = Y;
     width = w;
     height = h;

  }



  public int getX()
  {
    return x;
  }

  public int getY()
  {
   return y;
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
