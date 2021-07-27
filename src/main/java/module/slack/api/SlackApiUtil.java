package module.slack.api;

import com.slack.api.Slack;
import com.slack.api.model.block.Blocks;
import com.slack.api.model.block.LayoutBlock;
import com.slack.api.model.block.composition.OptionObject;
import com.slack.api.model.block.composition.PlainTextObject;
import com.slack.api.model.block.element.BlockElement;
import com.slack.api.model.block.element.BlockElements;
import com.slack.api.webhook.WebhookPayloads;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.slack.api.model.block.composition.BlockCompositions.plainText;

@RequiredArgsConstructor
@Component
public class SlackApiUtil {

    private final SlackProperties slackProperties;

    /* 헤더 -> logGroup */
    private LayoutBlock getHeader(String text) {
        return Blocks.header(h -> h.text(
                plainText(pt -> pt.emoji(true)
                        .text(text))));
    }

    /* 섹션 -> error message */
    private LayoutBlock getSection(String message) {
        return Blocks.section(s -> s.text(
                plainText(message)));
    }

    /* 블록 -> select 박스 */
    private List<BlockElement> getActionBlocks() {
        List<BlockElement> actions = new ArrayList<>();

        actions.add(BlockElements.staticSelect(b->
                b.placeholder(PlainTextObject.builder()
                        .text("처리 상태")
                        .emoji(true)
                        .build())
                        .options(OptionObjects())
                        .actionId("select_action")) );
        return actions;
    }

    /* 블록 -> select -> options */
    private List<OptionObject> OptionObjects() {

        List<OptionObject> optionObject = new ArrayList<>();

        optionObject.add(OptionObject.builder()
                .text(PlainTextObject.builder()
                        .emoji(true)
                        .text("처리완료")
                        .build())
                .value("value-0")
                .build());

        optionObject.add(OptionObject.builder()
                .text(PlainTextObject.builder()
                        .emoji(true)
                        .text("처리중")
                        .build())
                .value("value-1")
                .build());

        optionObject.add(OptionObject.builder()
                .text(PlainTextObject.builder()
                        .emoji(true)
                        .text("미처리")
                        .build())
                .value("value-2")
                .build());

        return optionObject;
    }

    public String sendMessage(String errorMessage) throws IOException {

        String headerMessage = "Header Messge 입니다";

        /* 최종적인 레이아웃 만드는 곳 */
        List<LayoutBlock> layoutBlocks = Blocks.asBlocks(
                getHeader(headerMessage),
                getSection(checkErrorMessage(errorMessage)),
                Blocks.divider(),
                Blocks.actions(getActionBlocks())
        );

        Slack.getInstance().send(slackProperties.getWebhookUrl(), WebhookPayloads
                    .payload(p -> p.blocks(layoutBlocks)));

        return errorMessage;
    }

    /* 메세지 길이 체크 */
    private String checkErrorMessage(String errorMessage) {
        return errorMessage.length() > 2000 ? errorMessage.substring(0,1999): errorMessage;
    }
}
