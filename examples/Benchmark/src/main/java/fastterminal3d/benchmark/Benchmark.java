package fastterminal3d.benchmark;

import fastterminal.FastTerminalScene;
import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
@Warmup(iterations = 2, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 3, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(1)
public class Benchmark {

    private int[] srcPixels;
    private FastTerminalScene canvas;
    private final int cols = 120;
    private final int rows = 30;
    private final int ssaa = 2;

    @Setup
    public void setup() {
        int width = cols * ssaa;
        int height = rows * 2 * ssaa;
        srcPixels = new int[width * height];
        for (int i = 0; i < srcPixels.length; i++) {
            srcPixels[i] = (i * 33) | 0xFF000000;
        }
        canvas = new FastTerminalScene(0, 0, cols, rows);
    }

    @org.openjdk.jmh.annotations.Benchmark
    public void benchmarkDownsampleHalfBlocks() {
        int srcW = cols * ssaa;
        int srcH = rows * 2 * ssaa;
        for (int row = 0; row < rows; row++) {
            int yTop = row * 2 * ssaa;
            int yBot = (row * 2 + 1) * ssaa;

            for (int col = 0; col < cols; col++) {
                int xBase = col * ssaa;

                int rT = 0, gT = 0, bT = 0;
                int countT = 0;
                for (int dy = 0; dy < ssaa; dy++) {
                    int yy = yTop + dy;
                    if (yy < 0 || yy >= srcH) continue;
                    int rowOff = yy * srcW + xBase;
                    for (int dx = 0; dx < ssaa; dx++) {
                        int xx = xBase + dx;
                        if (xx < 0 || xx >= srcW) continue;
                        int srcIdx = rowOff + dx;
                        if (srcIdx >= 0 && srcIdx < srcPixels.length) {
                            int px = srcPixels[srcIdx];
                            rT += (px >> 16) & 0xFF;
                            gT += (px >> 8) & 0xFF;
                            bT += px & 0xFF;
                            countT++;
                        }
                    }
                }
                int topC = (countT > 0) ? (((rT / countT) << 16) | ((gT / countT) << 8) | (bT / countT)) : 0;

                int rB = 0, gB = 0, bB = 0;
                int countB = 0;
                for (int dy = 0; dy < ssaa; dy++) {
                    int yy = yBot + dy;
                    if (yy < 0 || yy >= srcH) continue;
                    int rowOff = yy * srcW + xBase;
                    for (int dx = 0; dx < ssaa; dx++) {
                        int xx = xBase + dx;
                        if (xx < 0 || xx >= srcW) continue;
                        int srcIdx = rowOff + dx;
                        if (srcIdx >= 0 && srcIdx < srcPixels.length) {
                            int px = srcPixels[srcIdx];
                            rB += (px >> 16) & 0xFF;
                            gB += (px >> 8) & 0xFF;
                            bB += px & 0xFF;
                            countB++;
                        }
                    }
                }
                int botC = (countB > 0) ? (((rB / countB) << 16) | ((gB / countB) << 8) | (bB / countB)) : 0;

                canvas.writeCell(col, row, '\u2580', topC, botC);
            }
        }
    }
}
