package traer.physics;


public class Vector3D
{
	float x;
	float y;
	float z;
	
	/**
	 * 
	 * @param X
	 * @param Y
	 * @param Z
	 */
	public Vector3D( float X, float Y, float Z )	{ x = X; y = Y; z = Z; }
	
	/**
	 * Default x, y, z to zero
	 */
	public Vector3D()                      				{ x = 0; y = 0; z = 0; }
	
	/**
	 * 
	 * @param p Vector3D
	 */
	public Vector3D( Vector3D p )							{ x = p.x; y = p.y; z = p.z; }
	
	/**
	 * get Z Value
	 * @return float Z
	 */
	public final float z()									{ return z; }
	
	/**
	 * get Y Value
	 * @return float Y
	 */
	public final float y()                   				{ return y; }
	
	/**
	 * get X Value
	 * @return float X
	 */
	public final float x()                   				{ return x; }
	
	/**
	 * set X Value
	 * @param X float
	 */
	public final void setX( float X )           			{ x = X; }
	
	/**
	 * set Y Value
	 * @param Y float
	 */
	public final void setY( float Y )           			{ y = Y; }
	
	/**
	 * set Z Value
	 * @param Z float
	 */
	public final void setZ( float Z )						{ z = Z; }
	
	/**
	 * set X, Y, Z Values
	 * @param X float
	 * @param Y float
	 * @param Z float
	 */
	public final void set( float X, float Y, float Z )	{ x = X; y = Y; z = Z; }
	
	/**
	 * set 3D Vector
	 * @param p Vector3D
	 */
	public final void set( Vector3D p )						{ x = p.x; y = p.y; z = p.z; }
	
	/**
	 * Add 3D Vector
	 * @param p Vector3D
	 */
	public final void add( Vector3D p )          				{ x += p.x; y += p.y; z += p.z; }
	
	/**
	 * Subtract 3D Vector
	 * @param p
	 */
	public final void subtract( Vector3D p )					{ x -= p.x; y -= p.y; z -= p.z; }
	
	/**
	 * Add 3D Vector 
	 * @param a float
	 * @param b float
	 * @param c float
	 */
	public final void add( float a, float b, float c )		{ x += a; y += b; z += c; }
	
	/**
	 * Add 3D Vector 
	 * @param a float
	 * @param b float
	 * @param c float
	 */
	public final void subtract( float a, float b, float c )		{ x -= a; y -= b; z -= c; } 
	
	/**
	 * Mutiply by f
	 * @param f 
	 * @return Vector3D
	 */
	public final Vector3D multiplyBy( float f )					{ x *= f; y *= f; z*= f; return this; }
	
	
	/**
	 * Distance to p Vector
	 * @param p Vector3D
	 * @return float Distance
	 */
	public final float distanceTo( Vector3D p )  			{ return (float)Math.sqrt( distanceSquaredTo( p ) ); }
	
	
	/**
	 * Square Distance to Vector p 
	 * @param p Vector3D
	 * @return float Square Distance
	 */
	public final float distanceSquaredTo( Vector3D p )		{ float dx = x-p.x; float dy = y-p.y; float dz = z-p.z; return dx*dx + dy*dy + dz*dz; }
	
	/**
	 * Distance to position (x, y, z)
	 * @param x float
	 * @param y float
	 * @param z float
	 * @return Distance to 
	 */
	public final float distanceTo( float x, float y, float z )
	{
		float dx = this.x - x;
		float dy = this.y - y;
		float dz = this.z - z;
		return (float)Math.sqrt( dx*dx + dy*dy + dz*dz );
	}
	
	/**
	 * Dot product with Vector p
	 * @param p Vector3D
	 * @return float Dot Product
	 */
	public final float dot( Vector3D p )         			{ return x*p.x + y*p.y + z*p.z; }
	
	/**
	 * Length of the vector
	 * @return float length
	 */
	public final float length()                 			{ return (float)Math.sqrt( x*x + y*y + z*z ); }
	
	/**
	 * Squared length of the vector
	 * @return float Squared length
	 */
	public final float lengthSquared()						{ return x*x + y*y + z*z; }
	  
	/**
	 * set x, y, z to zero
	 */
	public final void clear()                   				{ x = 0; y = 0; z = 0; }

	/**
	 * convert vector to string
	 * @return String Vector3D
	 */
	public final String toString()              				{ return new String( "(" + x + ", " + y + ", " + z + ")" ); }

	/**
	 * Cross product with the Vector3D p
	 * @param p Vector3D 
	 * @return Vector3D
	 */
	public final Vector3D cross( Vector3D p )
	{
		return new Vector3D( 	this.y*p.z - this.z*p.y, 
								this.x*p.z - this.z*p.x,
								this.x*p.y - this.y*p.x );
	}
	
	/**
	 * Chech if vector is equal to Zero
	 * @return boolean 
	 */
	public boolean isZero()
	{
		return x == 0 && y == 0 && z == 0;
	}
}
