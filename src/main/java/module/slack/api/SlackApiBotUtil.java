package module.slack.api;

import com.slack.api.Slack;
import com.slack.api.app_backend.interactive_components.payload.BlockActionPayload;
import com.slack.api.model.block.Blocks;
import com.slack.api.model.block.HeaderBlock;
import com.slack.api.model.block.LayoutBlock;
import com.slack.api.model.block.composition.BlockCompositions;
import com.slack.api.model.block.composition.TextObject;
import com.slack.api.webhook.WebhookPayloads;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Component
public class SlackApiBotUtil {

    private final SlackProperties slackApiProperties;
    private final Statement statement;

    private TextObject getField(Statement statement, String headerMessage) {
        return BlockCompositions.markdownText(
                "*" + "[" + headerMessage + "] " + "@" + statement.getUser() + "*" + " - " + statement.getSelectedOption());
    }

    private LayoutBlock getFieldSection(List<TextObject> fields) {
        return Blocks.section(s -> s.fields(fields));
    }

    public String callbackMessage(BlockActionPayload blockPayload) throws IOException {

        String user = blockPayload.getUser().getUsername(); // Slack User id
        String selectedOption = blockPayload.getActions().get(0).getSelectedOption().getText().getText(); // 선택 옵션
        HeaderBlock headerMessage = (HeaderBlock) blockPayload.getMessage().getBlocks().get(0); // header 메세지

        Statement select = new Statement(user,selectedOption);

        List<LayoutBlock> blocks = Blocks.asBlocks(
                Blocks.divider(),
                getFieldSection(Arrays.asList(getField(select, headerMessage.getText().getText())))
        );

        /* Slack 메세지 전송 */
        Slack.getInstance().send(slackApiProperties.getWebhookUrl(), WebhookPayloads.payload(p -> p.blocks(blocks)));

        return user;
    }
}
