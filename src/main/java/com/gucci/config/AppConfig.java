package com.gucci.config;

import org.aeonbits.owner.Config;

@Config.Sources({"classpath:app.properties"})
public interface AppConfig extends Config {

    @Key("base.url")
    String baseUrl();

    @Key("headless.mode")
    boolean headless();

    @Key("docker.remote")
    boolean remote();

    @Key("remote.url.docker")
    String dockerUrl();

    @Key("server")
    String server();

    @Key("port")
    int port();

    @Key("user")
    String user();

    @Key("sqlpassword")
    String sqlpassword();
}
