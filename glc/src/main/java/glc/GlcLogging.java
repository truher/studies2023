package glc;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Set;

import glc.glc_interpolation.InterpolatingPolynomial;

class GlcLogging {

    /**
     * \brief Logs the states labeling each equivalence class to a nodesToFile
     * \param[in] name is the desired filename
     * \param[in] path is the desired location for the file to be saved
     * \param[in] domains is the set of labeled equivalence classes from a run of
     * GLC
     */

    void nodesToFile(
            final String name,
            final String path,
            final Set<GlcStateEquivalenceClass> domains) {
        try {
            PrintWriter points = new PrintWriter(new FileWriter(path + name));
            for (var x : domains) {
                ArrayList<Double> state = x.label.state;
                for (int j = 0; j < state.size() - 1; j++) {
                    points.print(state.get(j));
                    points.print(",");
                }
                points.println(state.get(state.size() - 1));
            }
            points.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * \brief Logs a finely sampled set of points along a trajectory to a file
     * \param[in] name is the desired filename
     * \param[in] path is the desired location for the file to be saved
     * \param[in] traj an interpolating spline object that is to be logged
     * \param[in] num_points is the number of points sampled uniformly along traj
     */

    void trajectoryToFile(
            final String name,
            final String path,
            final InterpolatingPolynomial traj,
            int num_points) {
        try {
            PrintWriter points = new PrintWriter(new FileWriter(path + name));

            double t = traj.initialTime();
            double dt = (traj.numberOfIntervals() * traj.intervalLength()) / num_points;
            for (int i = 0; i < num_points; i++) {
                ArrayList<Double> state = traj.at(t);
                for (int j = 0; j < state.size() - 1; j++) {
                    points.print(state.get(j));
                    points.print(",");
                }
                points.println(state.get(state.size() - 1));
                t += dt;
            }
            points.close();
        } catch (IOException e) {

            e.printStackTrace();

        }

    }

}