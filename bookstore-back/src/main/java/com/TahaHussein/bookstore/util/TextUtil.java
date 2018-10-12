package com.TahaHussein.bookstore.util;

/**
 * @author Taha Hussein
 */
public class TextUtil {

    // ======================================
    // =          Business methods          =
    // ======================================

    public String sanitize(String textToSanitize) {
        return textToSanitize.replaceAll("\\s+", " ");
    }
}