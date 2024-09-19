package org.example;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

//import static java.awt.SystemColor.text;

public class TelegramBot extends TelegramLongPollingBot {

    public static void main(String[] args) throws TelegramApiException {
        System.out.println("Starting Telegram Bot...");
        System.out.println("------------------------");
        TelegramBot telegramBot = new TelegramBot();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        telegramBotsApi.registerBot(telegramBot);
    }

    @Override
    public String getBotUsername() {
        return "";
    }

    @Override
    public String getBotToken() {
        return "";
    }

    @Override
    public void onUpdateReceived(final Update update) {
        if (!update.hasMessage() || !update.getMessage().hasText()) return;

        Message message = update.getMessage();
        String messageText = message.getText();
        String userName = message.getFrom().getUserName();
        System.out.println("From: '" + userName + "'\ntext: '" + messageText + "'" + "\n------------------------");

        if (messageText.startsWith("/start")) {
            sendMsg(message.getChatId(), "Hello and Welcome!\nTo generate new password send command\n'/generate X',\nwhere X is the number of characters.\nDefault number is 12.", false);
        }

        if (messageText.startsWith("/generate")) {
            final String[] messageParts = messageText.split(" ");
            int passwordLength = 12;
            if (messageParts.length > 1) {
                passwordLength = Integer.parseInt(messageParts[1]);
            }

            PasswordGenerator passwordGenerator = new PasswordGenerator();
            String password = passwordGenerator.generateNewPassword(passwordLength);

            sendMsg(message.getChatId(), "||" + password + "||", true);
        }
    }

    void sendMsg(long chatId, String messageText, boolean isMarkdown) {
        SendMessage sendMessage = new SendMessage();

//            sendMessage.enableHtml(true);
//            sendMessage.setText("<span class=\"tg-spoiler\">" + password + "</span>");

        sendMessage.enableMarkdownV2(isMarkdown);
        sendMessage.setText(messageText);

        sendMessage.setChatId(chatId);

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
//                throw new RuntimeException(e);
            System.out.println("Failed to send a message!");
        }

    }
}
