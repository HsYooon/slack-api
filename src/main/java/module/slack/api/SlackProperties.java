package module.slack.api;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@Getter
@ConstructorBinding
@RequiredArgsConstructor
@ConfigurationProperties("slack.api-url")
public class SlackProperties {

    /*
    *  @ConfigurationProperties
    *    *.properties, *.yml 파일에 있는 property 를 자바 클래스에 값을 가져와서 바인딩 해줌
    *    Application에 @ConfigurationPropertiesScan으로 properties를 스캔해주어야 한다
    *
    *    ------------------------------
    *    - @Value("${slack.api.url})  -
    *    - private String webhookUrl; -
    *    ------------------------------
    *       위처럼 사용가능하다
    *
    *  @ConstructorBinding
    *     생성자로 바인딩 시켜줌
    *
    * */

    private String webhookUrl;
}
