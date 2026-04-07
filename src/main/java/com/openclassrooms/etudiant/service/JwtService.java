package com.openclassrooms.etudiant.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class JwtService {

    // D'abord, on crée une clef secrète
    private final String SECRET = "+04@7noillebeR&enuLeDelluB:terceSnEesoRenUemiAlIuQervuoceDiuQdraneRnUDelleBserTeriotsiHLtsEC";

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET.getBytes());
    }

    // Et on génère le token PROPREMENT
    public String generateToken(UserDetails userDetails) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername()) // login
                .setIssuedAt(new Date())  // Créé maintenant
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1h c'est déjà pas mal
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)  // Ca devrait suffire
                .compact();
    }
}
