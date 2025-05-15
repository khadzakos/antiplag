package hse.filestoring.service

import org.springframework.stereotype.Service

@Service
class HashService (
    private val hashAlgorithm: String = "SHA-256",
    private val hashLength: Int = 64,
    private val hashEncoding: String = "base64"
) {
    fun hash(input: ByteArray): String {
        val digest = java.security.MessageDigest.getInstance(hashAlgorithm)
        val hashBytes = digest.digest(input)
        return when (hashEncoding) {
            "base64" -> java.util.Base64.getEncoder().encodeToString(hashBytes)
            else -> throw IllegalArgumentException("Unsupported hash encoding: $hashEncoding")
        }.take(hashLength)
    }
}