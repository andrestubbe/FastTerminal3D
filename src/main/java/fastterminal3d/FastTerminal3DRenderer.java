package fastterminal3d;

import fastascii.FastGlyphDensity;
import fastterminal.FastTerminalScene;

public class FastTerminal3DRenderer {

    /**
     * Downsamples a high-resolution 3D pixel buffer into terminal characters.
     * Uses SSAA (Super Sample Anti-Aliasing).
     * 
     * @param src          The high-resolution RGB pixel array from FastSoftware3D
     * @param srcW         Width of the pixel array (cols * ssaa)
     * @param srcH         Height of the pixel array (rows * 2 * ssaa)
     * @param canvas       The terminal canvas to draw onto
     * @param cols         Number of terminal columns
     * @param rows         Number of terminal rows
     * @param ssaa         The Super Sample Anti-Aliasing factor
     * @param isAsciiMode  If true, renders using ASCII characters based on brightness. If false, uses Unicode half-blocks.
     */
    public static void render(int[] src, int srcW, int srcH,
                              FastTerminalScene canvas,
                              int cols, int rows, int ssaa, boolean isAsciiMode) {
        for (int row = 0; row < rows; row++) {
            int yTop = row * 2 * ssaa;
            int yBot = (row * 2 + 1) * ssaa;

            for (int col = 0; col < cols; col++) {
                int xBase = col * ssaa;

                // Average top block
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
                        if (srcIdx >= 0 && srcIdx < src.length) {
                            int px = src[srcIdx];
                            rT += (px >> 16) & 0xFF;
                            gT += (px >>  8) & 0xFF;
                            bT +=  px        & 0xFF;
                            countT++;
                        }
                    }
                }
                int topC = 0;
                if (countT > 0) {
                    topC = ((rT / countT) << 16) | ((gT / countT) << 8) | (bT / countT);
                }

                // Average bottom block
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
                        if (srcIdx >= 0 && srcIdx < src.length) {
                            int px = src[srcIdx];
                            rB += (px >> 16) & 0xFF;
                            gB += (px >>  8) & 0xFF;
                            bB +=  px        & 0xFF;
                            countB++;
                        }
                    }
                }
                int botC = 0;
                if (countB > 0) {
                    botC = ((rB / countB) << 16) | ((gB / countB) << 8) | (bB / countB);
                }

                if (isAsciiMode) {
                    int r = 0, g = 0, b = 0;
                    int totalCount = countT + countB;
                    if (totalCount > 0) {
                        r = (rT + rB) / totalCount;
                        g = (gT + gB) / totalCount;
                        b = (bT + bB) / totalCount;
                    }
                    int color = (r << 16) | (g << 8) | b;
                    float brightness = (0.299f * r + 0.587f * g + 0.114f * b) / 255.0f;
                    char glyph = FastGlyphDensity.getGlyphForOpacity(brightness);
                    canvas.writeCell(col, row, glyph, color, 0x000000);
                } else {
                    canvas.writeCell(col, row, '\u2580', topC, botC); // ▀
                }
            }
        }
    }
}
