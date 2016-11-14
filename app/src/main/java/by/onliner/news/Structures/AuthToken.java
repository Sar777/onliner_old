package by.onliner.news.Structures;

/**
 * Токен авторизации
 */
public class AuthToken {
    /**
     * Токен для авторизации
     */
    private String mToken;
    /**
     * Время создания в unix формате
     */
    private long mUnixDate;

    public AuthToken() {
        this.mToken = "";
        this.mUnixDate = 0;
    }

    public AuthToken(String token) {
        this.mToken = token;
        this.mUnixDate = System.currentTimeMillis() / 1000L;
    }

    /**
     * Получение токена авторизации
     * @return Токен авторизвации
     */
    public String getToken() {
        return mToken;
    }

    /**
     * Получение времени получения токена в unix формате
     * @return Время получения токена в unix формате
     */
    public long getUnixDate() {
        return mUnixDate;
    }
}
