package be.henallux.masi.pedagogique.utils;

import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;

/**
 * Created by Le Roi Arthur on 24-12-17.
 */

public class SHA256CryptographyService implements ICryptographyService{

    @Override
    public String hashPassword(String password) {
        return Hashing.sha256().hashString(password, StandardCharsets.UTF_8).toString();
    }
}
