package com.Shoots.domain;

public class Faq {
    private int faq_idx;
    private int writer;
    private String title;
    private String content;
    private String faq_file;
    private String register_date;
    private String name;

    public int getFaq_idx() {
        return faq_idx;
    }

    public void setFaq_idx(int faq_idx) {
        this.faq_idx = faq_idx;
    }

    public int getWriter() {
        return writer;
    }

    public void setWriter(int writer) {
        this.writer = writer;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFaq_file() {
        return faq_file;
    }

    public void setFaq_file(String faq_file) {
        this.faq_file = faq_file;
    }

    public String getRegister_date() {
        return register_date;
    }

    public void setRegister_date(String register_date) {
        this.register_date = register_date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}