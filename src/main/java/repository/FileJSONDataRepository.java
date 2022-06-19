package repository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * JSONファイルからJSONデータを取得するリポジトリ
 * @author Takumi Osawa
 */
public class FileJSONDataRepository implements IJSONDataRepository {
    private final String pathOfResource;

    /**
     * コンストラクタ
     * @param pathOfResource resources内のjsonファイルパス
     */
    public FileJSONDataRepository(String pathOfResource) {
        this.pathOfResource = pathOfResource;
    }

    /**
     * JSONデータを取得する
     * @return JSONデータ文字列。取得に失敗した場合はnull。
     */
    @Override
    public String get() {
        StringBuilder stringBuilder = new StringBuilder();

        try (InputStream inputStream = getClass().getResourceAsStream(pathOfResource)) {
            assert inputStream != null;
            try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
                String content = bufferedReader.readLine();
                while(content != null) {
                    stringBuilder.append(content).append(System.getProperty("line.separator"));
                    content = bufferedReader.readLine();
                }
            }
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }

        return stringBuilder.toString();
    }
}