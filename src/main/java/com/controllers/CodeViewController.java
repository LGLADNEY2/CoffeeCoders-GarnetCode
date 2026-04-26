package com.controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.Duration;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;
import org.fxmisc.richtext.model.StyleSpans;
import org.fxmisc.richtext.model.StyleSpansBuilder;

import com.techprep.App;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * Controller for the code view; handles code editing, syntax highlighting, and file operations.
 * @author Coffee Coders
 */
public class CodeViewController {

    @FXML
    private CodeArea codeArea;

    @FXML
    private ComboBox<String> languagePicker;

    private static final String[] KEYWORDS = new String[] {
        "abstract","assert","boolean","break","byte","case","catch","char","class",
        "const","continue","default","do","double","else","enum","extends","final",
        "finally","float","for","goto","if","implements","import","instanceof","int",
        "interface","long","native","new","package","private","protected","public",
        "return","short","static","strictfp","super","switch","synchronized","this",
        "throw","throws","transient","try","void","volatile","while"
    };

    private static final String KEYWORD_PATTERN = "\\b(" + String.join("|", KEYWORDS) + ")\\b";
    private static final String PAREN_PATTERN = "\\(|\\)";
    private static final String BRACE_PATTERN = "\\{|\\}";
    private static final String BRACKET_PATTERN = "\\[|\\]";
    private static final String SEMICOLON_PATTERN = "\\;";
    private static final String STRING_PATTERN = "\"([^\"\\\\]|\\\\.)*\"";
    private static final String COMMENT_PATTERN = "//[^\\n]*" + "|" + "/\\*(.|\\R)*?\\*/";

    private static final Pattern PATTERN = Pattern.compile(
        "(?<KEYWORD>" + KEYWORD_PATTERN + ")"
      + "|(?<PAREN>" + PAREN_PATTERN + ")"
      + "|(?<BRACE>" + BRACE_PATTERN + ")"
      + "|(?<BRACKET>" + BRACKET_PATTERN + ")"
      + "|(?<SEMICOLON>" + SEMICOLON_PATTERN + ")"
      + "|(?<STRING>" + STRING_PATTERN + ")"
      + "|(?<COMMENT>" + COMMENT_PATTERN + ")"
    );

    /**
     * Initializes the code area with syntax highlighting, line numbers, and language selection.
     */
    @FXML
    public void initialize() {
        codeArea.getStylesheets().add(
            getClass().getResource("/com/techprep/java-keywords.css").toExternalForm()
        );

        codeArea.setParagraphGraphicFactory(LineNumberFactory.get(codeArea));
        codeArea.setWrapText(false);

        languagePicker.getItems().setAll(List.of("C++", "Java", "Python"));
        languagePicker.getSelectionModel().select("C++");
        codeArea.clear();

        codeArea.multiPlainChanges()
                .successionEnds(Duration.ofMillis(300))
                .subscribe(ignore ->
                    codeArea.setStyleSpans(0, computeHighlighting(codeArea.getText()))
                );
    }

    /**
     * Placeholder for code execution functionality.
     */
    @FXML
    private void runCode() {
        // Placeholder action until execution service is wired.
    }

    /**
     * Placeholder for code submission functionality.
     */
    @FXML
    private void submitCode() {
        // Placeholder action until submission service is wired.
    }

    /**
     * Navigates back to the question detail view.
     *
     * @throws IOException when the FXML cannot be loaded
     */
    @FXML
    private void goBackToQuestion() throws IOException {
        App.setRoot("question_detail");
    }

    @FXML
    private void openFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Source File");
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Source Files", "*.cpp", "*.cc", "*.cxx", "*.java", "*.py"),
            new FileChooser.ExtensionFilter("All Files", "*.*")
        );

        Scene scene = codeArea.getScene();
        if (scene == null) return;

        Stage stage = (Stage) scene.getWindow();
        File file = fileChooser.showOpenDialog(stage);

        if (file != null) {
            try {
                String content = Files.readString(file.toPath());
                codeArea.replaceText(content);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static StyleSpans<Collection<String>> computeHighlighting(String text) {
        Matcher matcher = PATTERN.matcher(text);
        int lastKwEnd = 0;
        StyleSpansBuilder<Collection<String>> spansBuilder = new StyleSpansBuilder<>();

        while (matcher.find()) {
            String styleClass =
                    matcher.group("KEYWORD") != null ? "keyword" :
                    matcher.group("PAREN") != null ? "paren" :
                    matcher.group("BRACE") != null ? "brace" :
                    matcher.group("BRACKET") != null ? "bracket" :
                    matcher.group("SEMICOLON") != null ? "semicolon" :
                    matcher.group("STRING") != null ? "string" :
                    matcher.group("COMMENT") != null ? "comment" :
                    null;

            spansBuilder.add(Collections.emptyList(), matcher.start() - lastKwEnd);
            spansBuilder.add(Collections.singleton(styleClass),
                             matcher.end() - matcher.start());
            lastKwEnd = matcher.end();
        }

        spansBuilder.add(Collections.emptyList(), text.length() - lastKwEnd);
        return spansBuilder.create();
    }
}