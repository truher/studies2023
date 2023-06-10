package org.team100.lib.estimator;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;
import org.team100.lib.math.RandomVector;

import edu.wpi.first.math.Matrix;
import edu.wpi.first.math.Nat;
import edu.wpi.first.math.VecBuilder;
import edu.wpi.first.math.numbers.N1;
import edu.wpi.first.math.numbers.N2;

public class IntegratingPredictorTest {
    private static final double kDelta = 0.001;

    static RandomVector<N1> v1(double x, double P) {
        Matrix<N1, N1> xV = VecBuilder.fill(x);
        Matrix<N1, N1> PV = VecBuilder.fill(P);
        return new RandomVector<>(xV, PV);
    }

    static void assert1(RandomVector<N1> v, double x, double P) {
        assertArrayEquals(new double[] { x }, v.x.getData(), kDelta);
        assertArrayEquals(new double[] { P }, v.P.getData(), kDelta);
    }

    /** xdot = 0 */
    public RandomVector<N1> f1Zero(RandomVector<N1> x, Matrix<N1, N1> u) {
        return new RandomVector<>(VecBuilder.fill(0), VecBuilder.fill(1));
    }

    /** xdot = x */
    public RandomVector<N1> f1X(RandomVector<N1> x, Matrix<N1, N1> u) {
        Matrix<N1, N1> p = new Matrix<>(Nat.N1(), Nat.N1());
        p.set(0, 0, 1);
        return new RandomVector<>(x.x, p);
        // return x;
    }

    @Test
    public void testZero() {
        IntegratingPredictor<N1, N1> p = new IntegratingPredictor<N1, N1>();
        Matrix<N1, N1> u = VecBuilder.fill(0);
        double dtS = 1;
        // if xdot is zero then the prediction is always the same as the input
        {
            RandomVector<N1> x = v1(0, 0);
            RandomVector<N1> v = p.predict(this::f1Zero, x, u, dtS);
            assert1(v, 0, 0.278);
        }
        {
            RandomVector<N1> x = v1(1, 1);
            RandomVector<N1> v = p.predict(this::f1Zero, x, u, dtS);
            assert1(v, 1, 1.278);
        }
        {
            RandomVector<N1> x = v1(2, 2);
            RandomVector<N1> v = p.predict(this::f1Zero, x, u, dtS);
            assert1(v, 2, 2.278);
        }
    }

    @Test
    public void testX() {
        IntegratingPredictor<N1, N1> p = new IntegratingPredictor<N1, N1>();
        Matrix<N1, N1> u = VecBuilder.fill(0);
        double dtS = 1;
        {
            // if x is zero, then nothing changes
            RandomVector<N1> x = v1(0, 0);
            RandomVector<N1> v = p.predict(this::f1X, x, u, dtS);
            assert1(v, 0, 0.278);
        }
        {
            // if x is one, then the prediction should be e, and it's pretty close.
            RandomVector<N1> x = v1(1, 1);
            RandomVector<N1> v = p.predict(this::f1X, x, u, dtS);
            assert1(v, 2.708, 1.278); // should be 2.718
        }
        {
            RandomVector<N1> x = v1(2, 2);
            RandomVector<N1> v = p.predict(this::f1X, x, u, dtS);
            assert1(v, 5.417, 2.278);
        }
    }


    public RandomVector<N1> f1(RandomVector<N1> x, Matrix<N1, N1> u) {
        Matrix<N1, N1> xx = new Matrix<>(Nat.N1(), Nat.N1());
        Matrix<N1, N1> p = new Matrix<>(Nat.N1(), Nat.N1());
        p.set(0, 0, 1);
        return new RandomVector<>(xx, p);
    }

    public RandomVector<N1> f1x(RandomVector<N1> x, Matrix<N1, N1> u) {
        Matrix<N1, N1> p = new Matrix<>(Nat.N1(), Nat.N1());
        p.set(0, 0, 1);
        return new RandomVector<>(x.x, p);
    }

    public RandomVector<N2> f2(RandomVector<N2> x, Matrix<N1, N1> u) {
        Matrix<N2, N1> xx = new Matrix<>(Nat.N2(), Nat.N1());
        Matrix<N2, N2> p = new Matrix<>(Nat.N2(), Nat.N2());
        p.set(0, 0, 1);
        p.set(1, 1, 1);
        return new RandomVector<>(xx, p);
    }

    @Test
    public void testRandomVectorIntegration1() {
        Matrix<N1, N1> x = new Matrix<>(Nat.N1(), Nat.N1());
        x.set(0, 0, 1);
        Matrix<N1, N1> p = new Matrix<>(Nat.N1(), Nat.N1());
        p.set(0, 0, 1);
        RandomVector<N1> v1 = new RandomVector<>(x, p);
        Matrix<N1, N1> u = new Matrix<>(Nat.N1(), Nat.N1());
        // big time step here to see the effect
        IntegratingPredictor<N1, N1> predictor = new IntegratingPredictor<>();
        RandomVector<N1> i1 = predictor.predict(this::f1, v1, u, 1.0);
        // same as input
        assertArrayEquals(new double[] { 1 }, i1.x.getData(), kDelta);
        // more variance over time
        assertArrayEquals(new double[] { 1.278 }, i1.P.getData(), kDelta);
    }


    @Test
    public void testRandomVectorIntegration1x() {
        Matrix<N1, N1> x = new Matrix<>(Nat.N1(), Nat.N1());
        x.set(0, 0, 1);
        Matrix<N1, N1> p = new Matrix<>(Nat.N1(), Nat.N1());
        p.set(0, 0, 1);
        RandomVector<N1> v1 = new RandomVector<>(x, p);
        Matrix<N1, N1> u = new Matrix<>(Nat.N1(), Nat.N1());
        // big time step here to see the effect
        IntegratingPredictor<N1, N1> predictor = new IntegratingPredictor<>();
        RandomVector<N1> i1 = predictor.predict(this::f1x, v1, u, 1);
        // pretty close to e, which is the right answer.
        assertArrayEquals(new double[] { 2.708 }, i1.x.getData(), kDelta);
        // more variance over time
        assertArrayEquals(new double[] { 1.278 }, i1.P.getData(), kDelta);
    }

    @Test
    public void testRandomVectorIntegration2() {
        Matrix<N2, N1> x = new Matrix<>(Nat.N2(), Nat.N1());
        x.set(0, 0, 1);
        x.set(1, 0, 1);
        Matrix<N2, N2> p = new Matrix<>(Nat.N2(), Nat.N2());
        p.set(0, 0, 1);
        p.set(0, 1, 0);
        p.set(1, 0, 0);
        p.set(1, 1, 1);
        RandomVector<N2> v2 = new RandomVector<>(x, p);
        Matrix<N1, N1> u = new Matrix<>(Nat.N1(), Nat.N1());
        // big time step here to see the effect
        IntegratingPredictor<N2, N1> predictor = new IntegratingPredictor<>();
        RandomVector<N2> i2 = predictor.predict(this::f2, v2, u, 1);
        assertArrayEquals(new double[] { 1, 1 }, i2.x.getData(), kDelta);
        assertArrayEquals(new double[] { 1.278, 0, 0, 1.278 }, i2.P.getData(), kDelta);
    }


}
