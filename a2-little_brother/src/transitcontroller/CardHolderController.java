package transitcontroller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import transitmodel.Card;
import transitmodel.CardHolder;
import transitmodel.Trip;
import transitutility.ChangeName;
import transitutility.CheckName;
import transitview.CardHolderView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A CardHolderController that controls the CardHolder class and is a EventHandler for the CardHolderView class.
 */
public class CardHolderController implements EventHandler<ActionEvent> {
    private static CardHolder cardHolder;
    private static Card card;

    /**
     * Get the CardHolder of current CardHolderController.
     *
     * @return the CardHolder
     */
    public static CardHolder getCardHolder() {
        return cardHolder;
    }

    /**
     * Set the CardHolder of this CardHolderController.
     *
     * @param cardHolder the CardHolder
     */
    public static void setCardHolders(CardHolder cardHolder) {
        CardHolderController.cardHolder = cardHolder;
    }

    /**
     * Get the Card that is currently being used.
     *
     * @return the Card
     */
    public static Card getCard() {
        return card;
    }

    /**
     * Set the card that is being used.
     *
     * @param card a card to be used
     */
    public static void setCard(Card card) {
        CardHolderController.card = card;
    }

    /**
     * Handle 7 different types of action by the CardHolder including Register/Suspend/TopUp the Card,
     * change name, get recent trips and get monthly cost.
     *
     * @param event the event that triggers this CardHolderController.
     */
    @Override
    public void handle(ActionEvent event) {
        String operation = ((Button) event.getSource()).getText();
        InputStreamReader is = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(is);
        switch (operation) {
            case "Register Card":
                System.out.println("------Create Card------");
                Card newCard = createCard();
                if (newCard != null) {
                    System.out.println("Create card card_number:" + newCard.getCardNumber());
                } else {
                    System.out.println("Cannot create card");
                }
                break;
            case "Use / Change Another":
                System.out.println("Select a card");
                cardHolder.getCardBag().forEach(item ->
                        System.out.println("CardNumber: " + item.getCardNumber() + " " +
                                "CardBalance: " + String.format("%1.2f", item.getBalance())));
                String number = "";
                boolean valid;
                do {
                    System.out.print("Input card card_number (0 to exit): ");
                    try {
                        number = br.readLine();
                        if (number.equals("0")) {
                            return;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    valid = true;
                    for (int i = 0; i < number.length(); i++) {
                        if (!Character.isDigit(number.charAt(i))) {
                            valid = false;
                        }
                    }
                } while (!valid);
                if (useCard(number)) {
                    if (CardHolderController.card.getBalance() >= 0) {
                        System.out.println("Success");
                        CardHolderView.cardLabel.setText("Card: " + CardHolderController.card.getCardNumber() + " is being Used");
                        CardHolderView.trip.setDisable(false);
                    } else {
                        System.out.println("Please recharge your card.");
                    }
                    CardHolderView.amount1.setDisable(false);
                    CardHolderView.amount2.setDisable(false);
                    CardHolderView.amount3.setDisable(false);
                    CardHolderView.suspend.setDisable(false);
                } else {
                    System.out.println("Your card has been banned");
                }
                break;

            case "$10":
            case "$20":
            case "$50":
                if (card == null) {
                    System.out.println("Use A Card First");
                    break;
                }
                Double amount = new Double(operation.substring(1));
                cardHolder.topup(CardHolderController.card, amount);
                System.out.println("Topup: " + amount + " " + "Current Balance: " + CardHolderController.card.getBalance());
                break;
            case "Suspend":
                if (card == null) {
                    System.out.println("Use A Card First");
                    break;
                }
                if (cardHolder.suspendCard(card)) {
                    CardHolderView.amount1.setDisable(true);
                    CardHolderView.amount2.setDisable(true);
                    CardHolderView.amount3.setDisable(true);
                    CardHolderView.trip.setDisable(true);
                    CardHolderView.suspend.setDisable(true);
                    CardHolderView.cardLabel.setText("Suspended Card: " + card.getCardNumber());
                }
                break;
            case "Change Name":
                String newName = "";
                do {
                    System.out.print("Input new name (0 to exit): ");
                    try {
                        newName = br.readLine();
                        if (newName.equals("0")) {
                            return;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } while (!CheckName.check(newName));
                ChangeName.change(newName, cardHolder.getName(), cardHolder.getEmail());
                cardHolder.setName(newName);
                CardHolderView.nameLabel.setText(newName);
                System.out.println("New Name: " + cardHolder.getName());
                break;
            case "Recent Trips":
                System.out.println("------------------");
                if (CardHolderController.cardHolder.getRecentTrips().size() == 0) {
                    System.out.println("You do not have any trips yet!");
                    break;
                }
                for (Trip t : CardHolderController.cardHolder.getRecentTrips()) {
                    System.out.println(t.tripInfo());
                }
                System.out.println("Daily Cost: $" + String.format("%1.2f", CardHolderController.cardHolder.getDailyCost()));
                break;
            case "Monthly Cost":
                System.out.println("------------------");
                cardHolder.getCosts().forEach((k, v) ->
                        System.out.println("Month: " + k + ", Cost: " + "$" + String.format("%1.2f", v)));
                System.out.println("Average Monthly Cost: " + String.format("%1.2f", cardHolder.averageMonthlyCost()));
                break;
        }
    }

    /**
     * Create a Card for the system.
     *
     * @return the Card
     */
    public Card createCard() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyMMddHHmmss");
        String dateString = formatter.format(new Date());
        String s = String.format("%s%d", dateString, (int) (Math.random() * 10));
        Card card = new Card(cardHolder, s);
        if (cardHolder.registerCard(card)) {
            return card;
        }
        return null;
    }

    /**
     * Return whether the Card can be used.
     *
     * @param number the Card number
     * @return True if the Card can be used, false otherwise.
     */
    public boolean useCard(String number) {
        for (Card item : CardHolderController.cardHolder.getCardBag()) {
            if (item.getCardNumber().equals(number) && item.canUse()) {
                CardHolderController.card = item;
                return true;
            }
        }
        return false;
    }
}
