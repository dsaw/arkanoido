



import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.awt.geom.*;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;


//import Arkanoido.Sprite;
//
//

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




 class Paddle extends Sprite {

   	

	private final static int DX = 5;
	private final static int DY = 0;
    private Image paddleimg;
    private int dx,dy;
    boolean keypressed;

	public Paddle(int X, int Y)
	{
		super(X,Y,0,0);
		keypressed = false;
        loadImage();
		setSize();

		generateVel();
		
	}
		
	void loadImage()
	{

	  paddleimg = new ImageIcon("res/paddle.gif").getImage();
	}

        void setSize()
	{
	  width = paddleimg.getWidth(null);
	  height = paddleimg.getHeight(null);
         
	  

	}

    
    
    // Paddle keyEvent methods to process
    
    public void keyPressed( KeyEvent e )
    {
		
		// 1). Left RIght movement
		keypressed = true;
		
		switch( e.getKeyCode() )
		{
			
			case KeyEvent.VK_LEFT:
			     dx = -DX;
			     
			     break;
			case KeyEvent.VK_RIGHT:
				 dx = +DX;
				 break;
				 
			default:
				 System.out.println("\nKey Pressed"); 
				 break;
			
			
		}
		
		
		
	}
	
	
	public void keyReleased( KeyEvent e )
	{
		// Stop movement
		keypressed = false;
		switch( e.getKeyCode() )
		{
			case KeyEvent.VK_LEFT:
			     dx = 0;
			     break;
			case KeyEvent.VK_RIGHT:
				 dx = 0;
				 break;
			
		}
		
		
		
	}

	void generateVel()
	{
		dx = 0;
		dy = 0;
	}

    public void move()
    {
		if (keypressed)
		{ x += dx;
		  y += dy;
		}
	}    

	public void renderItself( Graphics2D g )
	{
		// Set gradient Paint as instance variable
		
	 //	GradientPaint gpPaddle = new GradientPaint(0, 0 ,Color.BROWN , 0,20 , Color.ORANGE ,true );
		
		
	//	g.setColor( Color.BLACK );
	//	g.drawRect( x ,y ,width , height   );

	    g.drawImage(paddleimg , x,y, null);
		// perform your custom painting operations here
	//	g.setPaint( gpPaddle );
//		g.fillRect( x ,y ,width , height  );

	}



	}

 class Ball extends Sprite {
	

	static final int INIT_X = 300;
	static final int INIT_Y = 200;
	static final int VEL_X = 5;
	static final int VEL_Y = -5;

	
	int dx,dy;
	
	
	
	public Ball(int x, int y,int w , int h )
	{
	        super(x,y,w,h);
		
		generateVel();
		
	}
	
	private void  generateVel()
	{
	//	Random rand = new Random();
		dx = VEL_X;// rand.nextInt(40) % 20;
		dy = VEL_Y;// rand.nextInt(40) % 20;
	
	
	}
	
	public void move(int wGame , int hGame)
	{
	  // Simple Wall collision checking
	  // wGame --> width of game
	  // hGame --> height of game
	  if ( y + dy <= 0 || y + height + dy >= hGame )
	    dy = -dy;
	  if ( x + dx <= 0 || x + width +  dx >= wGame )
	    dx = -dx;
	    

	  x += dx;
	  y += dy;
	
	}
	
	public void renderItself( Graphics2D g )
	{
		g.setColor( Color.BLACK );
		g.drawArc( x ,y ,width , height ,0 , 360	 );
		// perform your custom painting operations here
                g.setColor( Color.RED );
		g.fillArc( x ,y ,width , height ,0 , 360 );

	}

	public void checkCollision( Paddle p )
	{
	  boolean ifCollided = false;
          if ( p.getX() <= x + dx && x + dx <= p.getX() + p.getWidth()  && p.getY() <= y + height + dy  && y + height + dy <= p.getY() + p.getHeight() )
                  ifCollided = true;
           
          if ( p.getX() <= x+width+dx && x+width+dx  <= p.getX() + p.getWidth()  && p.getY() <=  y+height+dy && y+height + dy <= p.getY() + p.getHeight() )
                 ifCollided = true;

          // reflect velocity if collided 
	  if ( ifCollided)
	  {
	    // lower left corner is above paddle ?
	    if ( y + height < p.getY() )
	      dy = -dy;
	    else
	      dx = -dx;

	}
	// Check for collision between Sprites : Ball and Paddle
     }

 }

public class SwingTemplate extends JPanel  {
	
	
	public static final int CANVAS_WIDTH  = 640;
	public static final int CANVAS_HEIGHT = 480;
	public static final long DELAY =30; 
	public static final long WAIT =20;
	public static final String TITLE = " Damn Window";
	Ball ball;
        Timer timer;
	BallTask balltask;
        Paddle paddle;

	SwingTemplate()
	{
		setPreferredSize( new Dimension(CANVAS_WIDTH + 10 , CANVAS_HEIGHT + 10 ));
        setBackground(Color.BLACK);		
		// Set size and other attributes
		
		// Sets Layout 
		
		
		// Allocate GUI components
		
		// Add to container these components
		
		// source object add event listeners
		CustomPanel game = new CustomPanel(); 
		setDoubleBuffered(true);
                // init game res
	    game.setPreferredSize(new Dimension(CANVAS_WIDTH , CANVAS_HEIGHT ) );
	    ball = new Ball(300,200,20,20);
	    paddle = new Paddle(320  , CANVAS_HEIGHT - 30 );

        // game object add event listeners
        addKeyListener( new PaddleKeyAdapter() );

		requestFocus();
		add( game );
		
		

        balltask = new BallTask();
	    timer = new Timer() ;
		timer.schedule(balltask, WAIT , DELAY);
	}
	
	
        // inner TimerTask override to move and reflect ball
	private class BallTask extends TimerTask {
	  @Override
	  public void run()
	  {

	   ball.checkCollision( paddle );
	   ball.move(CANVAS_WIDTH , CANVAS_HEIGHT);
	   paddle.move();	
	   repaint();

          }          

        }

   // Event Adapters are classes
   // Event Listeners are interfaces
   
   // Paddle movement -- > inner Adapter Class
     private class PaddleKeyAdapter extends KeyAdapter {
		 @Override
		 public void keyPressed( KeyEvent e )
		 {
			 paddle.keyPressed( e);
			 
			 
		 }
		 
		 @Override
		 public void keyReleased( KeyEvent e )
		 {
			 paddle.keyReleased(e);
			 
		 }
		 
    }


	// Custom overriding painting inner class for this JPanel
	
	private class CustomPanel extends JPanel {
	
	@Override
		public void paintComponent( Graphics g )
		{
			super.paintComponent(g);
			Graphics2D g2d = (Graphics2D) g;
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
			    				RenderingHints.VALUE_ANTIALIAS_ON);
		    ball.renderItself( g2d );
			paddle.renderItself(g2d);
		    Toolkit.getDefaultToolkit().sync();	
		}
	
	}
	






	public static void main (String args[]) {
		
		// run GUI on Event-Dispatch thread for thread-safety
		
		SwingUtilities.invokeLater(new Runnable() {
         public void run() {
            JFrame frame = new JFrame(TITLE);
            frame.setContentPane(new SwingTemplate());
            
            // JFrame : top-level container- "setting up" operations
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();             // "this" JFrame packs its components
            frame.setLocationRelativeTo(null); // center the application window
            frame.setVisible(true);            // show it
         }
      });
		
		
	
	
		
	}
	
	
}


