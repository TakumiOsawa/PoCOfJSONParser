import repository.FileJSONDataRepository;
import repository.IJSONDataRepository;

/**
 * @author Takumi Osawa
 */
public class Main {
    public static void main(String[] args) {
        String pathOfResource = "/testData.json";
        IJSONDataRepository repository = new FileJSONDataRepository(pathOfResource);
        String data = repository.get();
        System.out.println(data);
    }
}
