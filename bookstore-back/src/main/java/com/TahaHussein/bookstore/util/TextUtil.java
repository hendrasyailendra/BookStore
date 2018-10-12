package com.TahaHussein.bookstore.util;

/**
 * @author Taha Hussein
 *         taha.hussein.12.6.95@gmail.com
 *         --
 */
public class TextUtil {

    // ======================================
    // =          Business methods          =
    // ======================================

    public String sanitize(String textToSanitize) {
        return textToSanitize.replaceAll("\\s+", " ");
    }
}
