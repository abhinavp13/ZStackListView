package com.pabhinav.zsrv.zstacklistview;

import java.util.HashMap;

/**
 * Class used for saving name plate letter and corresponding color mapping.
 *
 * @author pabhinav (pabhinav@iitrpr.ac.in)
 */
public class LetterColorMapping {

    /**
     * {@link HashMap} mapping string to int, where
     * string is denoting the name plate letter and
     * int is the corresponding color id.
     */
    public static HashMap<String, Integer> letterToColorIdMapping;

    /** Static color mapping population **/
    static {
        letterToColorIdMapping = new HashMap<String, Integer>(){{
                put("A",R.color.A);
                put("B",R.color.B);
                put("C",R.color.C);
                put("D",R.color.D);
                put("E",R.color.E);
                put("F",R.color.F);
                put("G",R.color.G);
                put("H",R.color.H);
                put("I",R.color.I);
                put("J",R.color.J);
                put("K",R.color.K);
                put("L",R.color.L);
                put("M",R.color.M);
                put("N",R.color.N);
                put("O",R.color.O);
                put("P",R.color.P);
                put("Q",R.color.Q);
                put("R",R.color.R);
                put("S",R.color.S);
                put("T",R.color.T);
                put("U",R.color.U);
                put("V",R.color.V);
                put("W",R.color.W);
                put("X",R.color.X);
                put("Y",R.color.Y);
                put("Z",R.color.Z);
        }};
    }
}
