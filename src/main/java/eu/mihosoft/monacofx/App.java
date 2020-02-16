/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package eu.mihosoft.monacofx;

import java.io.File;
import java.util.function.Function;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker.State;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import netscape.javascript.JSObject;

public class App extends Application {

    public String getGreeting() {
        return "Hello world.";
    }

    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) throws Exception {

        StackPane root = new StackPane();

        MonacoFX editor = new MonacoFX();

        editor.getEditor().getDocument().setText("[error:]   \n\nint a; \"abc\"");

        LanguageSupport myLang = new LanguageSupport() {
            @Override
            public String getName() {
                return "mylang";
            }

            @Override
            public FoldingProvider getFoldingProvider() {
                return editor1 ->
                        new Folding[]{
                            new Folding(5, 7),
                            new Folding(13, 17),
                            new Folding(19, 21)
                        };
            }

            @Override
            public MonarchSyntaxHighlighter getMonarchSyntaxHighlighter() {
                return () ->
                " 	tokenizer: {\n"+
                "\n"+
                " 		root: [\n"+
                "\n"+
                "           [/\\[error.*/, \"custom-error\"],\n"+
                "\n"+
                " 			[/\\[notice.*/, \"custom-notice\"],\n"+
                "\n"+
                " 			[/\\[info.*/, \"custom-info\"],\n"+
                "\n"+
                " 			[/\\[[a-zA-Z 0-9:]+\\]/, \"custom-date\"],\n"+
                "\n"+
                " 		],\n"+
                "\n"+
                " 	}\n";
            }
        };

        editor.getEditor().registerLanguage(myLang);

        EditorTheme theme = new EditorTheme("mylangTheme","vs",false,
            new Rule("custom-info","808080"),
            new Rule("custom-error", "ff0000",  "bold")
        );

        editor.getEditor().registerTheme(theme);

        Thread thread = new Thread(()->{
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Platform.runLater(()->editor.getEditor().setCurrentLanguage("mylang"));
            Platform.runLater(()->editor.getEditor().setCurrentTheme("mylangTheme"));

        });
        thread.start();

        editor.getEditor().setCurrentLanguage("c");
        editor.getEditor().setCurrentTheme("vs");

//        editor.getEditor().getDocument().textProperty().addListener((ov,o,n)->{
//            System.out.println("Text-Change: " + editor.getEditor().getDocument().getText());
//        });

        root.getChildren().add(editor);

        Scene scene = new Scene(root, 800,600);

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}