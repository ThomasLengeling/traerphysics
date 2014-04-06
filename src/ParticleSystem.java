/*
 * May 29, 2005
 */
package traer.physics;

import java.util.*;

public class ParticleSystem {
	public static final int RUNGE_KUTTA = 0;
	public static final int MODIFIED_EULER = 1;

	protected static final float DEFAULT_GRAVITY = 0;
	protected static final float DEFAULT_DRAG = 0.001f;

	ArrayList<Particle> particles;
	ArrayList<Spring> springs;
	ArrayList<Attraction> attractions;
	ArrayList<Force> customForces = new ArrayList<Force>();

	Integrator integrator;

	Vector3D gravity;
	float drag;

	boolean hasDeadParticles = false;

	/**
	 * Set Integrator Runge Kutta or Euler
	 * 
	 * @param integrator
	 */
	public final void setIntegrator(int integrator) {
		switch (integrator) {
		case RUNGE_KUTTA:
			this.integrator = new RungeKuttaIntegrator(this);
			break;
		case MODIFIED_EULER:
			this.integrator = new ModifiedEulerIntegrator(this);
			break;
		}
	}

	/**
	 * Set Gravity
	 * 
	 * @param x float
	 * @param y float
	 * @param z float
	 */
	public final void setGravity(float x, float y, float z) {
		gravity.set(x, y, z);
	}

	/**
	 * Set Default Gravity Y direction
	 * 
	 * @param g float
	 */
	public final void setGravity(float g) {
		gravity.set(0, g, 0);
	}

	/**
	 * Set Drag
	 * 
	 * @param d float
	 */
	public final void setDrag(float d) {
		drag = d;
	}
	
	/**
	 * Update physics
	 */
	public final void tick() {
		tick(1);
	}
	
	/**
	 * Update physics with step t
	 * @param t float
	 */
	public final void tick(float t) {
		integrator.step(t);
	}

	/**
	 * Make new Particle
	 * @param mass float
	 * @param x float
	 * @param y float
	 * @param z float 
	 * @return Particle
	 */
	public final Particle makeParticle(float mass, float x, float y, float z) {
		Particle p = new Particle(mass);
		p.position().set(x, y, z);
		particles.add(p);
		return p;
	}

	/**
	 * Make new Particle with mass 1 and position (0.0, 0.0, 0.0)
	 * @return Particle
	 */
	public final Particle makeParticle() {
		return makeParticle(1.0f, 0f, 0f, 0f);
	}

	/**
	 * Make new Spring between Particle A and B
	 * @param a Particle
	 * @param b Particle
	 * @param ks float
	 * @param d float 
	 * @param r float
	 * @return Spring
	 */
	public final Spring makeSpring(Particle a, Particle b, float ks, float d,
			float r) {
		Spring s = new Spring(a, b, ks, d, r);
		springs.add(s);
		return s;
	}

	/**
	 * Make Attraction between Particles A and B
	 * @param a Particle
	 * @param b Particle
	 * @param k float
	 * @param minDistance float
	 * @return Attraction
	 */
	public final Attraction makeAttraction(Particle a, Particle b, float k,
			float minDistance) {
		Attraction m = new Attraction(a, b, k, minDistance);
		attractions.add(m);
		return m;
	}

	/**
	 * Clear particles, spring and attractions.
	 */
	public final void clear() {
		particles.clear();
		springs.clear();
		attractions.clear();
	}
	
	/**
	 * Create Particle System
	 * @param g default gravity Y
	 * @param somedrag add Drag
	 */
	public ParticleSystem(float g, float somedrag) {
		integrator = new RungeKuttaIntegrator(this);
		particles = new ArrayList<Particle>();
		springs = new ArrayList<Spring>();
		attractions = new ArrayList<Attraction>();
		gravity = new Vector3D(0, g, 0);
		drag = somedrag;
	}

	/**
	 * Create new Particle System
	 * @param gx float gravity x
	 * @param gy float gravity y
	 * @param gz float gravity z
	 * @param somedrag float
	 */
	public ParticleSystem(float gx, float gy, float gz, float somedrag) {
		integrator = new RungeKuttaIntegrator(this);
		particles = new ArrayList<Particle>();
		springs = new ArrayList<Spring>();
		attractions = new ArrayList<Attraction>();
		gravity = new Vector3D(gx, gy, gz);
		drag = somedrag;
	}

	/**
	 * Create Particle System with default Gravity 0.0 and default Drag 0.001f
	 */
	public ParticleSystem() {
		integrator = new RungeKuttaIntegrator(this);
		particles = new ArrayList<Particle>();
		springs = new ArrayList<Spring>();
		attractions = new ArrayList<Attraction>();
		gravity = new Vector3D(0, ParticleSystem.DEFAULT_GRAVITY, 0);
		drag = ParticleSystem.DEFAULT_DRAG;
	}

	/**
	 * Apply forces to particles
	 */
	protected final void applyForces() {
		if (!gravity.isZero()) {
			for (int i = 0; i < particles.size(); ++i) {
				Particle p = (Particle) particles.get(i);
				p.force.add(gravity);
			}
		}

		for (int i = 0; i < particles.size(); ++i) {
			Particle p = (Particle) particles.get(i);
			p.force.add(p.velocity.x() * -drag, p.velocity.y() * -drag,
					p.velocity.z() * -drag);
		}

		for (int i = 0; i < springs.size(); i++) {
			Spring f = (Spring) springs.get(i);
			f.apply();
		}

		for (int i = 0; i < attractions.size(); i++) {
			Attraction f = (Attraction) attractions.get(i);
			f.apply();
		}

		for (int i = 0; i < customForces.size(); i++) {
			Force f = (Force) customForces.get(i);
			f.apply();
		}
	}

	/**
	 * Clear Forces
	 */
	protected final void clearForces() {
		Iterator i = particles.iterator();
		while (i.hasNext()) {
			Particle p = (Particle) i.next();
			p.force.clear();
		}
	}

	/**
	 * Number of Particles
	 * @return int
	 */
	public final int numberOfParticles() {
		return particles.size();
	}

	/**
	 * Number of Springs
	 * @return int
	 */
	public final int numberOfSprings() {
		return springs.size();
	}

	/**
	 * Number of Attractions
	 * @return int
	 */
	public final int numberOfAttractions() {
		return attractions.size();
	}

	/**
	 * Get Particle 
	 * @param i int index
	 * @return Particle
	 */
	public final Particle getParticle(int i) {
		return (Particle) particles.get(i);
	}

	/**
	 * Get Spring
	 * @param i int index
	 * @return Spring
	 */
	public final Spring getSpring(int i) {
		return (Spring) springs.get(i);
	}

	/**
	 * Get Attraction
	 * @param i int index
	 * @return Attraction
	 */
	public final Attraction getAttraction(int i) {
		return (Attraction) attractions.get(i);
	}

	/**
	 * Add Custom Force
	 * @param f Force
	 */
	public final void addCustomForce(Force f) {
		customForces.add(f);
	}

	/**
	 * Number of Custom Forces
	 * @return int
	 */
	public final int numberOfCustomForces() {
		return customForces.size();
	}

	/**
	 * Get Custom Force
	 * @param i int index
	 * @return Force
	 */
	public final Force getCustomForce(int i) {
		return (Force) customForces.get(i);
	}
	
	/**
	 * Remove Custom Force
	 * @param i int index
	 * @return Force
	 */
	public final Force removeCustomForce(int i) {
		return (Force) customForces.remove(i);
	}

	/**
	 * Remove Particle
	 * @param p Particle
	 */
	public final void removeParticle(Particle p) {
		particles.remove(p);
	}

	/**
	 * Remove Spring
	 * @param i int index
	 * @return Spring
	 */
	public final Spring removeSpring(int i) {
		return (Spring) springs.remove(i);
	}

	/**
	 * Remove Attraction 
	 * @param i int index
	 * @return Attraction
	 */
	public final Attraction removeAttraction(int i) {
		return (Attraction) attractions.remove(i);
	}
	
	/**
	 * Remove Attraction
	 * @param s Attraction
	 */
	public final void removeAttraction(Attraction s) {
		attractions.remove(s);
	}

	/**
	 * Remove Spring
	 * @param a Spring
	 */
	public final void removeSpring(Spring a) {
		springs.remove(a);
	}

	/**
	 * Remove Custom Force
	 * @param f Force
	 */
	public final void removeCustomForce(Force f) {
		customForces.remove(f);
	}

}