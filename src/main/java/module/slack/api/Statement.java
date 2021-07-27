package module.slack.api;

import lombok.Data;

@Data
public class Statement {

    private String user;
    private String selectedOption;

    public Statement(String user, String selectedOption) {
        this.user = user;
        this.selectedOption = selectedOption;
    }
}
