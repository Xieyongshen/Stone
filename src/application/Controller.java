package application;

import application.astree.ASTList;
import application.canvastree.CanvasTree;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import application.astree.ASNode;

public class Controller implements Initializable {

    @FXML
    private TextArea textInput;

    @FXML
    private Button btnLexer;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    public void newLexer(ActionEvent event) throws UnsupportedEncodingException {
        String input = textInput.getText();
        Lexer lx = new Lexer(input);
        try{
            String type;
            System.out.println("----------------------------");
            for(Token t; (t = lx.read()) != Token.EOF;){

                switch (t.getType()){
                    case 1:
                        type = "Number";
                        break;
                    case 2:
                        type = "String";
                        break;
                    case 3:
                        type = "Identifier";
                        break;
                    case 4:
                        type = "Keyword";
                        break;
                    case 5:
                        type = "Operator";
                        break;
                    default:
                        type = "Invalid token";
                        break;
                }
                if(t.isEOL()){
                    type = "EOL";
                }
                System.out.println(type + "  =>  " + t.getText());
            }

            ArrayList<ASNode> rchildren = new ArrayList<>();

            System.out.println("----------------------------");

            ClassParser bp = new ClassParser();
            while (lx.peek(0) != Token.EOF) {
                ASNode ast = bp.parse(lx);
                System.out.println("=> " + ast.toString());
                rchildren.add(ast);
            }
            System.out.println("----------------------------");

            ASTList root = new ASTList(rchildren);
            CanvasTree ctree = new CanvasTree(root);
            ctree.draw();
            
        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    public void clearLexer(ActionEvent event){
        textInput.setText("");
    }

    @FXML
    public void importFile(ActionEvent event){
        Stage mainStage = null;
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Import File From");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(mainStage);

        try {
            InputStreamReader isr = new InputStreamReader(new FileInputStream(file), "UTF-8");
            BufferedReader br = new BufferedReader(isr);
            String line;
            String content = "";
            while ((line = br.readLine()) != null) {
                content += line + "\n";
            }
            textInput.setText(content);
            br.close();
            isr.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
