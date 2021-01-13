package br.com.core.utils;

import br.com.core.exception.ClassException;
import br.com.core.model.Client;
import org.springframework.http.HttpStatus;

import javax.security.auth.login.CredentialException;
import java.util.regex.Pattern;

public class Utils {

    private static Pattern PATTERN_GENERIC = Pattern.compile("[0-9]{3}\\.?[0-9]{3}\\.?[0-9]{3}\\-?[0-9]{2}");
    private static Pattern PATTERN_NUMBERS = Pattern.compile("(?=^((?!((([0]{11})|([1]{11})|([2]{11})|([3]{11})|([4]{11})|([5]{11})|([6]{11})|([7]{11})|([8]{11})|([9]{11})))).)*$)([0-9]{11})");

    public static boolean isValidClient(Client c) throws ClassException {
        try {
            if (c != null) {
                isValidId(c.getId());
                isValidAge(c.getIdade());
                isValidCpf(c.getCpf());
                isValidEmail(c.getEmail());
                return true;
            } else
                throw new ClassException("Cliente Inválido", HttpStatus.BAD_REQUEST);
        } catch (ClassException ex) {
            throw new ClassException(ex.getMsg(), ex.getStatus());
        }
    }

    private static void isValidId(Long id) throws ClassException {
        if (id < 0)
            throw new ClassException("Id Inválido", HttpStatus.BAD_REQUEST);
    }

    private static void isValidEmail(String email) throws ClassException {
        if (email == null || email.equals(""))
            return;
        if (!email.contains("@"))
            throw new ClassException("E-mail Inválido", HttpStatus.BAD_REQUEST);
        email = email.split("@")[1];
        if (
                email.equalsIgnoreCase("gmail.com") ||
                email.equalsIgnoreCase("hotmail.com") ||
                email.equalsIgnoreCase("yahoo.com") ||
                email.equalsIgnoreCase("outlook.com") ||
                email.equalsIgnoreCase("alelo.com")
        )
            return;
        else
            throw new ClassException("E-mail Inválido", HttpStatus.BAD_REQUEST);
    }

    private static void isValidAge(int idade) throws ClassException {
        if (idade > 130 || idade < 0) {
            throw new ClassException("Idade Inválida", HttpStatus.BAD_REQUEST);
        }
    }

    private static void isValidCpf(String cpf) throws ClassException {
        if (cpf == null || cpf.equals("")) {
            throw new ClassException("É necessário informar um CPF válido.", HttpStatus.BAD_REQUEST);
        }
        if (cpf != null && PATTERN_GENERIC.matcher(cpf).matches()) {
            cpf = cpf.replaceAll("-|\\.", "");
            if (cpf != null && PATTERN_NUMBERS.matcher(cpf).matches()) {
                int[] numbers = new int[11];
                for (int i = 0; i < 11; i++) numbers[i] = Character.getNumericValue(cpf.charAt(i));
                int i;
                int sum = 0;
                int factor = 100;
                for (i = 0; i < 9; i++) {
                    sum += numbers[i] * factor;
                    factor -= 10;
                }
                int leftover = sum % 11;
                leftover = leftover == 10 ? 0 : leftover;
                if (leftover == numbers[9]) {
                    sum = 0;
                    factor = 110;
                    for (i = 0; i < 10; i++) {
                        sum += numbers[i] * factor;
                        factor -= 10;
                    }
                    leftover = sum % 11;
                    leftover = leftover == 10 ? 0 : leftover;
                    if (leftover != numbers[10])
                        throw new ClassException("CPF Inválido", HttpStatus.BAD_REQUEST);
                    else
                        return;
                }
            }
        }
        throw new ClassException("CPF Inválido", HttpStatus.BAD_REQUEST);
    }


}
