package ru.netology.web.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import ru.netology.web.data.DataHelper;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class DashboardPage {
    private ElementsCollection cards = $$(".list__item div");
    private final String balanceStart = "баланс: ";
    private final String balanceFinish = " р.";
    private final SelenideElement depositButton = $("[data-test-id=action-deposit]");
    private final SelenideElement heading = $("[data-test-id=dashboard]");
    private final SelenideElement reloadButton = $("[data-test-id='action-reload']");

    public DashboardPage() {
        heading.should(Condition.visible);
    }

    private SelenideElement getCardElement(DataHelper.CardInfo cardInfo){
        return cards.find(Condition.attribute("data-test-id", cardInfo.getTestId()));
    }

    public int getCardBalance(DataHelper.CardInfo cardInfo){
       var text = getCardElement(cardInfo).getText();
       return extractBalance(text);
    }

    public int getCardBalance(int index) {
        var text = cards.get(index).getText();
        return extractBalance(text);
    }

    public TransferPage selectCardToTransfer(DataHelper.CardInfo cardInfo){
        getCardElement(cardInfo). $("[data-test-id=action-deposit]").click();
        return new TransferPage();
    }

    public void reloadDashboardPage(){
        reloadButton.click();
        heading.shouldBe(Condition.visible);
    }

    private int extractBalance(String text) {
        var start = text.indexOf(balanceStart);
        var finish = text.indexOf(balanceFinish);
        var value = text.substring(start + balanceStart.length(), finish);
        return Integer.parseInt(value);
    }

    public void checkCardBalance(DataHelper.CardInfo cardInfo, int expectedBalance){
        getCardElement(cardInfo).should(Condition.visible).should(Condition.text(balanceStart + expectedBalance + balanceFinish));
    }


}
