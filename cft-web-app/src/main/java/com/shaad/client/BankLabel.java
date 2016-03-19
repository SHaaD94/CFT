package com.shaad.client;

import com.shaad.client.entities.Bank;
import com.shaad.client.entities.Office;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;

class BankLabel extends Composite {

    interface BankUiBinder extends UiBinder<FlexTable, BankLabel> {
    }

    private static BankUiBinder ourUiBinder = GWT.create(BankUiBinder.class);

    @UiField
    FlexTable table;

    public BankLabel(final Bank bank) {
        initWidget(ourUiBinder.createAndBindUi(this));
        int row = 0;
        table.setTitle(bank.getName());
        for (Office office : bank.getOfficeList()) {
            table.insertRow(row);
            table.addCell(row);
            table.setText(row, 0, office.getCity());
            table.addCell(row);
            table.setText(row, 1, office.getAddress());
            table.addCell(row);
            table.setText(row, 2, office.getWorkingHours());
            row++;
        }
    }
}