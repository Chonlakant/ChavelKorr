package com.twentyfour.chavel.bus;

/**
 * Created by Bas on 9/3/2017 AD.
 */

import com.squareup.otto.Bus;

public class BusProvider {
    private static Bus BUS = new Bus();
    public static Bus getInstance() {
        return BUS;
    }
}
