package cn.element.manage.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "system")
public class SystemProperty {
    
    private String basePackage;
}
