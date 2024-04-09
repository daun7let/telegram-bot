package org.example.bottelegram.service;

import lombok.extern.slf4j.Slf4j;
import org.example.bottelegram.config.BotConfig;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class TelegramBot extends TelegramLongPollingBot {

    final BotConfig config;



    public TelegramBot(BotConfig config) {
        this.config = config;
        List<BotCommand> listOfCommands = new ArrayList<>();
        listOfCommands.add(new BotCommand("/start", "Да-да я Павел <3"));
        listOfCommands.add(new BotCommand("/help", "Что блять, какого хуя и почему!"));
        listOfCommands.add(new BotCommand("/punch", "Пиздани меня пж"));
        listOfCommands.add(new BotCommand("/score", "Счет очков"));
        try {
            this.execute(new SetMyCommands(listOfCommands, new BotCommandScopeDefault(), null));

        }catch (TelegramApiException e){

            log.error("Error setting bot's commad list: " + e.getMessage());
        }
    }

    @Override
    public String getBotToken(){
        return config.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
      if (update.hasMessage() && update.getMessage().hasText()){
          String messageText = update.getMessage().getText();
          long chatId = update.getMessage().getChatId();


          switch (messageText){
              case "/start":

                      startCommandReceived(chatId, update.getMessage().getChat().getFirstName());
                      break;
              case "/help":

                  helpAnswer(chatId, update.getMessage().getChat().getFirstName());
                  break;
              default:

                  sendMessage(chatId, "Иди ты нахуй!");
          }

      }
    }


    private void helpAnswer (long chatId, String name){
        String helloAnswerToYou = "Павла можно пиздить! Пизди его каждый день и получай очки!\n\n" +
                                  "Доступные команды: \n\n" +
                                  "/start позволяет поприветствовать Павла.\n" +
                                  "/help вся нужная информация.\n" +
                                  "/punch ударь его!\n" +
                                  "/score узнай сколько у тебя очков.\n\n" +
                                  "Удачи и веселого времяприпровождения, " + name + "!";

        sendMessage(chatId, helloAnswerToYou);

        log.info("This guy:" + name);
    }

    private void startCommandReceived(long chatId, String name){
         String answer = "Привет, " + name + ", " + "я Павел, " + "пиздани меня!";

         sendMessage(chatId, answer);

         log.info("Replied to user: " + name);
    }

    private void sendMessage(long chatId, String textToSend){
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(textToSend);

        try {
            execute(message);
        }
        catch (TelegramApiException e) {
            log.error("Error occurred: " + e.getMessage());
        }

    }

    @Override
    public String getBotUsername() {
        return config.getBotName();
    }

}
