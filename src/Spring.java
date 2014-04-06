/*
 * May 29, 2005
 */

package traer.physics;

/**
 * @author jeffrey traer bernstein
 *
 */
public class Spring implements Force
{
	
  float springConstant;
  float damping;
  float restLength;
  Particle a, b;
  boolean on;
    
  /**
   * 
   * @param A Particle A
   * @param B Particle B
   * @param ks float springConstant
   * @param d float damping
   * @param r float restLength
   */
  public Spring( Particle A, Particle B, float ks, float d, float r )
  {
    springConstant = ks;
    damping = d;
    restLength = r;
    a = A;
    b = B;
    on = true;
  }
  
  /**
   * Turn off Spring
   */
  public final void turnOff()
  {
	  on = false;
  }
  
  /**
   * Turn on Spring
   */
  public final void turnOn()
  {
	  on = true;
  }
  
  /**
   * check if On
   * @return boolean 
   */
  public final boolean isOn()
  {
	  return on;
  }
  
  /**
   * check if off
   * @return boolean
   */
  public final boolean isOff()
  {
	  return !on;
  }
  
  /**
   * Get Particle A
   * @return Particle 
   */
  public final Particle getOneEnd()
  {
	  return a;
  }
  
  /**
   * Get Particle B
   * @return Particle
   */
  public final Particle getTheOtherEnd()
  {
	  return b;
  }
  
  /**
   * get Current length of Spring
   * @return float
   */
  public final float currentLength()
  {
	  return a.position().distanceTo( b.position() );
  }
  
  /**
   * get restLength
   * @return float
   */
  public final float restLength()
  {
	  return restLength;
  }
  
  /**
   * get springConstant 
   * @return float
   */
  public final float strength()
  {
	  return springConstant;
  }
  
  /**
   *  set springConstant
   * @param ks float
   */
  public final void setStrength( float ks )
  {
	  springConstant = ks;
  }
  
  /**
   * get Damping
   * @return float
   */
  public final float damping()
  {
	  return damping;
  }
  
  /**
   * set Damping
   * @param d float
   */
  public final void setDamping( float d )
  {
	  damping = d;
  }
  
  /**
   * set setRestLength
   * @param l float
   */
  public final void setRestLength( float l )
  {
	  restLength = l;
  }
  
  /**
   * apply Spring
   */
  public final void apply()
  {	
	if ( on && ( a.isFree() || b.isFree() ) )
	{
		float a2bX = a.position().x - b.position().x;
		float a2bY = a.position().y - b.position().y;
		float a2bZ = a.position().z - b.position().z;
		
		float a2bDistance = (float)Math.sqrt( a2bX*a2bX + a2bY*a2bY + a2bZ*a2bZ );
		
		if ( a2bDistance == 0 )
		{
			a2bX = 0;
			a2bY = 0;
			a2bZ = 0;
		}
		else
		{
			a2bX /= a2bDistance;
			a2bY /= a2bDistance;
			a2bZ /= a2bDistance;
		}
		
	
		// spring force is proportional to how much it stretched 
		
		float springForce = -( a2bDistance - restLength ) * springConstant; 
		
		
		// want velocity along line b/w a & b, damping force is proportional to this
		
		float Va2bX = a.velocity().x - b.velocity().x;
		float Va2bY = a.velocity().y - b.velocity().y;
		float Va2bZ = a.velocity().z - b.velocity().z;
		               		
		float dampingForce = -damping * ( a2bX*Va2bX + a2bY*Va2bY + a2bZ*Va2bZ );
		
		
		// forceB is same as forceA in opposite direction
		
		float r = springForce + dampingForce;
		
		a2bX *= r;
		a2bY *= r;
		a2bZ *= r;
	    
		if ( a.isFree() )
			a.force().add( a2bX, a2bY, a2bZ );
		if ( b.isFree() )
			b.force().add( -a2bX, -a2bY, -a2bZ );
	}
  }

  /**
   * set new Particle A
   * @param p Particle
   */
  protected void setA( Particle p )
  {
	  a = p;
  }
  
  /**
   * set new Particle B
   * @param p Particle
   */
  protected void setB( Particle p )
  {
	  b = p;
  }
}