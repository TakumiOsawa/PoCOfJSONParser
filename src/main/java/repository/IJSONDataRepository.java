package repository;

/**
 * JSONデータ入手先リポジトリ
 * @author Takumi Osawa
 */
public interface IJSONDataRepository {
    /**
     * JSONデータを取得する
     * @return JSONデータ文字列。取得に失敗した場合はnull。
     */
    String get();
}
