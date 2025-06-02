package com.example.expensesplitter.util;

import java.security.SecureRandom;

public class InviteCodeUtil {
    private static final String CHARS = "ABCDEFGHJKLMNPQRSTUVWXYZ23456789";
    private static final int CODE_LENGTH = 8;
    private static final SecureRandom RANDOM = new SecureRandom();

    public static String generateInviteCode() {
        StringBuilder code = new StringBuilder(CODE_LENGTH);
        int bound = CHARS.length();
        for (int i = 0; i < CODE_LENGTH; i++) {
            int index = RANDOM.nextInt(bound);
            code.append(CHARS.charAt(index));
        }
        return code.toString();
    }

    public static String generateInviteUrl(String inviteCode, String baseUrl) {
        return baseUrl + "/api/groups/join?inviteCode=" + inviteCode;
    }
}
