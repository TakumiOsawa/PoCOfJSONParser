package domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 人物を表すクラス
 * サンプルなので各フィールドは値オブジェクトにしない
 *
 * @author Takumi Osawa
 */
@NoArgsConstructor
@Setter
@ToString
public class Person {
    /**
     * 友人を表すクラス
     * サンプルなので各フィールドは値オブジェクトにしない
     *
     * @author Takumi Osawa
     */
    @NoArgsConstructor
    @Setter
    @ToString
    public static class Friend {
        private int friendId;
        private String name;
    }

    @Getter
    private String id;
    private int index;
    private String guid;
    @JsonProperty("isActive")
    private boolean isActive;
    private String balance;
    private String picture;
    private int age;
    private String eyeColor;
    private String name;
    private String gender;
    private String company;
    private String email;
    private String phone;
    private String address;
    private String about;
    private String registered;
    private double latitude;
    private double longitude;
    private String[] tags;
    private Friend[] friends;
    private String greeting;
    private String favoriteFruit;
}
