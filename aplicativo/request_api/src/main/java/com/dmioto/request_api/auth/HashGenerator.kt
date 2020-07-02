package com.dmioto.request_api.auth

import com.dmioto.request_api.CompanionKeys.Companion.PRIVATE_KEY_API
import com.dmioto.request_api.CompanionKeys.Companion.PUBLIC_KEY_API
import java.math.BigInteger
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException


/**
 *  Class designed to generate hash authenticators like MD5 used in the API request.
 *  @param timestamp to be able to generate a proper hash.
 * **/

internal class HashGenerator(private val timestamp: String){

    /** @see generateMd5Hash **/
    val md5: String by lazy {
        generateMd5Hash()
    }


    /**
     * Generate a MD5 hash to validate API requests.
     * Uses [timestamp] to generate a hash given the [System.currentTimeMillis].
     * @param privateKey (optional) API provided private key.
     * @param publicKey (optional) API provided public key.
     *
     * @return an MD5 hash
     *
     * @throws NoSuchAlgorithmException if on the specific Android device doesn't have MD5 algorithm
     * **/
    @Throws(NoSuchAlgorithmException::class)
    private fun generateMd5Hash(privateKey: String = PRIVATE_KEY_API, publicKey: String = PUBLIC_KEY_API): String {
        val messageDigest = MessageDigest.getInstance("MD5").run {
            this.digest(timestamp.toByteArray()
                    + privateKey.toByteArray()
                    + publicKey.toByteArray())
        }

        var hashtext = BigInteger(1, messageDigest).toString(16)

        while (hashtext.length < 32) {
            hashtext = "0$hashtext"
        }

        return hashtext
    }

}