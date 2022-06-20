package service

import domain.repository.FileJSONDataRepository
import spock.lang.Specification
import spock.lang.Unroll

class GsonObjectMapperPersonSearchServiceSpec extends Specification {
    @Unroll
    def "#idでJSONデータを検索した時の結果が#resultであること"() {
        setup:
        def pathOfResource = "/testData.json"
        def repository = new FileJSONDataRepository(pathOfResource)
        def source = repository.get()
        def gsonObjectMapper = new GsonObjectMapperPersonSearchService()

        expect:
        (gsonObjectMapper.search(id, source) != null) == result

        where:
        id                         || result
        "62af330633189062cf3e40bf" || true
        null                       || false
        ""                         || false
    }
}