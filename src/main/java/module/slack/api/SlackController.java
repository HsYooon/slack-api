package module.slack.api;

import com.slack.api.app_backend.interactive_components.payload.BlockActionPayload;
import com.slack.api.util.json.GsonFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class SlackController {

    private final SlackApiUtil slackApiUtil;
    private final SlackApiBotUtil slackApiBotUtil;

    @PostMapping
    public String send() throws IOException {
        String errorMessage = "error message";
        slackApiUtil.sendMessage(errorMessage);
        return errorMessage;
    }

    @PostMapping
    public String answer(String payload) throws IOException {
        return slackApiBotUtil.callbackMessage(GsonFactory.createSnakeCase()
                .fromJson(payload, BlockActionPayload.class));
    }

}
