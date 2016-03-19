package com.shaad.client;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.shaad.client.entities.Bank;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

import java.util.List;

class RootPanel extends Composite {

    interface TestViewUiBinder extends UiBinder<HTMLPanel, RootPanel> {
    }

    private static TestViewUiBinder ourUiBinder = GWT.create(TestViewUiBinder.class);

    private static final BankService BANK_SERVICE = GWT.create(BankService.class);

    @UiField
    FlowPanel bankList;

    @UiField
    TextBox cityTextBox;

    @UiField
    Button getBanksButton;

    public RootPanel() {
        initWidget(ourUiBinder.createAndBindUi(this));
        getBanksInCityList("");

        getBanksButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                final String todoItemText = cityTextBox.getText();
                RootPanel.this.getBanksInCityList(todoItemText);
            }
        });

        cityTextBox.getElement().setAttribute("placeholder", "Get banks in this city");
        cityTextBox.addKeyUpHandler(new KeyUpHandler() {
            @Override
            public void onKeyUp(KeyUpEvent event) {
                if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
                    final String todoItemText = cityTextBox.getText();
                    if (!todoItemText.isEmpty()) {
                        RootPanel.this.getBanksInCityList(todoItemText);
                    }
                }
            }
        });

    }

    private void getBanksInCityList(String city) {
        BANK_SERVICE.getCities(city, new MethodCallback<List<Bank>>() {
            @Override
            public void onFailure(final Method method, final Throwable exception) {
            }

            @Override
            public void onSuccess(final Method method, final List<Bank> response) {
                bankList.clear();
                for (final Bank bank : response) {
                    final BankLabel bankLabel = new BankLabel(bank);
                    bankList.add(new Label(bank.getName()));
                    bankList.add(bankLabel);
                }
            }
        });
    }
}