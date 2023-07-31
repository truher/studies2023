package org.team100.glclib;

public class GlcParameters {
    /**
     * This parameter defines the initial condition for the trajectory optimization
     */
    public double[] x0;
    /**
     * The primary tuning parameter for the algorithm.
     * 
     * The guaranteed property of the algorithm is that if all other parameters are
     * held fixed and the resolution "res" is increased, then the output will
     * converge to an optimal solution. Note that the sequence of solutions
     * generated by calling planner with increasing resolution is not guaranteed to
     * converge in cost monotonically, but it almost always does.
     */
    public int res;
    /**
     * The dimension of the dynamical system's state space
     * 
     * TODO: actually use this for something
     */
    public int state_dim;
    /**
     * The dimension of the control input space
     * 
     * TODO: actually use this for something
     */
    public int control_dim;
    /**
     * An iteration limit for the algorithm
     * 
     * Theoretically, the algorithm is guaranteed to terminate with or without
     * an iteration limit. However, this parameter can be set to prevent out
     * of memory errors. The time complexity of the implementation is
     * max_iter*log(max_iter) while the space complexity is linear with
     * max_iter. A linear time complexity implementation can be achieved
     * by replacing std::set with std::unordered_set.
     */
    public int max_iter;
    /**
     * A constant factor dilation of the forward integration time in each node
     * expansion
     * 
     * The duration that the dynamics are forward simulated must converge to zero
     * with increasing resolution parameter for the formal guarantees to hold.
     * However, we are free to scale whatever function we choose by a constant
     * factor "time_scale". In this implementation the forward simulation time is
     * set to T = time_scale/res.
     */
    public double time_scale;
    /**
     * A constant factor dilation of the equivalence class size
     *
     * The partition size is carefully controlled by the resolution
     * parameter. It will decrease as the resolution increases, and
     * with larger Lipschitz coefficients in the differential
     * constraint the faster the partition must shrink to guaranteed
     * convergence. partition_scale is a constant factor multiplying
     * this function of the Lipschitz constant.
     */
    public double partition_scale;
    /**
     * A constant factor multiplying the depth limit of the search tree
     * 
     * In order to guarantee finite time termination, the algorithm is limited
     * to a finite search depth for a fixed search resolution. This limit is
     * increased with increasing resolution at a carefully controlled rate.
     * The user is free to tune a constant factor.
     */
    public int depth_scale;
    /**
     * Assuming a numerical integration scheme is used, this is the maximum
     * integration step
     */
    public double dt_max;

    /**
     * Prints the parameters to the terminal
     */
    void printParams() {
        System.out.println("state_dim " + state_dim);
        System.out.println("control_dim " + control_dim);
        System.out.println("res " + res);
        System.out.println("max_iter " + max_iter);
        System.out.println("time_scale " + time_scale);
        System.out.println("partition_scale " + partition_scale);
        System.out.println("depth_scale " + depth_scale);
        System.out.println("dt_max_scale " + partition_scale);
        System.out.println("size of x0 " + x0.length);
        return;
    }
}