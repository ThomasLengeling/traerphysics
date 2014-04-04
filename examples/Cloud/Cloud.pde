import traer.physics.*;

Particle mouse;
Particle[] others;
ParticleSystem physics;

PImage img;

void setup()
{
  size( 400, 400 );
  frameRate( 24 );
  cursor( CROSS );
  
  img = loadImage( "fade.png" );
  imageMode( CORNER );
  tint( 0, 32 );
  
  physics = new ParticleSystem( 0, 0.1 );
  mouse = physics.makeParticle();
  mouse.makeFixed();
  
  others = new Particle[1000];
  for ( int i = 0; i < others.length; i++ )
  {
    others[i] = physics.makeParticle( 1.0, random( 0, width ), random( 0, height ), 0 );
    physics.makeAttraction( mouse, others[i], 5000, 50 ); 
  }
}

void draw()
{
  mouse.position().set( mouseX, mouseY, 0 );
  
  physics.tick();

  background( 255 );
  
  for ( int i = 0; i < others.length; i++ )
  {
     Particle p = others[i];
     image( img,p.position().x()-img.width/2,p.position().y()-img.height/2 );
  }
}
