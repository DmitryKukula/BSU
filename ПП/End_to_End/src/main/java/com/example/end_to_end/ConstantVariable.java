package com.example.end_to_end;

import javax.crypto.spec.SecretKeySpec;

public class ConstantVariable {
    public static final String PATH_IN = "input\\"; // Путь до папки со входными файлами
    public static final String PATH_OUT = "output\\"; //Путь до папки с выходными файлами
    public static final String PATH_TEST = "test\\"; //Путь до папки с временными файлами
    public static final String PASSWORD = "Hahahahahaha9182"; // Пароль шифрования
    public static SecretKeySpec key;

    static {
        key = null;
    }
}
