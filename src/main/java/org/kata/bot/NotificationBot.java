package org.kata.bot;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
@Getter
@Setter
public class NotificationBot extends TelegramLongPollingBot {

    private static final String START = "/start";
    private Long chatId;

    public NotificationBot(@Value("${bot.token}") String botToken) {
        super(botToken);
    }

    @Override
    public void onUpdateReceived(Update update) {
        var message = update.getMessage().getText();
        chatId = update.getMessage().getChatId();

        if (message.equals(START)) {
            String userName = update.getMessage().getChat().getUserName();
            startChat(chatId, userName);
            System.out.println(chatId);
        } else {
            sendText(chatId, "Sorry, the message is not supported");
            System.out.println(chatId);
        }
    }

    @Override
    public String getBotUsername() {
        return "check_the_sent_code_bot";
    }

    private void startChat(Long who, String userName) {
        var text = """
                Welcome, %s!
                Here you can get confirmation code to change your contact details.
                """;
        var formText = String.format(text, userName);
        sendText(who, formText);
    }

    public void sendText(Long who, String what){
        SendMessage sm = SendMessage.builder()
                .chatId(who.toString()) //Who are we sending a message to
                .text(what).build();    //Message content
        try {
            execute(sm);                        //Actually sending the message
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);      //Any error will be printed here
        }
    }
}
