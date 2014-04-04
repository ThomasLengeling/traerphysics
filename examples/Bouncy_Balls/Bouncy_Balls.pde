import traer.physics.*;

Particle mouse, b, c;
ParticleSystem physics;

void setup()
{
  size( 400, 400 );
  frameRate( 24 );
  smooth();
  ellipseMode( CENTER );
  noStroke();
  noCursor();
  
  physics = new ParticleSystem();
  mouse = physics.makeParticle();
  mouse.makeFixed();
  b = physics.makeParticle( 1.0, random( 0, width ), random( 0, height ), 0 );
  c = physics.makeParticle( 1.0, random( 0, width ), random( 0, height ), 0 );
  
  physics.makeAttraction( mouse, b, 10000, 10 );
  physics.makeAttraction( mouse, c, 10000, 10 );
  physics.makeAttraction( b, c, -10000, 5 );
}

void draw()
{
  mouse.position().set( mouseX, mouseY, 0 );
  handleBoundaryCollisions( b );
  handleBoundaryCollisions( c );
  physics.tick();

  background( 255 );
  
  stroke( 0 );
  noFill();
  ellipse( mouse.position().x(), mouse.position().y(), 35, 35 );

  
  fill( 0 );
  ellipse( b.position().x(), b.position().y(), 35, 35 );
   
  fill( 0 );
  ellipse( c.position().x(), c.position().y(), 35, 35 );
}

// really basic collision strategy:
// sides of the window are walls
// if it hits a wall pull it outside the wall and flip the direction of the velocity
// the collisions aren't perfect so we take them down a notch too
void handleBoundaryCollisions( Particle p )
{
  if ( p.position().x() < 0 || p.position().x() > width )
    p.velocity().set( -0.9*p.velocity().x(), p.velocity().y(), 0 );
  if ( p.position().y() < 0 || p.position().y() > height )
    p.velocity().set( p.velocity().x(), -0.9*p.velocity().y(), 0 );
  p.position().set( constrain( p.position().x(), 0, width ), constrain( p.position().y(), 0, height ), 0 ); 
}
