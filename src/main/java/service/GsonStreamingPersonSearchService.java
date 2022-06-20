package service;

import domain.model.Person;

/**
 * GsonのStreaming APIを用いて人物を探す機能
 *
 * @author Takumi Osawa
 */
public class GsonStreamingPersonSearchService implements IPersonSearchService {

    /**
     * 人物一覧から特定の人物を探し出す
     *
     * @param id     人物のID
     * @param source 人物一覧
     * @return 目的の人物。存在しない場合はnull。
     */
    @Override
    public Person search(String id, String source) {
        return null;
    }
}
