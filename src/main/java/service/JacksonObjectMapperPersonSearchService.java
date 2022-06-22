package service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import domain.model.Person;

import java.util.List;

/**
 * JacksonのObjectMapperを用いて人物を探す機能
 *
 * @author Takumi Osawa
 */
public class JacksonObjectMapperPersonSearchService implements IPersonSearchService {
    private static final ObjectMapper mapper = new ObjectMapper();

    /**
     * 人物一覧から特定の人物を探し出す
     *
     * @param id     人物のID
     * @param source 人物一覧
     * @return 目的の人物。存在しない場合はnull。
     */
    @Override
    public Person search(String id, String source) {
        try {
            List<Person> people = mapper.readValue(source, new TypeReference<>() {
            });
            return people.stream()
                    .filter(it -> id.equals(it.getId()))
                    .findFirst()
                    .orElse(null);
        } catch (JsonProcessingException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
