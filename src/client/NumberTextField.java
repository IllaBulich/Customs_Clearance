package client;

import javafx.scene.control.TextField;

public class NumberTextField extends TextField {
    String numberRegEx="[0-9]*";

    @Override
    public void replaceText(int start,int end,String text){
        String ildValue = getText();
        if ((validata(text))){
            super.replaceText(start,end,text);
            String newText = super.getText();
            if (!validata(newText)){
                super.setText(ildValue);
            }
        }
    }

    private boolean validata(String text){
        return ("".equals(text) || text.matches(numberRegEx));
    }
}
