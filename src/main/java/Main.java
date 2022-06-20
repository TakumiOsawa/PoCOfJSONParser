import domain.model.Person;
import domain.repository.FileJSONDataRepository;
import domain.repository.IJSONDataRepository;
import service.IPersonSearchService;
import service.JacksonObjectMapperPersonSearchService;

/**
 * @author Takumi Osawa
 */
public class Main {
    public static void main(String[] args) {
        String pathOfResource = "/testData.json";
        IJSONDataRepository repository = new FileJSONDataRepository(pathOfResource);
        String data = repository.get();

        IPersonSearchService jacksonObjectMapper = new JacksonObjectMapperPersonSearchService();

        long start = System.currentTimeMillis();
        Person target = jacksonObjectMapper.search("62af330633189062cf3e40bf", data);
        long end = System.currentTimeMillis();

        System.out.println(target);
        System.out.println((end - start) + "ms");
    }
}
