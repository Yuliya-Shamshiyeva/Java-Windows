package src.lab2;



import java.io.BufferedReader;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;



public  class Login extends Application {

    TextField usertext = new TextField();
    PasswordField pwtext = new PasswordField();
    String path = "D:\\user.txt";
    File file = new File(path);

    @Override
    public void start(Stage primaryStage)  {
        GridPane pane = new GridPane();
        pane.setAlignment( Pos.CENTER);
        pane.setPadding(new Insets(30,30,30,30));
        pane.setHgap(9.5);
        pane.setVgap(9.5);
        pane.add(new Label("Логин :"), 0, 0);
        pane.add(usertext, 1, 0);
        pane.add(new Label("Пароль :"), 0, 1);
        pane.add(pwtext, 1, 1);
        Button login = new Button("      Войти     ");
        Button register = new Button("Регистрация");
        pane.add(login, 1, 2);
        pane.add(register, 1, 3);

        GridPane.setHalignment(login, HPos.RIGHT);
        GridPane.setHalignment(register, HPos.RIGHT);

        register.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                try {
                    if (IsUserExist(file, usertext.getText())) {
                        System.out.println("Пользователь уже существует");
                    }
                    else {
                        try {
                            UserRegister(file, usertext.getText(),pwtext.getText());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        });

        login.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                try {
                    if (IsUserExist(file, usertext.getText())) {
                        UserLogin(file, usertext.getText(), pwtext.getText());
                        Label secondLabel = new Label("Добро пожаловать!");

                        StackPane secondaryLayout = new StackPane();
                        secondaryLayout.getChildren().add(secondLabel);
                        Scene secondScene = new Scene(secondaryLayout, 230, 100);
                        Stage newWindow = new Stage();
                        newWindow.setTitle("Second Stage");
                        newWindow.setScene(secondScene);

                        newWindow.setX(primaryStage.getX() + 200);
                        newWindow.setY(primaryStage.getY() + 100);

                        newWindow.show();

                    }
                    else {
                        System.out.println("Пользователь не зарегистрирован");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });


        Scene scene  = new Scene(pane);
        primaryStage.setTitle("Вход в систему");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public static boolean IsUserExist(File file , String username) throws IOException {
        boolean flag = false;
        try {
            FileInputStream fis = new FileInputStream(file);
            InputStreamReader isr = new InputStreamReader(fis,"utf-8");
            BufferedReader br = new BufferedReader(isr);

            String line = "";
            while((line = br.readLine())!=null) {
                if(line.equals("un"+username)) {
                    flag = true;
                    break;
                }

            }
            br.close();
            isr.close();
            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return flag;
    }
    public static void WriteInFile(File file,String str) {

        try {
            FileWriter fw = new FileWriter(file,true);
            fw.write(str+"\n");
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    static void UserRegister(File file,String username,
                             String passwd) throws IOException {
        if(!IsUserExist(file, "un"+username)) {
            WriteInFile(file, "un"+username);
            WriteInFile(file, "pw"+passwd);
            System.out.println("Вы успешно зарегистрировались");
        }else {
            System.out.println("Имя пользователя уже существует");
        }
    }
    public static void UserLogin(File file,String username,String passwd) throws IOException {

        FileInputStream fis = new FileInputStream(file);
        InputStreamReader isr = new InputStreamReader(fis,"utf-8");
        BufferedReader br = new BufferedReader(isr);
        String line = "";
        while((line = br.readLine())!=null) {
            if(line.equals("un"+username)) {
                if(br.readLine().equals("pw"+passwd)) {
                    System.out.println("Авторизация успешна");

                    break;
                }else {
                    System.out.println("Неверный пароль");
                    break;
                }
            }
        }
        br.close();
        isr.close();
        fis.close();
    }
}

