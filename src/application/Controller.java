package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ResourceBundle;

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
        
//        String input = new String(input1.getBytes("gbk"),"gbk");
        
        Lexer lx = new Lexer(new StringReader(input));

        
        try{
            String type;
            System.out.println("----------------------------");
           
            for(Token t; (t = lx.read()) != Token.EOF;){
            	
                switch (t.getType()){
                    case 1:
                        type = "Number";
                        break;
                    case 2:
                        type = "Identifier";
                        break;
                    case 3:
                        type = "String";
                        break;
                    default:
                        type = "Invalid token";
                        break;
                }
                if(t.getType() == 2){
                    if(t.isKeyword()) type = "Keyword";
                    else if(t.isOperator()) type = "Operator";
                    else if(t.isEOL()) type = "EOL";
                }
                System.out.println(type + "  =>  " + t.getText());
            }
            
            System.out.println("----------------------------");	
            
        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    public void clearLexer(ActionEvent event){
        textInput.setText("");
    }

}
