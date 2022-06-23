package service

import domain.repository.FileJSONDataRepository
import spock.lang.Specification
import spock.lang.Unroll

class GsonStreamingPersonSearchServiceSpec extends Specification {
    @Unroll
    def "#idでJSONデータを検索した時の結果==nullの評価値が#resultであること"() {
        setup:
        def pathOfResource = "/testData.json"
        def repository = new FileJSONDataRepository(pathOfResource)
        def source = repository.get()
        def gsonStreaming = new GsonStreamingPersonSearchService()

        expect:
        (gsonStreaming.search(id, source) != null) == result

        where:
        id                         || result
        "62b35772c6ec3db35156e227" || true
        null                       || false
        ""                         || false
    }
}
