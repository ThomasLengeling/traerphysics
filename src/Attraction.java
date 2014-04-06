// attract positive repel negative

package traer.physics;

public class Attraction implements Force
{
	Particle a;
	Particle b;
	float k;
	boolean on;
	float distanceMin;
	float distanceMinSquared;
	
	/**
	 * @param a Particle A
	 * @param b Particle B
	 * @param k Strength
	 * @param distanceMin Minimun Distance
	 */
	
	public Attraction( Particle a, Particle b, float k, float distanceMin )
	{
		this.a = a;
		this.b = b;
		this.k = k;
		on = true;
		this.distanceMin = distanceMin;
		this.distanceMinSquared = distanceMin*distanceMin;
	}

	/**
	 * Set Particle A
	 * @param p Particle A
	 */
	protected void setA( Particle p )
	{
		a = p;
	}
	
	/**
	 * Set Particle B
	 * @param p Particle B
	 */
	protected void setB( Particle p )
	{
		b = p;
	}

	/**
	 * get Minimun Distance
	 * @return float
	 */
	public final float getMinimumDistance()
	{
		return distanceMin;
	}

	/**
	 * set Minimun Distance
	 * @param d 
	 */
	public final void setMinimumDistance( float d )
	{
		distanceMin = d;
		distanceMinSquared = d*d;
	}
	
	/**
	 *  Turn off Attraction
	 */
	public final void turnOff()
	{
		on = false;
	}

	/**
	 * Turn on Attraction
	 */
	public final void turnOn()
	{
		on = true;
	}

	/**
	 * Set Strength
	 * @param k set Strength float
	 */
	public final void setStrength( float k )
	{
		this.k = k;
	}
	
	/**
	 * get Particle A
	 * @return particle A
	 */
	public final Particle getOneEnd()
	{
		return a;
	}

	/**
	 * get Particle B
	 * @return particle B
	 */
	public final Particle getTheOtherEnd()
	{
		return b;
	}

	/**
	 * apply attraction
	 */
	public void apply()
	{
		if ( on && ( a.isFree() || b.isFree() ) )
		{
			float a2bX = a.position().x() - b.position().x();
			float a2bY = a.position().y() - b.position().y();
			float a2bZ = a.position().z() - b.position().z();

			float a2bDistanceSquared = a2bX*a2bX + a2bY*a2bY + a2bZ*a2bZ;

			if ( a2bDistanceSquared < distanceMinSquared )
				a2bDistanceSquared = distanceMinSquared;

			float force = k * a.mass * b.mass / a2bDistanceSquared;

			float length = (float)Math.sqrt( a2bDistanceSquared );
			
			// make unit vector
			
			a2bX /= length;
			a2bY /= length;
			a2bZ /= length;
			
			// multiply by force 
			
			a2bX *= force;
			a2bY *= force;
			a2bZ *= force;

			// apply
			
			if ( a.isFree() )
				a.force().add( -a2bX, -a2bY, -a2bZ );
			if ( b.isFree() )
				b.force().add( a2bX, a2bY, a2bZ );
		}
	}

	/**
	 * get Strength
	 * @return float Strength
	 */
	public final float getStrength()
	{
		return k;
	}
	
	/** check if attraction is on
	 * @return boolean on
	 */
	public final boolean isOn()
	{
		return on;
	}

	/** check if attraction is off
	 * @return boolean !on
	 */
	public final boolean isOff()
	{
		return !on;
	}
}
