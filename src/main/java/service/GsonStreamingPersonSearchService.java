package service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.jayway.jsonpath.JsonPath;
import domain.model.Person;

import java.io.IOException;
import java.io.StringReader;
import java.util.LinkedHashMap;

/**
 * GsonのStreaming APIを用いて人物を探す機能
 *
 * @author Takumi Osawa
 */
public class GsonStreamingPersonSearchService implements IPersonSearchService {

    private static final Gson gson = new Gson();

    /**
     * 人物一覧から特定の人物を探し出す
     *
     * @param id     人物のID
     * @param source 人物一覧
     * @return 目的の人物。存在しない場合はnull。
     */
    @Override
    public Person search(String id, String source) {
        try (JsonReader jsonReader = new JsonReader(new StringReader(source))) {
            return analyseToken(id, source, jsonReader);
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
     * @param jsonReader GsonのJSONパーサ
     * @return 対象のPersonインスタンス。変換失敗または見つからなかった時はnull。
     * @throws IOException Gson Streaming APIが送出する
     */
    private Person analyseToken(String id, String source, JsonReader jsonReader) throws IOException {
        boolean foundTargetId = false;

        jsonReader.beginArray();
        while (jsonReader.hasNext()) {
            jsonReader.beginObject();
            while (jsonReader.hasNext()) {
                String fieldName = jsonReader.nextName();
                if (fieldName.equals("id")) {
                    String idValue = jsonReader.nextString();
                    if (idValue.equals(id)) {
                        foundTargetId = true;
                    }
                } else {
                    jsonReader.skipValue();
                }
            }
            jsonReader.endObject();
            if (foundTargetId) {
                return transformJsonPathToPerson(source, jsonReader);
            }
        }
        jsonReader.endArray();

        return null;
    }

    /**
     * 対象人物を示すJSONPathからPersonインスタンスを生成して返す
     *
     * @param source 人物一覧
     * @param jsonReader GsonのJSONパーサ
     * @return 変換後のPersonインスタンス
     */
    private Person transformJsonPathToPerson(String source, JsonReader jsonReader) {
        String jsonPath = jsonReader.getPreviousPath();
        LinkedHashMap<String, Object> result = JsonPath.read(source, jsonPath);
        String jsonStr = gson.toJson(result, new TypeToken<LinkedHashMap<String, Object>>() {
        }.getType());
        return gson.fromJson(jsonStr, Person.class);
    }
}
