import domain.model.Person;
import domain.repository.FileJSONDataRepository;
import domain.repository.IJSONDataRepository;
import service.*;

/**
 * @author Takumi Osawa
 */
public class Main {
    public static void main(String[] args) {
        String pathOfResource = "/testData.json";
        IJSONDataRepository repository = new FileJSONDataRepository(pathOfResource);
        String data = repository.get();

        IPersonSearchService jacksonObjectMapper = new JacksonObjectMapperPersonSearchService();
        IPersonSearchService gsonObjectMapper = new GsonObjectMapperPersonSearchService();
        IPersonSearchService jacksonStreaming = new JacksonStreamingPersonSearchService();
        IPersonSearchService gsonStreaming = new GsonStreamingPersonSearchService();
        String id = "62af330633189062cf3e40bf";

        long start = System.currentTimeMillis();
        Person target = jacksonObjectMapper.search(id, data);
        long end = System.currentTimeMillis();
        System.out.println(target);
        System.out.println("Jackson ObjectMapper : " + (end - start) + "ms");

        start = System.currentTimeMillis();
        target = gsonObjectMapper.search(id, data);
        end = System.currentTimeMillis();
        System.out.println(target);
        System.out.println("Gson ObjectMapper : " + (end - start) + "ms");

        start = System.currentTimeMillis();
        target = jacksonStreaming.search(id, data);
        end = System.currentTimeMillis();
        System.out.println(target);
        System.out.println("Jackson Streaming : " + (end - start) + "ms");

        start = System.currentTimeMillis();
        target = gsonStreaming.search(id, data);
        end = System.currentTimeMillis();
        System.out.println(target);
        System.out.println("Gson Streaming : " + (end - start) + "ms");
    }
}
