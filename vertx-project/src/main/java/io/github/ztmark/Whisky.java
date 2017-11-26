package io.github.ztmark;

import io.vertx.core.json.JsonObject;

/**
 * Author: Mark
 * Date  : 2017/11/25
 */
public class Whisky {

    private final int id;

    private String name;

    private String origin;

    public Whisky(String name, String origin) {
        this(-1, name, origin);
    }

    public Whisky() {
        this.id = -1;
    }

    public Whisky(int id, String name, String origin) {
        this.id = id;
        this.name = name;
        this.origin = origin;
    }

    public Whisky(JsonObject jsonObject) {
        this.id = jsonObject.getInteger("ID");
        this.name = jsonObject.getString("NAME");
        this.origin = jsonObject.getString("ORIGIN");
    }

    public String getName() {
        return name;
    }

    public String getOrigin() {
        return origin;
    }

    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }
}
