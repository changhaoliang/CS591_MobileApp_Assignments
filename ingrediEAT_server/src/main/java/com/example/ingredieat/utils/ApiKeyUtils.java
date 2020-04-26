package com.example.ingredieat.utils;


public class ApiKeyUtils {

    private static int i = 0;

    private static String[] apiKeys = {
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
            "e9f3f861a3304f878b8d027e96277716",
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
            "e0a67c0ea7d245aaa327ea27cd7ebef8",
            "ae64447fc09643529bc0c74b023dca77",
            "a0a7e8ce08314bb48784b87466e47c42",
            "d775a037ba3744cd93b301f594257b57",
            "6497d7a548c341fda5c1c4e5c114c0dd",
            "f8096394b2ab45218e1dbd3a7229cad9",
            "c9dfe30d9a6c42e0929cba658d859254",
            "45585cf63bb34a918071a4db41511069",
            "5e5a6d74388d46b0959eef36f11b4853",
            "fbca1785fd824e9db96fa6a973d10b49",
            "fc413b85a37f4aabb496f69d5537d758",
            "bb32112e3f0e4b1a826aa144a7a6b159",
            "0dfbf18103184c4da23945a812aa2b4a",
            "0cbcf5022a144d6a80b4413916a48807",
            "0a52f5d15e654d0498faafc3a206cd89",
            "057d50f471da47f7a45dc2c4eee58d09",
            "49c939321a4149998ddd1a0795eb49d8",
            "924193c401014676a1e21de56411390a",
            "7a9064b8e6e0433583f499e34b54f55e",
            "ac40b19357834416911787736ebea811",
            "9b31ffff06b94463a2e9d33ddf6010be",
            "aededd9f8fa942589acc4c07365ccfbe",
            "433ccf402e5b4afba8a2fa838a488769",
            "8e01b1c99bbe48bb998a805582c457a2"
    };

    public static String getApiKey() {
        String apiKey = apiKeys[i];
        i = (++i) % apiKeys.length;
        return apiKey;
    }
}
