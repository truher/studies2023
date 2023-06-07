package org.team100.lib.math;

import edu.wpi.first.math.Matrix;
import edu.wpi.first.math.Num;
import edu.wpi.first.math.numbers.N1;

/**
 * A vector-valued gaussian random variable.
 */
public class RandomVector<States extends Num> {
    public final Matrix<States, N1> x;
    public final Matrix<States, States> P;

    public RandomVector(Matrix<States, N1> x, Matrix<States, States> P) {
        this.x = x;
        this.P = P;
    }

    public final RandomVector<States> copy() {
        return new RandomVector<>(x.copy(), P.copy());
    }

    public RandomVector<States> minus(RandomVector<States> b) {
        // note assumption of independence
        return new RandomVector<>(x.minus(b.x), P.plus(b.P));
    }

    public RandomVector<States> plus(RandomVector<States> b) {
        // note assumption of independence
        return new RandomVector<>(x.plus(b.x), P.plus(b.P));
    }

    public RandomVector<States> times(double d) {
        // note square
        return new RandomVector<>(x.times(d), P.times(d * d));
    }
}
