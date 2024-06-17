package com.kemsu.sibiryakov.api.Services;

import com.kemsu.sibiryakov.api.DTOs.AccessDTO.AccessDTO;
import com.kemsu.sibiryakov.api.Entities.UserPart.Access;
import com.kemsu.sibiryakov.api.Repositories.IAccessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

@Service
public class AccessService {
    private final IAccessRepository accessRepository;

    @Autowired
    public AccessService(IAccessRepository accessRepository) {
        this.accessRepository = accessRepository;
    }

    public Access getByLogin(String login) {
        return accessRepository.findByLogin(login).orElse(null);
    }

    public Access checkAccess(AccessDTO accessDTO) throws NoSuchAlgorithmException, InvalidKeySpecException {
        Access access = this.getByLogin(accessDTO.getLogin());

        boolean isMatch = access.getPassword().equals(
                this.encode(
                        accessDTO.getPassword(),
                        access.getSalt()
                ));

        if (access != null && isMatch) {
            return access;
        }
        return null;
    }

    public Access createAccess(AccessDTO accessDTO) throws NoSuchAlgorithmException, InvalidKeySpecException {
        String salt = this.generateSalt();
        Access access = new Access(accessDTO.getLogin(), this.encode(accessDTO.getPassword(), salt),
                salt);
        return accessRepository.save(
                new Access(
                        accessDTO.getLogin(),
                        this.encode(accessDTO.getPassword(), salt),
                        salt)
        );
    }

    private String generateSalt() throws NoSuchAlgorithmException {
        byte[] salt = new byte[16];
        new SecureRandom().nextBytes(salt);

        MessageDigest md = MessageDigest.getInstance("SHA-512");
        byte[] messageDigest = md.digest(salt);

        BigInteger no = new BigInteger(1, messageDigest);

        StringBuilder hashSalt = new StringBuilder(no.toString(16));

        while (hashSalt.length() < 32) {
            hashSalt.insert(0, "0");
        }

        return hashSalt.toString();
    }

    private String encode(String password, String salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
        char[] chars = password.toCharArray();

        PBEKeySpec spec = new PBEKeySpec(chars, salt.getBytes(StandardCharsets.UTF_8),
                10000, 64 * 8);
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
        byte[] hash = skf.generateSecret(spec).getEncoded();
        return Base64.getEncoder().encodeToString(hash);
    }

}
