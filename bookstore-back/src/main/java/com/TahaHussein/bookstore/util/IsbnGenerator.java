package com.TahaHussein.bookstore.util;

import java.util.Random;

/**
 * @author Taha Hussein
 *         taha.hussein.12.6.95@gmail.com
 *         --
 */
public class IsbnGenerator implements NumberGenerator {

    // ======================================
    // =          Business methods          =
    // ======================================

    @Override
    public String generateNumber() {
        return "13-84356-" + Math.abs(new Random().nextInt());
    }
}
