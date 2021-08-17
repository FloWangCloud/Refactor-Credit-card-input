/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package creditcardfxml;

import java.io.File;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

/**
 *
 * @author Yiran Wang
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private ImageView imageView;

    @FXML
    private ImageView rightImageView;

    @FXML
    private TextField txtCardNumber;
    @FXML
    private TextField txtExpiryDate;
    @FXML
    private TextField txtCVV;

    @FXML
    private Text validExpiryDate;
    @FXML
    private Text validCVV;

    int flag = 0;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        Image image = new Image("creditcardfxml/notselected.jpg");
        imageView.setImage(image);

        Image image1 = new Image("creditcardfxml/blank.jpg");
        rightImageView.setImage(image1);
    }

    @FXML
    public void CardNumberChange() {

        String text = txtCardNumber.getText();

        CreditCardType visa = new Visa();
        CreditCardType amex = new Amex();
        CreditCardType mastercard = new Mastercard();
        CreditCardType jcb = new JCB();
        Image image;
        Image image1;
        if (!text.isEmpty()) {
            if (visa.matches(text)) {
                image = new Image("creditcardfxml/visa.jpg");
                imageView.setImage(image);
                flag = 1;

            } else if (mastercard.matches(text)) {
                image = new Image("creditcardfxml/master.jpg");
                imageView.setImage(image);
                flag = 2;

            } else if (text.length() > 1) {
                if (amex.matches(text)) {
                    image = new Image("creditcardfxml/amex.jpg");
                    imageView.setImage(image);
                    flag = 3;

                } else if (jcb.matches(text)) {
                    image = new Image("creditcardfxml/jcb.jpg");
                    imageView.setImage(image);
                    flag = 4;
                }
            } else {
                //imageLabel.setIcon(new ImageIcon("notvalid.jpg"));
            }
            if (isValidCreditCardNumber(text)) {
                image1 = new Image("creditcardfxml/right.jpg");
                rightImageView.setImage(image1);

            } else {
                image1 = new Image("creditcardfxml/blank.jpg");
                rightImageView.setImage(image1);

            }
        } else {
            image = new Image("creditcardfxml/notselected.jpg");
            imageView.setImage(image);

        }

    }

    @FXML
    public void ExpiryDateChange() {
        String text = txtExpiryDate.getText();
        String format = "MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        sdf.setLenient(false);
        try {
            Date date = sdf.parse(text);
            if (!sdf.format(date).equals(text)) {
                validExpiryDate.setText("Date is not in valid format(MM/YYYY).");
            } else {
                validExpiryDate.setText("");
            }
        } catch (ParseException ex) {
        }

    }

    @FXML
    public void CVVChange() {
        String text = txtCVV.getText();
        if (flag == 1 || flag == 2 || flag == 4) {
            if (text.length() != 3) {
                validCVV.setText("Not valid CVV number");
            } else {
                validCVV.setText("");
            }
        } else if (flag == 3) {
            if (text.length() != 4) {
                validCVV.setText("Not valid CVV number");
            } else {
                validCVV.setText("");
            }
        } else {
            validCVV.setText("");

        }
    }
// Returns whether the given string is a valid @ card number
// according to the Luhn checksum algorithm.

    public boolean isValidCreditCardNumber(String text) {

        // add all of the digits
        int sum = 0;
        for (int i = 0; i < text.length(); i++) {
            int digit = Integer.valueOf(text.substring(i, i + 1));
            if (i % 2 == 0) { // double every other number, add digits
                digit *= 2;
                sum += (digit / 10) + (digit % 10);
            } else {
                sum += digit;
            }
        }
        // valid numbers add up to a multiple of 10
        return (sum % 10 == 0 && text.length() == 16);

    }

    public class CreditCardType {

        /**
         * Returns true if the card number matches this type of credit card.
         * Note that this method is <strong>not</strong> responsible for
         * analyzing the general form of the card number because
         * <code>CreditCardValidator</code> performs those checks before calling
         * this method. It is generally only required to valid the length and
         * prefix of the number to determine if it's the correct type.
         *
         * @param card The card number, never null.
         * @return true if the number matches.
         */
        boolean matches(String card) {
            return false;
        }

    }

    private class Visa extends CreditCardType {

        private static final String PREFIX = "4";

        public boolean matches(String card) {
            return (card.substring(0, 1).equals(PREFIX));
        }
    }

    private class Amex extends CreditCardType {

        private static final String PREFIX1 = "34";
        private static final String PREFIX2 = "37";

        public boolean matches(String card) {
            return ((card.substring(0, 2).equals(PREFIX1) || card.substring(0, 2).equals(PREFIX2)));
        }
    }

    private class JCB extends CreditCardType {

        private static final String PREFIX = "35";

        public boolean matches(String card) {
            return (card.substring(0, 2).equals(PREFIX));
        }
    }

    private class Mastercard extends CreditCardType {

        private static final String PREFIX = "5";

        public boolean matches(String card) {
            return (card.substring(0, 1).equals(PREFIX));
        }
    }

}
