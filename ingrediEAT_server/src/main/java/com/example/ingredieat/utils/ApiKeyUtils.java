package com.example.ingredieat.utils;


public class ApiKeyUtils {

    private static int i = 0;

    private static String[] ApiKeys = {
        "e9f3f861a3304f878b8d027e96277716",
        "7144d96943ba4efc9862372e028fd772",
        "d939fd2dbc2a4b5ab46504bd7e756048",
        "1ec28c51343e428ab9b6daa43f6b8f00",
        "00994842e3674eeab5b01415252a89cf",
        "c2e8960eff7f4c639b36b59fbe5d3528",
        "ab8a8788c2374f7887db970320525cef",
        "cb857dffd98047c8a182595ac0af3069",
        "69b7686c2023405face5967ec3b90629",
        "ac4c622cbdf145d69815cbeb2386d2ce",
    };

    public static String getApiKey() {
        // return ApiKeys[(i++) % ApiKeys.length];
        return ApiKeys[7];
    }
}
