package it.unimol.vino.utils;


import org.jetbrains.annotations.NotNull;

public class PasswordValidator {

    public static boolean isPasswordNotValid(@NotNull String password) {
        return password.length() < 8 ||
                password.length() > 30 ||
                !password.matches(".*[A-Z].*") ||
                !password.matches(".*[a-z].*") ||
                !password.matches(".*[0-9].*");
    }

    public static String ERROR_MESSAGE = "La password non è valida, deve essere compresa tra 8 e 30 caratteri, " +
            "e deve contenere almeno una lettera maiuscola, una lettera minuscola e un numero";
}
