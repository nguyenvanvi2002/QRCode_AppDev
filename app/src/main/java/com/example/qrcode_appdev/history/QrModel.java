package com.example.qrcode_appdev.history;

public class QrModel {

    private final String title;
    private final String dateTime;
    private int type;
    private String content;
    /*QR Type
    1: url
    2: text
    3: wifi
    4: phone
    5: sms
    6: contact
    7: event
    8: email
     */


    public QrModel(int type, String dateTime, String title, String content) {
        this.type = type;
        this.dateTime = dateTime;
        this.title = title;
        this.content = content;
    }

    @Override
    public String toString() {
        return "Type: " + type +
                " Content: '" + content + '\'';
    }


    public String getTitle() {
        return title;
    }

    public String getDateTime() {
        return dateTime;
    }


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
