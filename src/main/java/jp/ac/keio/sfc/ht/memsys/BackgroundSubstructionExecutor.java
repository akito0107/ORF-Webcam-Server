package jp.ac.keio.sfc.ht.memsys;

import jp.ac.keio.sfc.ht.memsys.comp.Matrix;
import jp.ac.keio.sfc.ht.memsys.comp.RandomMatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by aqram on 11/10/14.
 */
public class BackgroundSubstructionExecutor {

    private static BackgroundSubstructionExecutor sInstance = null;
    private static final int POOLSIZE = 64;
    private final ExecutorService mService;
    private AtomicLong[] mCache;
    private long THRESHOLD = 6500000;
    private int N = 0;
    private int M = 0;

    private RealMatrix A;
    private Matrix AA;

    private volatile boolean isLearn = true;

    private BackgroundSubstructionExecutor(int width, int height, int n) {
        mService = Executors.newFixedThreadPool(POOLSIZE);
        mCache = new AtomicLong[n];

        for (int i = 0; i < mCache.length; i++) {
            mCache[i] = new AtomicLong(0);
        }
        N = width * height;
        M = N / 2;

        A = RandomMatrixUtils.createGaussianRealMatrix(M, N);
        AA = new Matrix(A.getData());

    }

    public static BackgroundSubstructionExecutor getsInstance(int width, int height, int N) {
        if (sInstance == null) {
            sInstance = new BackgroundSubstructionExecutor(width, height, N);
        }

        return sInstance;
    }

    public Future<Boolean> registerImage(int ID, BufferedImage image) {

        SubstractionTask task = new SubstractionTask(image, ID);

        return mService.submit(task);
    }

    public boolean isLearn() {
        return isLearn;
    }

    public void setLearn(boolean flag) {
        isLearn = flag;
    }


    class SubstractionTask implements Callable<Boolean> {

        private BufferedImage mImage;
        private int ID;

        public SubstractionTask(BufferedImage image, int ID) {
            mImage = image;
            this.ID = ID;
        }

        @Override
        public Boolean call() throws Exception {

            boolean result = false;

            long previous = mCache[ID].get();

            int width = mImage.getWidth();
            int height = mImage.getHeight();

            for(int i=0; i<height; i++){
                for(int j=0; j<width; j++){
                    Color c = new Color(mImage.getRGB(j, i));
                    int red = (int)(c.getRed() * 0.299);
                    int green = (int)(c.getGreen() * 0.587);
                    int blue = (int)(c.getBlue() *0.114);
                    Color newColor = new Color(red+green+blue,
                            red+green+blue,red+green+blue);
                    mImage.setRGB(j,i,newColor.getRGB());
                }
            }

            int[] dataBuffInt = mImage.getRGB(0, 0, width, height, null, 0, width);

            Matrix XX = new Matrix(dataBuffInt, N);
            Matrix yy = AA.times(XX);

            if (isLearn()) {
                long v = Math.round(previous * 0.8 + yy.sum() * 0.2);
                mCache[ID].set(v);
            } else {
                long v = Math.round(yy.sum());

                if (Math.abs(v - previous) > THRESHOLD) {
                    result = true;
                }
            }

            return result;
        }
    }
}
