package service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import domain.model.Person;

import java.util.List;

/**
 * GsonのObjectMapperを用いて人物を探す機能
 *
 * @author Takumi Osawa
 */
public class GsonObjectMapperPersonSearchService implements IPersonSearchService {

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
        List<Person> people = gson.fromJson(source, new TypeToken<List<Person>>() {
        }.getType());
        return people.stream()
                .filter(it -> id.equals(it.getId()))
                .findFirst()
                .orElse(null);
    }
}
