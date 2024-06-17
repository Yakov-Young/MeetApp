package com.kemsu.sibiryakov.api.Services;

import com.kemsu.sibiryakov.api.Entities.Emuns.ERole;
import com.kemsu.sibiryakov.api.JwtFilter.JwtFilter;

public class RightsService {

    public static boolean checkRight(String jwt, ERole... role) {
        if (jwt == null) {
            return false;
        }

        ERole tokenRole = ERole.valueOf(
                JwtFilter.getBody(jwt)
                        .get("role")
                        .toString()
                        .toUpperCase());

        for (ERole r : role) {
            if (tokenRole == r) {
                return true;
            }
        }

        return false;
    }
}
