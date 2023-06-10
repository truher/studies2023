package org.team100.lib.reference;

import org.junit.jupiter.api.Test;
import org.team100.lib.estimator.IntegratingPredictor;
import org.team100.lib.estimator.NonlinearEstimator;
import org.team100.lib.fusion.LinearPooling;
import org.team100.lib.fusion.VarianceWeightedLinearPooling;
import org.team100.lib.system.NonlinearPlant;
import org.team100.lib.system.examples.NormalDoubleIntegratorRotary1D;

import edu.wpi.first.math.numbers.N1;
import edu.wpi.first.math.numbers.N2;

public class ReferenceGeneratorTest {

    @Test
    public void testSimple() {
        NonlinearPlant<N2, N1, N2> plant = new NormalDoubleIntegratorRotary1D();
        IntegratingPredictor<N2, N1> predictor = new IntegratingPredictor<>();
        LinearPooling<N2> pooling = new VarianceWeightedLinearPooling<>();
        NonlinearEstimator<N2, N1, N2> eae = new NonlinearEstimator<>(plant, predictor, pooling);
        ReferenceGenerator rg = new ReferenceGenerator(eae);
    }
}
