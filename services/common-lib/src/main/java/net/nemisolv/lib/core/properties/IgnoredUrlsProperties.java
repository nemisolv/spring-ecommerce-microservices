package net.nemisolv.lib.core.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;


@Data
@Configuration
@ConfigurationProperties(prefix = "app.ignored")
public class IgnoredUrlsProperties {
    private List<String> urls = new ArrayList<>();
}