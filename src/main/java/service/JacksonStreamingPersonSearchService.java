package service;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import domain.model.Person;

import java.io.IOException;

/**
 * JacksonのStreaming APIを用いて人物を探す機能
 *
 * @author Takumi Osawa
 */
public class JacksonStreamingPersonSearchService implements IPersonSearchService {

    public static JsonFactory jsonFactory = new JsonFactory();
    public static ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.configure(JsonReadFeature.ALLOW_UNESCAPED_CONTROL_CHARS.mappedFeature(), true);
    }

    /**
     * 人物一覧から特定の人物を探し出す
     *
     * @param id     人物のID
     * @param source 人物一覧
     * @return 目的の人物。存在しない場合はnull。
     */
    @Override
    public Person search(String id, String source) {
        try (JsonParser jsonParser = jsonFactory.createParser(source)) {
            return analyseToken(id, source, jsonParser);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * JSONデータ文字列のトークンを解釈して、対象のJSONデータ文字列を探し出し、Personインスタンスに変換する
     *
     * @param id         人物のID
     * @param source     人物一覧
     * @param jsonParser JacksonのJSONパーサ
     * @return 対象のPersonインスタンス。変換失敗または見つからなかった時はnull。
     * @throws IOException Jackson Streaming APIが送出する
     */
    private Person analyseToken(String id, String source, JsonParser jsonParser) throws IOException {
        JsonToken token;

        //入れ子のデータを解析するために用意したカウンタ。ネスト分だけインクリメントされる。
        int insideObjectCount = 0;
        int insideArrayCount = 0;

        //対象JSONデータ文字列をソース文字列から切り出すために使用するオフセット値
        int startOffsetOfTarget = 0;
        int endOffsetOfTarget = 0;

        boolean foundIDField = false;
        boolean foundTargetID = false;

        do {
            token = jsonParser.nextToken();
            switch (token) {
                case START_ARRAY -> ++insideArrayCount;
                case END_ARRAY -> --insideArrayCount;
                case START_OBJECT -> {
                    ++insideObjectCount;
                    if (insideObjectCount == 1) {
                        startOffsetOfTarget =
                                Math.toIntExact(jsonParser.getCurrentLocation().getCharOffset());
                    }
                }
                case END_OBJECT -> {
                    --insideObjectCount;
                    if (foundTargetID && insideObjectCount == 0) {
                        //対象データを見つけたら、JSONデータ文字列をPersonインスタンスに変換して返す
                        endOffsetOfTarget =
                                Math.toIntExact(jsonParser.getCurrentLocation().getCharOffset());
                        return transformJsonStringToPerson(
                                source.substring(startOffsetOfTarget - 1, endOffsetOfTarget + 1));
                    }
                }
                default -> {
                    String fieldName = jsonParser.getCurrentName();

                    //スカラー配列内部の値の場合は無視する
                    if (fieldName == null) {
                        continue;
                    }

                    if (!foundIDField) {
                        if (fieldName.equals("id")) {
                            foundIDField = true;
                        }
                    } else {
                        String idValue = jsonParser.getText();
                        if (idValue.equals(id)) {
                            foundTargetID = true;
                        }
                        foundIDField = false;
                    }
                }
            }
        } while (!(token == JsonToken.END_ARRAY && insideArrayCount == 0));

        return null;
    }

    /**
     * 1件のJSONデータをPersonインスタンスに変換する
     *
     * @param source 元となる1件分のJSONデータ
     * @return 変換後のPersonインスタンス。変換失敗時はnull。
     */
    private Person transformJsonStringToPerson(String source) {
        try {
            return mapper.readValue(source, Person.class);
        } catch (JsonProcessingException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
