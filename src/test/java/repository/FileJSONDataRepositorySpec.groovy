package repository

import domain.repository.FileJSONDataRepository
import spock.lang.Specification

class FileJSONDataRepositorySpec extends Specification {
    def "FileJSONDataRepositoryを作成できる"() {
        expect:
        def sut = new FileJSONDataRepository("/testData.json")
        sut != null
    }

    def "resourcesのJSONファイルからテストデータを取得できる"() {
        setup:
        def sut = new FileJSONDataRepository("/testData.json")

        expect:
        sut.get() != null
    }
}