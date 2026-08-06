package com.example.util

import java.net.URI

data class LinkConversionResult(
    val originalText: String,
    val convertedUrl: String,
    val protocol: String, // "HTTP" or "HTTPS"
    val isValid: Boolean,
    val detailMessage: String
)

object LinkConverter {

    /**
     * Converts raw HTML, anchor tags, or arbitrary text into a clean HTTP or HTTPS URL.
     * Keeps HTTPS as HTTPS, standardizes HTTP as HTTP, and cleans up HTML wrappers.
     */
    fun convertLink(rawInput: String): LinkConversionResult {
        val trimmed = rawInput.trim()
        if (trimmed.isEmpty()) {
            return LinkConversionResult(
                originalText = rawInput,
                convertedUrl = "",
                protocol = "UNKNOWN",
                isValid = false,
                detailMessage = "Tautan kosong"
            )
        }

        // 1. Extract URL if wrapped inside HTML tag like <a href="..."> or href='...'
        val extractedUrl = extractUrlFromHtml(trimmed)

        // 2. Clean up quotes and whitespaces
        var cleanUrl = extractedUrl.replace("^['\"]+|['\"]+$".toRegex(), "").trim()

        // 3. Handle protocol logic
        val protocol: String
        val isValid: Boolean
        val message: String

        when {
            cleanUrl.startsWith("https://", ignoreCase = true) -> {
                // HTTPS remains HTTPS (tetap)
                protocol = "HTTPS"
                isValid = true
                message = "Berhasil: HTTPS tetap aman (HTTPS)"
            }
            cleanUrl.startsWith("http://", ignoreCase = true) -> {
                // HTTP remains HTTP
                protocol = "HTTP"
                isValid = true
                message = "Berhasil: Tautan HTML diubah menjadi HTTP standar"
            }
            cleanUrl.startsWith("//") -> {
                // Protocol relative link e.g. //example.com -> https://example.com
                cleanUrl = "https:$cleanUrl"
                protocol = "HTTPS"
                isValid = true
                message = "Berhasil: Protocol-relative link disesuaikan ke HTTPS"
            }
            cleanUrl.startsWith("www.", ignoreCase = true) -> {
                // www prefix missing scheme -> add https://
                cleanUrl = "https://$cleanUrl"
                protocol = "HTTPS"
                isValid = true
                message = "Berhasil: Subdomain www ditambahkan skema HTTPS"
            }
            cleanUrl.contains(".") && !cleanUrl.contains(" ") -> {
                // Raw domain e.g. "mygame.net/play" -> "https://mygame.net/play"
                cleanUrl = "https://$cleanUrl"
                protocol = "HTTPS"
                isValid = true
                message = "Berhasil: Domain mentah diubah ke tautan web valid"
            }
            else -> {
                // Fallback attempt
                cleanUrl = if (cleanUrl.startsWith("http")) cleanUrl else "http://$cleanUrl"
                protocol = if (cleanUrl.startsWith("https")) "HTTPS" else "HTTP"
                isValid = cleanUrl.length > 8 && cleanUrl.contains(".")
                message = if (isValid) "Berhasil dikonversi" else "Peringatan: Format URL mungkin tidak valid"
            }
        }

        return LinkConversionResult(
            originalText = rawInput,
            convertedUrl = cleanUrl,
            protocol = protocol,
            isValid = isValid,
            detailMessage = message
        )
    }

    /**
     * Regex extractor for href="..." or src="..." or raw link inside HTML strings.
     */
    private fun extractUrlFromHtml(input: String): String {
        // Match href="...", href='...', src="..."
        val hrefRegex = """(?i)(?:href|src)\s*=\s*["']([^"']+)["']""".toRegex()
        val match = hrefRegex.find(input)
        if (match != null && match.groupValues.size > 1) {
            return match.groupValues[1]
        }

        // Match standalone URL in text e.g. <a ...>https://site.com</a>
        val urlRegex = """(?i)\b(https?://[^\s<>'"]+)""".toRegex()
        val urlMatch = urlRegex.find(input)
        if (urlMatch != null) {
            return urlMatch.value
        }

        // Strip basic HTML tags if present e.g. <a>http://test.com</a> -> http://test.com
        return input.replace("<[^>]*>".toRegex(), "").trim()
    }

    /**
     * Batch convert multi-line text containing multiple HTML links or raw URLs.
     */
    fun convertBulkLinks(bulkInput: String): List<LinkConversionResult> {
        val lines = bulkInput.lines().map { it.trim() }.filter { it.isNotEmpty() }
        return lines.map { convertLink(it) }
    }
}
