package jp.ac.keio.sfc.ht.memsys.comp;

import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;
import org.apache.commons.math3.random.GaussianRandomGenerator;
import org.apache.commons.math3.random.MersenneTwister;

import java.util.Arrays;

/**
 * Created by aqram on 6/23/14.
 */
public class RandomMatrixUtils {

    public static RealMatrix createGaussianRealMatrix(int N, int M){

        double [][] matrix = new double[N][M];

        GaussianRandomGenerator gen = new GaussianRandomGenerator(new MersenneTwister());

        for(int i = 0; i<N; i++){
            for(int j = 0; j<M; j++){
                matrix[i][j] = gen.nextNormalizedDouble();
            }
        }

        RealMatrix A = MatrixUtils.createRealMatrix(matrix);

        //System.out.println(String.valueOf(A.getColumnDimension()));

        return A;
    }

    public static void sort(RealMatrix A){
        for(int i=0; i<A.getRowDimension(); i++){
            RealVector v = A.getColumnVector(i);
        }

    }

    public static void sort(RealVector V, int[] I) {
        double[] array = V.toArray();

        ArrayIndexComparator comparator = new ArrayIndexComparator(array);
        Integer[] indexes = comparator.createIndexArray();
        Arrays.sort(indexes,comparator);

    }
}

