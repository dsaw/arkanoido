
package arkanoid_1;


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




 class Paddle extends Sprite {

   	

	private final static int DX = 10;
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

    public void reset(int X,int Y)
    {
		dx = 0;
		dy = 0;
	    loc.setLocation(X,Y);
		
		
    }
    
    // Paddle keyEvent methods to process
    
    public void keyPressed( KeyEvent e )
    {
		
		// 1). Left RIght movement
		
		
		switch( e.getKeyCode() )
		{
			
			case KeyEvent.VK_LEFT:
			     dx = -DX;
			     break;
			     
			case KeyEvent.VK_RIGHT:
				 dx = +DX;
				 break;
				 
			default:
				 System.out.println("\nKey Pressed: " + e.getKeyChar()); 
				 break;
			
			
		}
		
		
		
	}
	
	public void printVel()
	{
		System.out.println("dx = "+ dx + " \ndy = " + dy );
		
		
		
		
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
		
		 loc.x += dx;
		  loc.y += dy;
		
	}    

	public void renderItself( Graphics2D g )
	{
		// Set gradient Paint as instance variable
		
	 //	GradientPaint gpPaddle = new GradientPaint(0, 0 ,Color.BROWN , 0,20 , Color.ORANGE ,true );
		
		
	//	g.setColor( Color.BLACK );
	//	g.drawRect( x ,y ,width , height   );

	    g.drawImage(paddleimg , loc.x,loc.y, null);
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
    static int wGame ;
    static int hGame ;
    	
	int dx,dy;
	
	
	
	public Ball(int x, int y,int w , int h )
	{
	    super(x,y,w,h);
		
		
		generateVel();
		
	}
	
	public static void updateScreenSize( int w , int h )
	{
		wGame = w;
		hGame = h;
	}
	
	
	private void  generateVel()
	{
	//	Random rand = new Random();
		dx = VEL_X;// rand.nextInt(40) % 20;
		dy = VEL_Y;// rand.nextInt(40) % 20;
	
	
	}
	
	public void reset()
	{
		
		
		loc.x = INIT_X;
		loc.y = INIT_Y;
		generateVel();
		
		
		
	}
	
	
	
	
	
	public void move()
	{
	  // Simple Wall collision checking
	  // wGame --> width of game
	  // hGame --> height of game
	  if ( loc.y + dy <= 0 )
	    dy = -dy;
	  if ( loc.x + dx <= 0 || loc.x + width +  dx >= wGame )
	    dx = -dx;
	    

	  loc.x += dx;
	  loc.y += dy;
	
	}
	
	public void renderItself( Graphics2D g )
	{
		g.setColor( Color.BLACK );
		g.drawArc( loc.x ,loc.y ,width , height ,0 , 360	 );
		// perform your custom painting operations here
                g.setColor( Color.GRAY );
		g.fillArc( loc.x ,loc.y ,width , height ,0 , 360 );

	}

	public void checkCollision( Paddle p )
	{
	  boolean ifCollided = false;
          if ( p.getX() <= loc.x + dx && loc.x + dx <= p.getX() + p.getWidth()  && p.getY() <= loc.y + height + dy  && loc.y + height + dy <= p.getY() + p.getHeight() )
                  ifCollided = true;
           
          if ( p.getX() <= loc.x+width+dx && loc.x+width+dx  <= p.getX() + p.getWidth()  && p.getY() <=  loc.y+height+dy && loc.y+height + dy <= p.getY() + p.getHeight() )
                 ifCollided = true;

          // reflect velocity if collided 
	  if ( ifCollided)
	  {
	    // lower left corner is above paddle ?
	    if ( loc.y + height < p.getY() )
	      dy = -dy;
	    else
	      dx = -dx;

	   }
	// Check for collision between Sprites : Ball and Paddle
     }


    // if out of the arena --> GAME OVER
    public boolean checkIfOut()
    {
		if ( loc.y + height>= hGame )
		  return true;
		  
		else
			return false;
		
		
	}

 }

	



public class ArkanoidEx extends JPanel implements Runnable  {
	
	
	public static final int CANVAS_WIDTH  = 640;
	public static final int CANVAS_HEIGHT = 480;
	public static final long DELAY = 30; 
	public static final long WAIT =20;
	public static final String TITLE = "ARKANOIDO!!";
	private JButton StartBt ,ExitBt;

	private Thread animator;    
    Ball ball;
    Paddle paddle;

	public static enum GameStates { STARTING , LOADING , PLAYING , MAIN_MENU , GAME_OVER ,EXIT};
	public GameStates game_state;
	
	
	
	
	ArkanoidEx()
	{
		
		game_state = GameStates.STARTING;
		
		
		setPreferredSize( new Dimension(CANVAS_WIDTH , CANVAS_HEIGHT));
        setBackground(Color.BLACK);		
		setDoubleBuffered(true);
	
	    // game object add event listeners	
		// Game initialisation
		addKeyListener( new PaddleKeyAdapter() );

        addMouseListener( new MouseAdapter() { 
							public void mouseClicked(MouseEvent e) 
							{
								if (game_state == GameStates.MAIN_MENU )
							   		
								
						     		
								
								
								paddle.printVel();
								
							}
							
						}); 
		
			
		//CustomPanel game = new CustomPanel(); 
	    //game.setPreferredSize(new Dimension(CANVAS_WIDTH , CANVAS_HEIGHT ) );
	    //add( game );
	    

	    
        //initialise game objects	    
	     Ball.updateScreenSize( CANVAS_WIDTH , CANVAS_HEIGHT );
	    
	    ball = new Ball(300,200,10,10);
	    paddle = new Paddle(320  , CANVAS_HEIGHT - 30 );
        StartBt = new JButton("START");
	  	ExitBt = new JButton("EXIT");
	    
	    // set focus on the game panel
	    setFocusable(true);
	    requestFocusInWindow();
	    
	    // Game objects need to know the dimensions
        //balltask = new BallTask()
	    //timer = new Timer() ;
		//timer.schedule(balltask, WAIT , DELAY);
	}
	
	
        // inner TimerTask override to move and reflect ball
	
	
	 @Override
	 public void addNotify() {
		 super.addNotify();
		 
		 animator = new Thread(this);
		 animator.start();
		 
		 
	 }
	
	
	
	
	
	public void initMenu() 
	{
	  	
	    StartBt.addActionListener( new ButtonActionListener() );
	    ExitBt.addActionListener( new ButtonActionListener() );
	    
	    
	  	add(StartBt);
	  	add(ExitBt);     	
		
	}
	
	
	public void reloadMenu()
	{
		add(StartBt);
	  	add(ExitBt);
	  	//revalidate();
	  	//repaint();
	}
	
	
	private class ButtonActionListener implements ActionListener {
		
		@Override 
		public void actionPerformed( ActionEvent e )
		{
			if ( e.getActionCommand().equals("START") )
			    {
				  game_state = GameStates.PLAYING;
			      
			    }
			
			else if ( e.getActionCommand().equals("EXIT") )
				{
				
				game_state = GameStates.EXIT;
				
			    }
		}
	
	
	}
	
	
 
	
	
	  public void restartGame()
	  {
		ball.reset();
		paddle.reset(320, CANVAS_HEIGHT - 30);
		
		
	    remove(StartBt);
	    remove(ExitBt);	
		
		game_state = GameStates.PLAYING;  
	  }
	  
	
	
	  @Override
	  public void run()
	  {
       long beforeTime, timeDiff, sleep;
       
       // set cur_gamestate and   past variable;
       GameStates prev_game_state = GameStates.STARTING;
       
       
       initMenu();
       
       while ( game_state != GameStates.EXIT )
       {
		   beforeTime = System.currentTimeMillis();   
		   switch( game_state )
	     {
		   case STARTING:
		        
				game_state = GameStates.MAIN_MENU;
				break;
				
		   case  LOADING:
				break;
				
		   case PLAYING:

                if ( prev_game_state == GameStates.MAIN_MENU )
                {
					
				  
				  restartGame();
				  
				  requestFocusInWindow();	
		          			
				}
                
				else
				
				{

					if(ball.checkIfOut())
						game_state = GameStates.GAME_OVER;
					ball.checkCollision( paddle );
					paddle.move();
					ball.move();		   
		        }
		         
				break;
				
		   case MAIN_MENU:
		   
				
                if ( prev_game_state == GameStates.PLAYING || prev_game_state ==  GameStates.GAME_OVER )
                {
					
				  
				  reloadMenu();
				  revalidate();
				  
					
				}
				break;
				
		   case GAME_OVER:
				
				if ( prev_game_state == GameStates.GAME_OVER )
				{
					// sleep and go back to MAIN_MENU
					
					try {
							Thread.sleep(2000);
						}
					catch( InterruptedException e ) 
					    {
							System.out.println("Interrupted:"+e.getMessage() );
					    }
                    game_state =   GameStates.MAIN_MENU;
                    
       
				 }
					
				
		        break; 
		   case EXIT:
			    remove(StartBt);
			    remove(ExitBt);
				
				
			    break;
        
	     }
	     
	     
     repaint();
     
     prev_game_state = game_state;
     timeDiff = System.currentTimeMillis() - beforeTime;
     sleep = DELAY - timeDiff;
        
     if ( sleep < 0 ) {
	      sleep =  2;
		              }
		
	 try {
			Thread.sleep(sleep);
		 }
		 catch( InterruptedException e ) {
			System.out.println("Interrupted:"+e.getMessage() );
				}
       
         }                

	 // if game_state is EXIT
	 System.exit(0);
       
	
	
	
	}
	

		
		
		
	private void drawGameOver( Graphics2D g)
	{
		g.setColor(Color.GRAY );
		g.setFont( new Font("Georgia",Font.BOLD , 30 ) );
		
		FontMetrics fm = g.getFontMetrics();
		
		String msg = "GAME OVER!";
		
		int msgX = getWidth() / 2 - fm.stringWidth(msg) / 2;
		int msgY  = getHeight() / 2 + fm.getAscent() / 2 ;
		
		g.drawString( msg , msgX, msgY );
		
		
		
		
	}
		
		

        

   // Event Adapters are classes
   // Event Listeners are interfaces
   
   // Paddle movement -- > inner Adapter Class
     private class PaddleKeyAdapter extends KeyAdapter {
		 @Override
		 public void keyTyped( KeyEvent e )
		 {
		    
			 
			 repaint();
		 
	     }
		
		
		
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
		 
		 
		 
		 // move the paddle only when event is detected
		
    }


	
	
	
	
	
	
	// RENDERING on this panel
	
	@Override
		public void paintComponent( Graphics g )
		{
			super.paintComponent(g);
			Graphics2D g2d = (Graphics2D) g;
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
			    				RenderingHints.VALUE_ANTIALIAS_ON);
			switch( game_state )
	    {
		   case STARTING:
				
				break;
				
		   case  LOADING:
				break;
				
		   case PLAYING:
		        
				ball.renderItself( g2d );
				paddle.renderItself(g2d);
				break;
				
		   case MAIN_MENU:
		        
		        
				break;
				
		   case GAME_OVER:
		        drawGameOver(g2d );
		        
		        break; 
		   case EXIT:
		   
			    break;
        }
			    				
			    				
		    Toolkit.getDefaultToolkit().sync();	
		}
	
	 	 	
	
	
	
	
	






	public static void main (String args[]) {
		
		// run GUI on Event-Dispatch thread for thread-safety
		
		SwingUtilities.invokeLater(new Runnable() {
         public void run() {
            JFrame frame = new JFrame(TITLE);
            frame.setContentPane(new ArkanoidEx());
            
            // JFrame : top-level container- "setting up" operations
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();             // "this" JFrame packs its components
            frame.setLocationRelativeTo(null); // center the application window
            frame.setVisible(true);            // show it
         }
      });
		
		
	
	
		
	}
	
	
}


