package framework.data;

/**
 * Created by Zakir_Mustafin on 2/20/2017.
 */
public class LetterParams {
    private static final String ADDRESS = "samsamitch1@yandex.ru";
    private static final String SUBJECTFORLETTER = String.valueOf(System.currentTimeMillis());
    private static final String TEXTFORLETTER = "Мама мыла раму!!!";

    public static String getADDRESS() {
        return ADDRESS;
    }

    public static String getSUBJECTFORLETTER() {
        return SUBJECTFORLETTER;
    }

    public static String getTEXTFORLETTER() {
        return TEXTFORLETTER;
    }
}
